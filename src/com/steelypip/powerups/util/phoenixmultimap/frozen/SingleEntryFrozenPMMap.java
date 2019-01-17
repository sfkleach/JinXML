package com.steelypip.powerups.util.phoenixmultimap.frozen;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsSingleEntryMutablePMMap;

public class SingleEntryFrozenPMMap< K, V > extends AbsSingleEntryMutablePMMap< K, V > {
	
	private K key;
	private V value;
	
	public K getKey() {
		return key;
	}

	public void setKey( K key ) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue( V value ) {
		this.value = value;
	}

	public SingleEntryFrozenPMMap( K key, V value ) {
		super();
		this.key = key;
		this.value = value;
	}

	public SingleEntryFrozenPMMap( AbsSingleEntryMutablePMMap< K, V > that ) {
		super();
		this.key = that.getKey();
		this.value = that.getValue();
	}
	@Override
	public PhoenixMultiMap< K, V > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > add( K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntry( K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntryAt( K _key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntries( K _key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > setValues( K _key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > setSingletonValue( K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > updateValue( K _key, int n, V _value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}



	@Override
	public PhoenixMultiMap< K, V > freezeByPhoenixing() {
		return this;
	}
	
	
	
}
