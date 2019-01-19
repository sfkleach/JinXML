/**
 * Copyright Stephen Leach, 2014
 * This file is part of the Value for Java library.
 * 
 * Value for Java is free software: you can redistribute it and/or modify
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
 * along with Value for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */

package com.steelypip.powerups.hydraxml;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.steelypip.powerups.common.EmptyIterator;


/**
 * A convenience class for implementing a recursive walk of a tree
 * with some control over the branches explored.
 */
public abstract class AbsMultiTreeSearcher< Key, Value > {

	public abstract Key defaultKey();
	public abstract Value badValue();
	public abstract Iterable< Map.Entry< Key, Value > > entries( Value v );
	public abstract List< Map.Entry< Key, Value > > linksToList( Value v );

	/**
	 * startSearch is called at the start of the tree-walk of the subject and its children. 
	 * Return false if you want the the child elements to be visited, true otherwise.
	 * 
	 * @param subject the Value element to be visited
	 * @return flag saying if the children were cutoff.
	 */
	public boolean startSearch( Map.Entry< Key, Value > e ) {
		return this.startSearch( e.getKey(), e.getValue() );
	}
	
	public abstract boolean startSearch( Key field, Value child );

	
	/**
	 * endSearch is called at the end of the tree-walk of the subject and its children.
	 * Return false normally but true to signal to the parent that the iteration over
	 * the child nodes can be stopped early. The found flag indicates if
	 * any child search found the goal, it is the short-circuit OR of all the
	 * flags returned by the children's endVisits.
	 * 
	 * @param subject the Value element to be visited.
	 * @param cutoff flag indicating if any child search was cutoff.
	 * @return boolean a flag saying if sibling-search should be cutoff.
	 */
	public abstract boolean endSearch( Key field, Value subject, boolean cutoff );
	
	public boolean endSearch( Map.Entry< Key, Value > e, boolean cutoff ) {
		return this.endSearch( e.getKey(), e.getValue(), cutoff );
	}
		
	/**
	 * The search method is used to implement basic recursive scans over a tree
	 * of elements. It is typically used to search a tree or to implement a series of
	 * in-place updates.
	 * 
	 * @param subject the element tree to be searched
	 * @return a successful node, otherwise null.
	 */
	@SuppressWarnings("null")
	public Value search( Key field, Value value ) {
		Value cutoff = this.startSearch( field, value ) ? value : (Value)null;
		final Iterator< Map.Entry< Key, Value > > kids = cutoff != null ? new EmptyIterator< Map.Entry< Key, Value > >() : this.entries( value ).iterator();
		while ( cutoff == null && kids.hasNext() ) {
			cutoff = this.search( kids.next() );
		}
		return this.endSearch( field, value, cutoff != null ) ? ( cutoff != null ? cutoff : value ) : (Value)null;
	}
	
	public Value search( final Map.Entry< Key, Value > link ) {
		return this.search( link.getKey(), link.getValue() );
	}
	
	/**
	 * This are a pair of markers value used to mark the element 'below' it (lower index)
	 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
	 * represent 'startWalk + expand' tasks. The choice of marker is used to signal
	 * true/false.
	 */
	
	static abstract class CommonMethodsPreOrderIterator< Key, Value > implements Iterator< Value > {
		protected final AbsMultiTreeSearcher< Key, Value > searcher;
		protected final Deque< Map.Entry< Key, Value > > queue = new ArrayDeque<>();
		protected final Map.Entry< Key, Value > cutoff_true;  
		protected final Map.Entry< Key, Value > cutoff_false;  
		
		public CommonMethodsPreOrderIterator( AbsMultiTreeSearcher< Key, Value > searcher, Value subject ) {
			this.searcher = searcher;
			this.queue.add( new StdLink< Key, Value >( this.searcher.defaultKey(), 0, subject ) );
			this.cutoff_true = new StdLink< Key, Value >( this.searcher.defaultKey(), 0, searcher.badValue() );
			this.cutoff_false = new StdLink< Key, Value >( this.searcher.defaultKey(), 0, searcher.badValue() );
		}			

		protected void cutAwaySiblings() {
			//	We have to cut away the siblings.
			while ( ! queue.isEmpty() ) {
				final Map.Entry< Key, Value > last = queue.removeLast();
				if ( cutoff_false == last || cutoff_true == last ) break;
			}
			queue.addLast( cutoff_true );
		}
		
		protected void expand( Map.Entry< Key, Value > e ) {
			final boolean cutoff = this.searcher.startSearch( e );
			queue.addLast( e );
			queue.addLast( cutoff ? cutoff_true : cutoff_false );
			if ( ! cutoff ) {
				final ListIterator< Map.Entry< Key, Value > > it = this.searcher.linksToList( e.getValue() ).listIterator();
				while ( it.hasPrevious() ) {
					queue.addLast( it.previous() );
				}				
			}
		}
	
	}
	
	static class SearcherPreOrderIterator< Key, Value > extends CommonMethodsPreOrderIterator< Key, Value > {
		
		public SearcherPreOrderIterator( AbsMultiTreeSearcher< Key, Value > searcher, Value subject ) {
			super( searcher, subject );
		}	

		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				final Map.Entry< Key, Value > last = queue.getLast();
				if ( cutoff_false != last && cutoff_true != last ) return true;
				queue.removeLast();
				final boolean cutoff = this.searcher.endSearch( queue.removeLast(), cutoff_true == last );
				if ( cutoff ) {
					this.cutAwaySiblings();
				}
			}
			return false;
		}

		@Override
		public Value next() {
			final Map.Entry< Key, Value > x = queue.removeLast();
			if ( cutoff_false != x && cutoff_true != x ) {
				this.expand( x );
				return x.getValue();
			} else {
				queue.addLast( x );
				this.hasNext();
				return this.next();
			}
		}
		
	}
	
	public Iterable< Value > preOrder( final Value subject ) {
		return new Iterable< Value >() {
			@Override
			public Iterator< Value > iterator() {
				return new SearcherPreOrderIterator< Key, Value >( AbsMultiTreeSearcher.this, subject );
			}
		};
	}
	
	static class SearcherPostOrderIterator< Key, Value > extends CommonMethodsPreOrderIterator< Key, Value > {
		
		public SearcherPostOrderIterator( AbsMultiTreeSearcher< Key, Value > searcher, Value subject ) {
			super( searcher, subject );
		}	
		
		/**
		 * This not only returns if there is a next member but normalises
		 * the queue. In the case of a post-order traversal, the queue is
		 * normal we
		 */
		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				final Map.Entry< Key, Value > last = queue.getLast();
				if ( cutoff_false == last || cutoff_true == last ) return true;
				this.expand( queue.removeLast() );
			}
			return false;
		}
		
		/**
		 * After this method the queue is NOT normal. A call to
		 * hasNext will sort it out.
		 */
		@Override
		public Value next() {
			final Map.Entry< Key, Value > x = queue.removeLast();
			if ( cutoff_false == x || cutoff_true == x ) {
				final Map.Entry< Key, Value > last = queue.removeLast();
				final boolean cutoff = this.searcher.endSearch( last, cutoff_true == x );
				if ( cutoff ) {
					this.cutAwaySiblings();
				}
				return last.getValue();
			} else {
				queue.addLast( x );
				this.hasNext();
				return this.next();
			}
		}

	}
	
	public Iterable< Value > postOrder( final Value subject ) {
		return new Iterable< Value >() {
			@Override
			public Iterator< Value > iterator() {
				return new SearcherPostOrderIterator< Key, Value >( AbsMultiTreeSearcher.this, subject );
			}
		};
	}
	
}
