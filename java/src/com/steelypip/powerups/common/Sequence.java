package com.steelypip.powerups.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.steelypip.powerups.alert.Alert;

public interface Sequence< T > extends Iterable< T > {
	
	static <U> Sequence< U > fromIterable( Iterable< U > list ) {
		return new Sequence< U >() {
			@Override
			public Iterator< U > iterator() {
				return list.iterator();
			}
		};
	}
	
	default Sequence< T > copy() {
		final ArrayList< T > list = new ArrayList<>(); 
		this.forEach( list::add );
		return fromIterable( list );
	}
		
	default boolean hasAtLeast( int n ) {
		int count = 0;
		if ( count >= n ) return true;
		for ( @SuppressWarnings("unused") T dummy : this ) {
			count += 1;
			if ( count >= n ) return true;
		}
		return false;
	}
	
	default Integer count( final int giveUp ) {
		int count = 0;
		if ( count > giveUp ) return null;
		for ( @SuppressWarnings("unused") T dummy : this ) {
			count += 1;
			if ( count > giveUp ) return null;			
		}
		return count;
	}
	
	default boolean isEmpty() {
		for ( @SuppressWarnings("unused") T dummy : this ) {
			return false;
		}
		return true;	
	}

	default Sequence< T > filter( Predicate< T > pred ) {
		return new Sequence< T >() {
			@Override
			public Iterator< T > iterator() {
				return new Iterator< T >() {
					final Iterator< T > base = Sequence.this.iterator();
					T peeked = null;
					boolean has_peeked = false;
					@Override
					public boolean hasNext() {
						for (;;) {
							if ( has_peeked ) return true; 
							if ( ! base.hasNext() ) return false;
							final T peek = base.next();
							if ( ! pred.test( peek ) ) continue;
							this.peeked = peek;
							this.has_peeked = true;
							return true;
						}
					}
					@Override
					public T next() {
						if ( has_peeked || this.hasNext() ) {
							final T popped = this.peeked;
							this.peeked = null;
							this.has_peeked = false;
							return popped;
						} else {
							throw new Alert( "Trying to get next item from exhausted Iterator" );
						}
					}
				};
			}			
		};
	}
	
	default < U > Sequence< U > map( Function< T, U >  func ) {
		return new Sequence< U >() {
			@Override
			public Iterator< U > iterator() {
				return new Iterator< U >() {
					final Iterator< T > base = Sequence.this.iterator();
					@Override
					public boolean hasNext() {
						return base.hasNext();
					}
					@Override
					public U next() {
						return func.apply( base.next() );

					}
				};
			}			
		};		
	}

	default void forEach( Consumer<? super T> action ) {
		for ( T t : this ) {
			action.accept( t );
		}
	}
	
    default Sequence<T> peek( Consumer<? super T> action ) {
    	return new Sequence<T>() {
			@Override
			public Iterator< T > iterator() {
				Iterator< T > it = Sequence.this.iterator();
				return new Iterator< T >() {

					@Override
					public boolean hasNext() {
						return it.hasNext();
					}

					@Override
					public T next() {
						T t = it.next();
						action.accept( t );
						return t;
					}
					
				};
			}
    	};
    }

    default Stream< T > stream() {
    	return StreamSupport.stream( this.spliterator(), false );
    }
    
    default List< T > toList() {
    	final ArrayList< T > list = new ArrayList< T >();
    	this.forEach( list::add ); 
    	return list;
    }

}
