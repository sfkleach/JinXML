package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.phoenixmultimap.AbsPhoenixMultiMap;

public abstract class AbsEmptyMutablePMMap< K, V > extends AbsPhoenixMultiMap< K, V > {

	@Override
	public boolean hasEntry( @NonNull K key, V value ) {
		return false;
	}
	
	@Override
	public boolean hasEntry( @NonNull K key, int index, V value ) {
		return false;
	}

	@Override
	public boolean hasKey( @NonNull K key ) {
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
	public List< V > getAll( @NonNull K key ) {
		return Collections.emptyList();
	}

	@Override
	public V getOrFail( @NonNull K key ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public V getOrFail( @NonNull K key, int N ) throws IllegalArgumentException {
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
	public int sizeEntriesWithKey( @NonNull K key ) {
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
	public V getElse( @NonNull K key, V otherwise ) throws IllegalArgumentException {
		return otherwise;
	}

	@Override
	public V getElse( @NonNull K key, int N, V otherwise ) throws IllegalArgumentException {
		return otherwise;
	}
	
	@Override
	public V getElse( @NonNull K key, boolean reverse, int N, V otherwise ) throws IllegalArgumentException {
		return otherwise;
	}
	
}
