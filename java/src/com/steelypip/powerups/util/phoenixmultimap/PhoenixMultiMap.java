package com.steelypip.powerups.util.phoenixmultimap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.Sequence;
import com.steelypip.powerups.util.phoenixmultimap.frozen.EmptyFrozenPMMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.EmptyMutablePMMap;

/**
 * A PhoenixMultiMap is a mutable collection of key-to-value entries. Entries are considered
 * to be ordered, with the entries being ordered by key. A destructive object implements all
 * update operations by returning an object which might or might not be new - but the old value
 * is invalidated and you are obliged to continue with the returned value ("phoenixed"). This 
 * mutation pattern is designed for uniquely referenced values (i.e. components), such as lists, 
 * whose implementation needs to be dynamically varied.
 * @author steve
 *
 * @param <K>
 * @param <V>
 */
public interface PhoenixMultiMap< K, V > extends Iterable< Map.Entry< K, V > > {
		
	default PhoenixMultiMap< K, V > mutableCopy() {
		PhoenixMultiMap< K, V > sofar = new EmptyMutablePMMap< K, V >();
		for ( Map.Entry< ? extends K, ? extends V > e : this ) {
			final K key = e.getKey();
			sofar = sofar.add( Objects.requireNonNull( key ), e.getValue() );
		}
		return sofar;
	}
	
	default PhoenixMultiMap< K, V > frozenCopyUnlessFrozen() {
		if ( this.isMutable() ) {
			return this.mutableCopy().freezeByPhoenixing();
		} else {
			return this;
		}
	}
	
	default boolean isMutable() {
		return this instanceof MutableMarkerInterface;
	}
	
	default boolean isFrozen() {
		return this instanceof FrozenMarkerInterface;
	}
	
	static < K, V > PhoenixMultiMap< K, V > newEmptyPhoenixMultiMap() {
		return new EmptyMutablePMMap< K, V >();
	}

	static < K, V > PhoenixMultiMap< K, V > newEmptyPhoenixMultiMap( boolean frozen ) {
		return frozen ? new EmptyFrozenPMMap< K, V >() : new EmptyMutablePMMap< K, V >();
	}
	
	static < K, V > PhoenixMultiMap< K, V > newPhoenixMultiMap( Map< ? extends K, ? extends V > map ) {
		PhoenixMultiMap< K, V > pmmap = new EmptyMutablePMMap< K, V >();
		for ( Entry< ? extends K, ? extends V > e : map.entrySet() ) {
			pmmap = pmmap.add( e );
		}
		return pmmap;
	}

	static < K, V > PhoenixMultiMap< K, V > newPhoenixMultiMap( Map< ? extends K, ? extends V > map, boolean freeze ) {
		PhoenixMultiMap< K, V > result = new EmptyMutablePMMap< K, V >();
		result.addAllEntries( map.entrySet() );
		return result;
	}

	static < K, V > PhoenixMultiMap< K, V > newPhoenixMultiMap( PhoenixMultiMap< ? extends K, ? extends V > ppmap, boolean freeze ) {
		PhoenixMultiMap< K, V > result = new EmptyMutablePMMap< K, V >();
		result.addAll( ppmap );
		if ( freeze ) {
			result = result.freezeByPhoenixing();
		}
		return result;
	}

	

	/** 
	 * Removes all key-value pairs from the multimap, leaving it empty. If the
	 * PhoenixMultiMap is immutable, it throws an UnsupportedOperationException.
	 */
	PhoenixMultiMap< K, V > clearAllEntries();
	
	/**
	 * Returns true if this multimap contains at least one key-value pair with the 
	 * key key and the value value.
	 * @param key
	 * @param value
	 * @return
	 */
	default boolean	hasEntry( @NonNull K key, V value ) {
		return this.getAll( key ).contains( value );
	}
	
	default boolean	hasEntry( @NonNull K key, int index, V value ) {
		try {
			V v = this.getAll( key ).get( index );
			return v == null ? value == null : v.equals( value );
		} catch ( IndexOutOfBoundsException _e ) {
			return false;
		}
	}
	
