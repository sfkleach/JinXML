/**
 * Copyright Stephen Leach, 2014
 * This file is part of the MinXML for Java library.
 * 
 * MinXML for Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinXML for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */

package com.steelypip.powerups.hydraxml;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.steelypip.powerups.common.StdPair;

/**
 *	A convenience class that implements a recursive
 * 	walk over a HydraXML tree. 
 */
public abstract class AbsMultiTreeWalker< Key, Value > implements AsMultiTree< Key, Value > {
	
	
	/**
	 * startWalk is called at the start of the tree-walk of the subject and its children. 
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the HydraXML element to be visited
	 */
	public abstract void startWalk( Key field, Value subject );
	
	private void startWalk( Map.Entry< Key, Value > link ) {
		this.startWalk( link.getKey(), link.getValue() );
	}

	
	/**
	 * endWalk is called at the end of the tree-walk of the subject and its children.
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the HydraXML element to be visited
	 */
	public abstract void endWalk( Key field, Value subject );

	private void endWalk( Map.Entry< Key, Value > link ) {
		this.endWalk( link.getKey(), link.getValue() );
	}
	
	
	/**
	 * The walk method is used to implement a depth-first, left-to-right recursive 
	 * scan over a tree. The startWalk and endWalk methods are invoked on the way
	 * down the tree and up the tree respectively. 
	 * 
	 * @param subject the element tree to be walked
	 * @return the walker itself, used for chaining method calls.
	 */	
	private AbsMultiTreeWalker< Key, Value > walk( Key field, final Value subject ) {
		this.startWalk( field, subject );
		for ( Map.Entry< Key, Value > link : this.entries( subject ) ) {
			this.walk( link.getKey(), link.getValue() );
		}
		this.endWalk( field, subject );
		return this;
	}
	
	public AbsMultiTreeWalker< Key, Value > walk( final Value subject ) {
		return this.walk( this.defaultKey(), subject );
	}
		
	static abstract class CommonIterator< Key, Value > implements Iterator< Value > {
		
		protected final AbsMultiTreeWalker< Key, Value > walker;
		protected final Deque< Map.Entry< Key, Value > > queue = new ArrayDeque<>();
		protected final Key default_key;
		
		/**
		 * This is a marker value used to mark the element 'below' it (lower index)
		 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
		 * represent 'startWalk + expand' tasks. 
		 */
		protected final Map.Entry< Key, Value > end_walk_marker;
		
		public CommonIterator( AbsMultiTreeWalker< Key, Value > walker, Value subject  ) {
			this.walker = walker;
			this.default_key = walker.defaultKey();
			this.queue.add( new StdPair< Key, Value >( this.default_key, subject ) );
			this.end_walk_marker = (Map.Entry< Key, Value >)( new StdPair< Key, Value >( this.default_key, this.walker.badValue() ) );
		}	

		protected void expand( final Map.Entry< Key, Value > x ) {
			this.walker.startWalk( x );
			queue.addLast( x );
			queue.addLast( this.end_walk_marker );
			List< Map.Entry< Key, Value > > link_list = this.walker.linksToList( x.getValue() );
			final ListIterator< Map.Entry< Key, Value > > it = link_list.listIterator( link_list.size() );
			while ( it.hasPrevious() ) {
				queue.addLast( it.previous() );
			}						
		}
		
	}
	
	static class PreOrderIterator< Key, Value > extends CommonIterator< Key, Value > {
		
		public PreOrderIterator( AbsMultiTreeWalker< Key, Value > walker, Value subject ) {
			super( walker, subject );
		}	
		
		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				if ( this.end_walk_marker != queue.getLast() ) return true;
				queue.removeLast();
				this.walker.endWalk( queue.removeLast() );
			}
			return false;
		}
		
		@Override
		public Value next() {
			final Map.Entry< Key, Value > x = queue.removeLast();
			if ( x != this.end_walk_marker ) {
				this.expand( x );
				return x.getValue();
			}
			this.walker.endWalk( queue.removeLast() );
			this.hasNext();
			return queue.removeLast().getValue();
		}
		
	}
	
	public Iterable< Value > preOrder( final Value subject ) {
		return new Iterable< Value >() {
			@Override
			public Iterator< Value > iterator() {
				return new PreOrderIterator< Key, Value >( AbsMultiTreeWalker.this, subject );
			}
		};
	}
	
	static class PostOrderIterator< Key, Value > extends CommonIterator< Key, Value > {
	
		public PostOrderIterator( AbsMultiTreeWalker< Key, Value > walker, Value subject) {
			super( walker, subject );
		}	
		
		/**
		 * Advances the queue so that the head is a sentinel value. We
		 * call this the normal situation. 
		 * @return the queue has items in it.
		 */
		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				if ( end_walk_marker == queue.getLast() ) return true;
				Map.Entry< Key, Value > e = queue.removeLast();
				this.expand( e );
			}
			return false;
		}
		

		/**
		 * After this method the queue is NOT normal. A call to
		 * hasNext would sort it out.
		 */
		@Override
		public Value next() {
			final Map.Entry< Key, Value > x = queue.removeLast();
			if ( x == end_walk_marker ) {
				//	The queue is normalised.
				//	This is the overwhelmingly common case - so we special case it.
				final Map.Entry< Key, Value  > e = queue.removeLast();
				this.walker.endWalk( e );
				return e.getValue();
			} else {
				//	The queue was not normalised by a call to hasNext and the
				//	head of the queue was not a request for an end-visit. We
				//	Must process this element, normalise the queue and re-try.
				this.expand( x );
				this.hasNext();
				return this.next();
			}
		}		
	}

	
	public Iterable< Value > postOrder( final Value subject ) {
		return new Iterable< Value >() {
			@Override
			public Iterator< Value > iterator() {
				return new PostOrderIterator< Key, Value >( AbsMultiTreeWalker.this, subject );
			}
		};
	}
	
}
