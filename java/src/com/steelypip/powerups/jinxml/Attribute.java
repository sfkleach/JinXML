package com.steelypip.powerups.jinxml;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.jinxml.stdmodel.StdAttribute;

/**
 * Attributes represent a (key, value) pair. All attributes of an 
 * Element are considered to have a key.
 *
 */
public interface Attribute {
	
	static Attribute newAttribute( @NonNull String key, @NonNull String value ) {
		return new StdAttribute( key, value );
	}
	

	
	/**
	 * The getter for the key of an attribute.
	 * @return the key
	 */
	@NonNull String getKey();

	/**
	 * The getter for the value of an attribute.
	 * @return the child
	 */
	@NonNull String getValue();

	
	static Attribute.Iterable fromIterable( final java.lang.Iterable< Attribute > it ) {
		return new Attribute.Iterable() {

			@Override
			public Iterator< Attribute > iterator() {
				return it.iterator();
			}
			
		};
	}
	
	/**
	 * The Attribute.Iterable class implements the main way to iterate over the 
	 * attributes of an element and is returned from Element.attributes(). In addition 
	 * to being an Iterable&lt;Attribute&gt;, it also implements two filtering methods:
	 * with(Predicate&lt;Attribute&gt;) and uniqueSelector(). 
	 *
	 */	
	static public interface Iterable extends java.lang.Iterable< Attribute > {

		/**
		 * Filters an Attribute.Iterable using a predicate to test each attribute
		 * in turn. Only the ones that pass the test are included.
		 * @param pred the test predicate
		 * @return a Attribute.Iterable that performs the filtering dynamically
		 */
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
							if ( ! pred.test( peek ) ) return this.hasNext();
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

		/**
		 * Filters a Attribute.Iterable, removing the attributes whose key has already
		 * been seen. The effect is that the members each include a different key.
		 * @return a Attribute.Iterable that dynamically thins out duplicates.
		 */
		default Attribute.Iterable uniqueKey() {
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
