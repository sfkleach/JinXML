package com.steelypip.powerups.util.phoenixmultimap.frozen;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.phoenixmultimap.FrozenMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsEmptyMutablePMMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.SingleEntryMutablePMMap;

public class EmptyFrozenPMMap< K, V > extends AbsEmptyMutablePMMap< K, V > implements FrozenMarkerInterface {
	
	@SuppressWarnings("rawtypes")
	public static EmptyFrozenPMMap INSTANCE = new EmptyFrozenPMMap< Object, Object >();
	
	@SuppressWarnings("unchecked")
	public static < K, V > EmptyFrozenPMMap< K, V > getInstance() { return INSTANCE; }

	@Override
	public EmptyFrozenPMMap< K, V > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SingleEntryMutablePMMap< K, V > add( @NonNull K key, V value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > addAll( @NonNull K key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public PhoenixMultiMap< K, V > addAll( PhoenixMultiMap< ? extends K, ? extends V > multimap ) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public EmptyFrozenPMMap< K, V > removeEntry( @NonNull Object key, Object value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EmptyFrozenPMMap< K, V > removeEntryAt( @NonNull Object key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EmptyFrozenPMMap< K, V > removeEntries( @NonNull Object key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > setValues( @NonNull K key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > setSingletonValue( @NonNull K key, V value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > updateValue( @NonNull K key, int n, V value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > freezeByPhoenixing() {
		return this;
	}

	
	
}
