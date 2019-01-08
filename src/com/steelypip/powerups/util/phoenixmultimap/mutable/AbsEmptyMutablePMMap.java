package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.steelypip.powerups.util.phoenixmultimap.AbsPhoenixMultiMap;

public abstract class AbsEmptyMutablePMMap< K, V > extends AbsPhoenixMultiMap< K, V > {

	@Override
	public boolean hasEntry( K key, V value ) {
		return false;
	}
	
	@Override
	public boolean hasEntry( K key, int index, V value ) {
		return false;
	}

	@Override
	public boolean hasKey( K key ) {
		return false;
	}

	@Override
	public boolean hasValue( V value ) {
		return false;
	}

	@Override
	public List< Map.Entry< K, V > > entriesToList() {
		return Collections.emptyList();
	}

	@Override
	public List< V > getAll( K key ) {
		return Collections.emptyList();
	}

	@Override
	public V getOrFail( K key ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public V getOrFail( K key, int N ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public Set< K > keySet() {
		return Collections.emptySet();
	}

	@Override
	public int sizeEntries() {
		return 0;
	}

	@Override
	public int sizeEntriesWithKey( K key ) {
		return 0;
	}

	@Override
	public int sizeKeys() {
		return 0;
	}

	@Override
	public List< V > valuesList() {
		return Collections.emptyList();
	}

	@Override
	public V getElse( K key, V otherwise ) throws IllegalArgumentException {
		return otherwise;
	}

	@Override
	public V getElse( K key, int N, V otherwise ) throws IllegalArgumentException {
		return otherwise;
	}
	
}