	/** 
	 * Returns true if this multimap contains at least one key-value pair with the key key.
	 */
	default boolean	hasKey( @NonNull K key ) {
		try {
			this.getOrFail( key );
			return true;
		} catch ( IllegalArgumentException _e ) {
			return false;
		} catch ( IndexOutOfBoundsException _e ) {
			return false;
		}
	}

	/* Returns true if this multimap contains at least one key-value pair with the value value.*/
	default boolean	hasValue( V value ) {
		for ( Map.Entry< ? extends K, ? extends V > p : this.entriesToList() ) {
			V v = p.getValue();
			if ( v == null ? value == null : v.equals( value ) ) return true;
		}
		return false;
	}
	
	/** 
	 * Returns list of all key-value pairs contained in this 
	 * multimap/ 
	 */
	List< Map.Entry< K, V > > entriesToList();
	
	default Iterator< Map.Entry< K, V > > iterator() {
		return this.entriesToList().iterator();
	}
	
	default Sequence< Entry< K, V > > entriesToIterable() {
		return Sequence.fromIterable( this.entriesToList() );
	}

	default Sequence< K > keysToIterable() {
		return Sequence.fromIterable( this.keySet() );
	}

	default Sequence< V > valuesToIterable( K key ) {
		return Sequence.fromIterable( this.getAll( key ) );
	}
	
	default Sequence< Map.Entry< K, V > > oneEntryPerKey() {
		final Set< K > seen = new TreeSet<>();
		return this.entriesToIterable().filter(
			( Map.Entry< K, V > t ) -> {
				K k = t.getKey();
				boolean unseen = ! seen.contains( k );
				if ( unseen ) {
					seen.add( k );
				}
				return unseen;				
			}
		);
	}
	
	default Stream< Map.Entry< K, V > > stream() {
		return this.entriesToList().stream();
	}
	
	/**
	 *	Compares the specified object with this multimap for equality.  
	 */
	boolean	equals( Object obj );
	
	/** Returns a list of the values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key
	 * @return list of the values associated with key
	 */
	List< V > getAll( @NonNull K key );

	
	/** Returns the first value associated with key in 
	 * this multimap
	 * @param key
	 * @return first value associated with key
	 */
	V getOrFail( @NonNull K key ) throws IllegalArgumentException;
	
	/** Returns the first value associated with key in 
	 * this multimap
	 * @param key the key to look up, may not be null
	 * @return first value associated with key
	 */
	V getElse( @NonNull K key, V otherwise ) throws IllegalArgumentException;
	
	/** Returns the Nth values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key the key to look up, may not be null
	 * @param N the position of the value in the list of values associated with key
	 * @return the Nth value associated with key
	 */
	V getOrFail( @NonNull K key, int N ) throws IllegalArgumentException;
	
	/** Returns the Nth values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key the key to look up, may not be null
	 * @param N the position of the value in the list of values associated with key
	 * @return the Nth value associated with key
	 */
	V getElse( @NonNull K key, int N, V otherwise ) throws IllegalArgumentException;
	
	/** Returns the Nth values associated with key in 
	 * this multimap, if any, that is guaranteed to be a new list.
	 * @param key the key to look up, may not be null
	 * @param reverse a flag indicating that the position is taken from the end of the list
	 * @param N the position of the value in the list of values associated with key
	 * @return the Nth value associated with key
	 */
	V getElse( @NonNull K key, boolean reverse, int N, V otherwise ) throws IllegalArgumentException;
	
	/**
	 * Returns true if this multimap contains no key-value pairs. 
	 */
	default boolean	isEmpty() {
		return this.sizeEntries() == 0;
	}
	
	
	/**
	 * Returns the set of all distinct keys contained in this multimap.
	 */
	default Set< K > keySet() {
		return this.entriesToList().stream().map( ( Map.Entry< ? extends K, ? extends V > p ) -> p.getKey() ).collect( Collectors.toSet() );
	}
		
	/**
	 * Stores a key-value pair in this multimap.
	 */
	PhoenixMultiMap< K, V > add( @NonNull K key, V value );
	
