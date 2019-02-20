package com.steelypip.powerups.jinxml;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import com.steelypip.powerups.alert.Alert;

public interface Member {
	
	String getSelector();
	Element getChild();
	
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
	
	static public interface Iterable extends java.lang.Iterable< Member > {
		
		default Member.Iterable with( Predicate< Member > pred ) {
			return new Member.Iterable() {

				@Override
				public Iterator< Member > iterator() {
					return new Iterator< Member >() {
						
						final Iterator< Member > base = Iterable.this.iterator();
						Member peeked = null;

						@Override
						public boolean hasNext() {
							if ( peeked != null ) return true; 
							if ( ! base.hasNext() ) return false;
							final Member peek = base.next();
							if ( pred.test( peek ) ) return this.hasNext();
							this.peeked = peek;
							return true;
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
		
		default Member.Iterable uniqueSelector() {
			return this.with( new Predicate< Member >() {
				
				final Set< String > seen = new TreeSet<>();

				@Override
				public boolean test( Member t ) {
					String s = t.getSelector();
					boolean result = this.seen.contains( s );
					this.seen.add( s );
					return result;
				}
				
			} );
		}
	}

}
