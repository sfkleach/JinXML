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

import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.EmptyIterator;

/**
 * A convenience class for implementing a recursive walk of a tree
 * with some control over the branches explored.
 */
public abstract class MinXMLSearcher {

	/**
	 * startSearch is called at the start of the tree-walk of the subject and its children. 
	 * Return false if you want the the child elements to be visited, true otherwise.
	 * 
	 * @param subject the MinXML element to be visited
	 * @return flag saying if the children were cutoff.
	 */
	public abstract boolean startSearch( MinXML subject );

	
	/**
	 * endSearch is called at the end of the tree-walk of the subject and its children.
	 * Return false normally but true to signal to the parent that the iteration over
	 * the child nodes can be stopped early. The found flag indicates if
	 * any child search found the goal, it is the short-circuit OR of all the
	 * flags returned by the children's endVisits.
	 * 
	 * @param subject the MinXML element to be visited.
	 * @param cutoff flag indicating if any child search was cutoff.
	 * @return boolean a flag saying if sibling-search should be cutoff.
	 */
	public abstract boolean endSearch( MinXML subject, boolean cutoff );
	
	private static final Iterator< MinXML > empty = new EmptyIterator< MinXML >();
	
	/**
	 * The search method is used to implement basic recursive scans over a tree
	 * of elements. It is typically used to search a tree or to implement a series of
	 * in-place updates.
	 * 
	 * @param subject the element tree to be searched
	 * @return a successful node, otherwise null.
	 */
	public @Nullable MinXML search( final MinXML subject ) {
		MinXML cutoff = this.startSearch( subject ) ? subject : null;
		final Iterator< MinXML > kids = cutoff != null ? empty : subject.iterator();
		while ( cutoff == null && kids.hasNext() ) {
			cutoff = this.search( kids.next() );
		}
		return this.endSearch( subject, cutoff != null ) ? ( cutoff != null ? cutoff : subject ) : null;
	}
	
	/**
	 * This are a pair of markers value used to mark the element 'below' it (lower index)
	 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
	 * represent 'startWalk + expand' tasks. The choice of marker is used to signal
	 * true/false.
	 */
	private static final MinXML cutoff_true = new BadMinXML();
	private static final MinXML cutoff_false = new BadMinXML();
	
	static abstract class CommonMethodsPreOrderIterator implements Iterator< MinXML > {
		protected final MinXMLSearcher searcher;
		protected final Deque< MinXML > queue = new ArrayDeque<>();
		
		public CommonMethodsPreOrderIterator( MinXMLSearcher searcher, MinXML subject ) {
			this.searcher = searcher;
			this.queue.add( subject );
		}			

		protected void cutAwaySiblings() {
			//	We have to cut away the siblings.
			while ( ! queue.isEmpty() ) {
				final MinXML last = queue.removeLast();
				if ( cutoff_false == last || cutoff_true == last ) break;
			}
			queue.addLast( cutoff_true );
		}
		
		protected void expand( final MinXML x ) {
			final boolean cutoff = this.searcher.startSearch( x );
			queue.addLast( x );
			queue.addLast( cutoff ? cutoff_true : cutoff_false );
			if ( ! cutoff ) {
				final ListIterator< MinXML > it = x.listIterator( x.size() );
				while ( it.hasPrevious() ) {
					queue.addLast( it.previous() );
				}				
			}
		}
	
	}
	
	static class SearcherPreOrderIterator extends CommonMethodsPreOrderIterator {
		
		public SearcherPreOrderIterator( MinXMLSearcher searcher, MinXML subject ) {
			super( searcher, subject );
		}	

		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				final MinXML last = queue.getLast();
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
		public MinXML next() {
			final MinXML x = queue.removeLast();
			if ( cutoff_false != x && cutoff_true != x ) {
				this.expand( x );
				return x;
			} else {
				queue.addLast( x );
				this.hasNext();
				return this.next();
			}
		}
		
	}
	
	public Iterable< MinXML > preOrder( final MinXML subject ) {
		return new Iterable< MinXML >() {
			@Override
			public Iterator< MinXML > iterator() {
				return new SearcherPreOrderIterator( MinXMLSearcher.this, subject );
			}
		};
	}
	
	static class SearcherPostOrderIterator extends CommonMethodsPreOrderIterator {
		
		public SearcherPostOrderIterator( MinXMLSearcher searcher, MinXML subject ) {
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
				final MinXML last = queue.getLast();
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
		public MinXML next() {
			final MinXML x = queue.removeLast();
			if ( cutoff_false == x || cutoff_true == x ) {
				final MinXML last = queue.removeLast();
				final boolean cutoff = this.searcher.endSearch( last, cutoff_true == x );
				if ( cutoff ) {
					this.cutAwaySiblings();
				}
				return last;
			} else {
				queue.addLast( x );
				this.hasNext();
				return this.next();
			}
		}

	}
	
	public Iterable< MinXML > postOrder( final MinXML subject ) {
		return new Iterable< MinXML >() {
			@Override
			public Iterator< MinXML > iterator() {
				return new SearcherPostOrderIterator( MinXMLSearcher.this, subject );
			}
		};
	}
	
}