	/**
	 * Stores a key-value pair in this multimap.
	 */
	default PhoenixMultiMap< K, V > add( Entry< ? extends K, ? extends V > e  ) {
		final K key = e.getKey();
		return this.add( Objects.requireNonNull( key ), e.getValue() );
	}
	
	/**
	 * Stores a key-value pair in this multimap for each of values, all using the same key, key. 
	 * @param key
	 * @param values
	 * @return
	 */
	default PhoenixMultiMap< K, V > addAll( @NonNull K key, Iterable< ? extends V > values ) {
		PhoenixMultiMap< K, V > self = this;
		for ( V v : values ) {
			self = self.add( key, v );
		}
		return self;		
	}
	
	/**
	 * Stores a key-value pair in this multimap for each of values, in the order supplied. 
	 * @param values
	 * @return
	 */
	default PhoenixMultiMap< K, V > addAllEntries( Iterable< ? extends Map.Entry< ? extends K, ? extends V > > values ) {
		PhoenixMultiMap< K, V > self = this;
		for ( Map.Entry< ? extends K, ? extends V > e : values ) {
			self = self.add( e );
		}
		return self;
	}

	
	/**
	 * Stores all key-value pairs of multimap in this multimap, in the order 
	 * returned by multimap.entries().
	 */
	default PhoenixMultiMap< K, V > addAll( PhoenixMultiMap< ? extends K, ? extends V > multimap ) {
		PhoenixMultiMap< K, V > self = this;
		for ( Map.Entry< ? extends K, ? extends V > p : multimap.entriesToList() ) {
			self = self.add( p );
		}
		return self;
	}
	
	/** 
	 * Removes a single key-value pair with the key key and the value value 
	 * from this multimap, if such exists.
	 */
	PhoenixMultiMap< K, V > removeEntry( @NonNull K key, V value );
	
	/** 
	 * Removes a single key-value pair with the key key and the Nth value 
	 * from this multimap, if such exists.
	 */
	PhoenixMultiMap< K, V > removeEntryAt( @NonNull K key, int N );
	
		
	/**
	 * Removes all values associated with the key key.
	 * @param key
	 * @return
	 */
	PhoenixMultiMap< K, V > removeEntries( @NonNull K key );
	
	/**
	 * Stores a collection of values with the same key, replacing any existing 
	 * values for that key.
	 * @param key
	 * @param values
	 * @return
	 */
	PhoenixMultiMap< K, V > setValues( @NonNull K key, Iterable<? extends V> values );
	
	/**
	 * After this assignment, the key has one and only one value, regardless of previous values.
	 * @param key the target to assign to
	 * @param value the value to replace all other values
	 * @return the assigned value (the subject is now defunct)
	 */
	PhoenixMultiMap< K, V > setSingletonValue( @NonNull K key, V value );
	
	/**
	 * updateValue requires that there is already an n-th entry with the specified key. 
	 * @param key the target to assign to
	 * @param n the position of the value to replace
	 * @param value the replacement value
	 * @return the assigned value (the subject is now defunct)
	 */
	PhoenixMultiMap< K, V > updateValue( @NonNull K key, int n, V value );

	/**
	 * Returns the number of key-value pairs in this multimap.
	 */
	int	sizeEntries();
	
	/**
	 * Returns the number of key-value pairs in this multimap that share key key.
	 */
	int	sizeEntriesWithKey( @NonNull K key );
	
	default int sizeKeys() {
		return this.keySet().size();
	}
	
	/**
	 * Returns a list containing the value from each key-value pair 
	 * contained in this multimap, without collapsing duplicates (so values().size() == size()). 
	 */
	default List< V > valuesList() {
		return this.entriesToList().stream().map( ( Map.Entry< ? extends K, ? extends V > p ) -> p.getValue() ).collect( Collectors.toList() );
	}
	
	PhoenixMultiMap< K, V > freezeByPhoenixing();
	
	default PhoenixMultiMap< K, V > trimToSize() {
		return this;
	}

}
