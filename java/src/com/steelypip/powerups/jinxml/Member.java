package com.steelypip.powerups.jinxml;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.jinxml.stdmodel.StdMember;

/**
 * Members represent a (selector, element) pair. All children of an 
 * Element are considered to have a selector, the default selector
 * being the empty string, Element.DEFAULT_SELECTOR.
 *
 */
public interface Member {
	
	static Member newMember( @NonNull String selector, @NonNull Element child ) {
		return new StdMember( selector, child );
	}
	
	/**
	 * The getter for the selector of a member
	 * @return the selector.
	 */
	@NonNull String getSelector();
	
	/**
	 * The getter for the child of a member.
	 * @return the child
	 */
	@NonNull Element getChild();
	
	/**
	 * A method for checking if a member has an empty selector.
	 * @return true if the selector equals Element.DEFAULT_SELECTOR.
	 */
	default boolean hasDefaultSelector() {
		return this.getSelector() == Element.DEFAULT_SELECTOR;
	}
	
	static Member.Iterable fromIterable( final java.lang.Iterable< Member > it ) {
		return new Member.Iterable() {

			@Override
			public Iterator< Member > iterator() {
				return it.iterator();
			}
			
		};
	}
	
	/**
	 * The Member.Iterable class implements the main way to iterate over the 
	 * members of an element and is returned from Element.members(). In addition 
	 * to being an Iterable&lt;Member&gt;, it also implements two filtering methods:
	 * with(Predicate&lt;Member&gt;) and uniqueSelector(). 
	 *
	 */
	static public interface Iterable extends java.lang.Iterable< Member > {
		
		/**
		 * Filters a Member.Iterable using a predicate to test each member
		 * in turn. Only the ones that pass the test are included.
		 * @param pred the test predicate
		 * @return a Member.Iterable that performs the filtering dynamically
		 */
		default Member.Iterable with( final Predicate< Member > pred ) {
			return new Member.Iterable() {

				@Override
				public Iterator< Member > iterator() {
					return new Iterator< Member >() {
						
						final Iterator< Member > base = Iterable.this.iterator();
						Member peeked = null;

						@Override
						public boolean hasNext() {
							for (;;) {
								if ( peeked != null ) return true; 
								if ( ! base.hasNext() ) return false;
								final Member peek = base.next();
								if ( ! pred.test( peek ) ) continue;
								this.peeked = peek;
								return true;
							}
						}

						@Override
						public Member next() {
							if ( this.peeked != null || this.hasNext() ) {
								final Member popped = this.peeked;
								this.peeked = null;
								return popped;
							} else {
								throw new Alert( "Trying to get next item from exhausted Iterator" );
							}
						}
						
					};
				}
				
			};
		}
		
		/**
		 * Filters a Member.Iterable, removing the members whose selector has already
		 * been seen. The effect is that the members each include a different selector.
		 * @return a Member.Iterable that dynamically thins out duplicates.
		 */
		default Member.Iterable uniqueSelector() {
			return this.with( new Predicate< Member >() {
				
				final Set< String > seen = new TreeSet<>();

				@Override
				public boolean test( Member t ) {
					String s = t.getSelector();
					boolean seen_before = this.seen.contains( s );
					if ( ! seen_before ) {
						this.seen.add( s );
					}
					return ! seen_before;
				}
				
			} );
		}
	}


}
