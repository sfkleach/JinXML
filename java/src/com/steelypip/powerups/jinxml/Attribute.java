package com.steelypip.powerups.jinxml;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import com.steelypip.powerups.alert.Alert;

public interface Attribute {
	
	String getKey();
	
	String getValue();

	
	static Attribute.Iterable fromIterable( final java.lang.Iterable< Attribute > it ) {
		return new Attribute.Iterable() {

			@Override
			public Iterator< Attribute > iterator() {
				return it.iterator();
			}
			
		};
	}
	
	static public interface Iterable extends java.lang.Iterable< Attribute > {
		
		default Attribute.Iterable with( Predicate< Attribute > pred ) {
			return new Attribute.Iterable() {

				@Override
				public Iterator< Attribute > iterator() {
					return new Iterator< Attribute >() {
						
						final Iterator< Attribute > base = Iterable.this.iterator();
						Attribute peeked = null;

						@Override
						public boolean hasNext() {
							if ( peeked != null ) return true; 
							if ( ! base.hasNext() ) return false;
							final Attribute peek = base.next();
							if ( pred.test( peek ) ) return this.hasNext();
							this.peeked = peek;
							return true;
						}

						@Override
						public Attribute next() {
							if ( this.peeked != null || this.hasNext() ) {
								final Attribute popped = this.peeked;
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
		
		default Attribute.Iterable uniqueSelector() {
			return this.with( new Predicate< Attribute >() {
				
				final Set< String > seen = new TreeSet<>();

				@Override
				public boolean test( Attribute t ) {
					String s = t.getKey();
					boolean result = this.seen.contains( s );
					this.seen.add( s );
					return result;
				}
				
			} );
		}
	}

	
	
}
