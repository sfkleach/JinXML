package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.Iterator;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.phoenixmultimap.MutableMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.frozen.EmptyFrozenPMMap;

public class EmptyMutablePMMap< K, V > extends AbsEmptyMutablePMMap< K, V > implements MutableMarkerInterface {
	
	public static EmptyMutablePMMap< Object, Object > INSTANCE = new EmptyMutablePMMap< Object, Object >();
	
	@SuppressWarnings("unchecked")
	public static < K, V > EmptyMutablePMMap< K, V > getInstance() { return (EmptyMutablePMMap< K, V >) INSTANCE; }

	@Override
	public EmptyMutablePMMap< K, V > clearAllEntries() {
		return this;
	}


	@Override
	public SingleEntryMutablePMMap< K, V > add( @NonNull K key, V value ) {
		return new SingleEntryMutablePMMap< K, V >( key, value );
	}

	@Override
	public PhoenixMultiMap< K, V > addAll( @NonNull K key, Iterable< ? extends V > values ) {
		final Iterator< ? extends V > it = values.iterator();
		if ( ! it.hasNext() ) return this;
		V value = it.next();
		if ( it.hasNext() ) {
			PhoenixMultiMap< K, V > m = new SharedKeyMutablePMMap< K, V >( key ).add( value ).add( it.next() );
			while ( it.hasNext() ) {
				m.add( key, it.next() );
			}
			return m;
		} else {
			return new SingleEntryMutablePMMap< K, V >( key, value );
		}
	}

	@Override
	public EmptyMutablePMMap< K, V > removeEntry( Object key, Object value ) {
		return this;
	}

	@Override
	public EmptyMutablePMMap< K, V > removeEntryAt( Object key, int N ) {
		return this;
	}

	@Override
	public EmptyMutablePMMap< K, V > removeEntries( Object key ) {
		return this;
	}

	@Override
	public PhoenixMultiMap< K, V > setValues( @NonNull K key, Iterable< ? extends V > values ) {
		return this.addAll( key, values );
	}

	@Override
	public PhoenixMultiMap< K, V > setSingletonValue( @NonNull K key, V value ) {
		return new SingleEntryMutablePMMap< K, V >( key, value );
	}

	@Override
	public PhoenixMultiMap< K, V > updateValue( @NonNull K key, int n, V value ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PhoenixMultiMap< K, V > freezeByPhoenixing() {
		return EmptyFrozenPMMap.INSTANCE;
	}

	
	
}
