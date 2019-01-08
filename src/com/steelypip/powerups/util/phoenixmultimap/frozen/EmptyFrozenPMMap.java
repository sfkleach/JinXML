package com.steelypip.powerups.util.phoenixmultimap.frozen;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsEmptyMutablePMMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.SingleEntryMutablePMMap;

public class EmptyFrozenPMMap< K, V > extends AbsEmptyMutablePMMap< K, V > {
	
	@SuppressWarnings("rawtypes")
	public static EmptyFrozenPMMap INSTANCE = new EmptyFrozenPMMap< Object, Object >();
	
	@SuppressWarnings("unchecked")
	public static < K, V > EmptyFrozenPMMap< K, V > getInstance() { return INSTANCE; }

	@Override
	public EmptyFrozenPMMap< K, V > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public SingleEntryMutablePMMap< K, V > add( K key, V value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > addAll( K key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EmptyFrozenPMMap< K, V > removeEntry( Object key, Object value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EmptyFrozenPMMap< K, V > removeEntryAt( Object key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EmptyFrozenPMMap< K, V > removeEntries( Object key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > setValues( K key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > setSingletonValue( K key, V value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > updateValue( K key, int n, V value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > freezeByMutation() {
		return this;
	}

	
	
}
