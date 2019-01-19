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

package com.steelypip.powerups.minxml;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jdt.annotation.NonNull;

/**
 *	A convenience class that implements a recursive
 * 	walk over a MinXML tree. 
 */
public abstract class MinXMLWalker {
	/**
	 * startWalk is called at the start of the tree-walk of the subject and its children. 
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void startWalk( MinXML subject );

	
	/**
	 * endWalk is called at the end of the tree-walk of the subject and its children.
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void endWalk( MinXML subject );
	
	/**
	 * The walk method is used to implement a depth-first, left-to-right recursive 
	 * scan over a tree. The startWalk and endWalk methods are invoked on the way
	 * down the tree and up the tree respectively. 
	 * 
	 * @param subject the element tree to be walked
	 * @return the walker itself, used for chaining method calls.
	 */	
	public MinXMLWalker walk( final MinXML subject ) {
		this.startWalk( subject );
		for ( MinXML kid : subject ) {
			this.walk(  kid );
		}
		this.endWalk( subject );
		return this;
	}
	
	
	/**
	 * This is a marker value used to mark the element 'below' it (lower index)
	 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
	 * represent 'startWalk + expand' tasks. 
	 */
	private static final @NonNull MinXML end_walk_marker = new BadMinXML();
	
	static abstract class CommonIterator implements Iterator< @NonNull MinXML > {
		
		protected final @NonNull MinXMLWalker walker;
		protected final Deque< @NonNull MinXML > queue = new ArrayDeque<>();
		
		public CommonIterator( @NonNull MinXMLWalker walker, @NonNull MinXML subject ) {
			this.walker = walker;
			this.queue.add( subject );
		}	

		protected void expand( final @NonNull MinXML x ) {
			this.walker.startWalk( x );
			queue.addLast( x );
			queue.addLast( end_walk_marker );
			final ListIterator< @NonNull MinXML > it = x.listIterator( x.size() );
			while ( it.hasPrevious() ) {
				queue.addLast( it.previous() );
			}						
		}
		
	}
	
	static class PreOrderIterator extends CommonIterator {
		
		public PreOrderIterator( @NonNull MinXMLWalker walker, @NonNull MinXML subject ) {
			super( walker, subject );
		}	
		
		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				if ( end_walk_marker != queue.getLast() ) return true;
				queue.removeLast();
				this.walker.endWalk( queue.removeLast() );
			}
			return false;
		}
		
		@Override
		public @NonNull MinXML next() {
			final MinXML x = queue.removeLast();
			if ( x != end_walk_marker ) {
				this.expand( x );
				return x;
			}
			this.walker.endWalk( queue.removeLast() );
			this.hasNext();
			return queue.removeLast();
		}
		
	}
	
	public Iterable< @NonNull MinXML > preOrder( final @NonNull MinXML subject ) {
		return new Iterable< @NonNull MinXML >() {
			@Override
			public Iterator< @NonNull MinXML > iterator() {
				return new PreOrderIterator( MinXMLWalker.this, subject );
			}
		};
	}
	
	static class PostOrderIterator extends CommonIterator {
	
		public PostOrderIterator( @NonNull MinXMLWalker walker, @NonNull MinXML subject) {
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
				MinXML e = queue.removeLast();
				this.expand( e );
			}
			return false;
		}
		

		/**
		 * After this method the queue is NOT normal. A call to
		 * hasNext would sort it out.
		 */
		@Override
		public @NonNull MinXML next() {
			final MinXML x = queue.removeLast();
			if ( x == end_walk_marker ) {
				//	The queue is normalised.
				//	This is the overwhelmingly common case - so we special case it.
				final MinXML e = queue.removeLast();
				this.walker.endWalk( e );
				return e;
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

	
	public Iterable< @NonNull MinXML > postOrder( final @NonNull MinXML subject ) {
		return new Iterable< @NonNull MinXML >() {
			@Override
			public Iterator< @NonNull MinXML > iterator() {
				return new PostOrderIterator( MinXMLWalker.this, subject );
			}
		};
	}
	
}
