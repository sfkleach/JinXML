package com.steelypip.powerups.util.multimap;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;

public abstract class ViewPhoenixMultiMapAsMultiMap< K, V > implements MultiMap< K, V > {
	
	protected abstract PhoenixMultiMap< K, V > accessPhoenixMultiMap();

	protected abstract void updatePhoenixMultiMap( PhoenixMultiMap< K, V > pmm );

	public void clearAllEntries() {
		accessPhoenixMultiMap().clearAllEntries();
	}

	public boolean hasEntry( K key, V value ) {
		return accessPhoenixMultiMap().hasEntry( key, value );
	}

	public boolean hasEntry( K key, int index, V value ) {
		return accessPhoenixMultiMap().hasEntry( key, index, value );
	}

	public boolean hasKey( K key ) {
		return accessPhoenixMultiMap().hasKey( key );
	}

	public boolean hasValue( V value ) {
		return accessPhoenixMultiMap().hasValue( value );
	}

	public List< Entry< K, V > > entriesToList() {
		return accessPhoenixMultiMap().entriesToList();
	}

	public Iterator< Entry< K, V > > iterator() {
		return accessPhoenixMultiMap().iterator();
	}

	public List< V > getAll( K key ) {
		return accessPhoenixMultiMap().getAll( key );
	}

	public V getOrFail( K key ) throws IllegalArgumentException {
		return accessPhoenixMultiMap().getOrFail( key );
	}

	public V getElse( K key, V otherwise ) throws IllegalArgumentException {
		return accessPhoenixMultiMap().getElse( key, otherwise );
	}

	public V getOrFail( K key, int N ) throws IllegalArgumentException {
		return accessPhoenixMultiMap().getOrFail( key, N );
	}

	public V getElse( K key, int N, V otherwise )
			throws IllegalArgumentException {
		return accessPhoenixMultiMap().getElse( key, N, otherwise );
	}

	public boolean isEmpty() {
		return accessPhoenixMultiMap().isEmpty();
	}

	public Set< K > keySet() {
		return accessPhoenixMultiMap().keySet();
	}

	public void add( K key, V value ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().add( key, value ) );
	}

	public void add( Entry< ? extends K, ? extends V > e ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().add( e ) );
	}

	public void addAll( K key, Iterable< ? extends V > values ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().addAll( key, values ) );
	}

	public void addAllEntries( Iterable< ? extends Entry< ? extends K, ? extends V > > values ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().addAllEntries( values ) );
	}

	public MultiMap< K, V > addAll( PhoenixMultiMap< ? extends K, ? extends V > multimap ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().addAll( multimap ) );
		return this;
	}

	public void removeEntry( K key, V value ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().removeEntry( key, value ) );
	}

	public void removeEntryAt( K key, int N ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().removeEntryAt( key, N ) );
	}

	public void removeEntries( K key ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().removeEntries( key ) );
	}

	public void setValues( K key, Iterable< ? extends V > values ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().setValues( key, values ) );
	}

	public void setSingletonValue( K key, V value ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().setSingletonValue( key, value ) );
	}

	public void updateValue( K key, int n, V value ) {
		updatePhoenixMultiMap( accessPhoenixMultiMap().updateValue( key, n, value ) );
	}

	public int sizeEntries() {
		return accessPhoenixMultiMap().sizeEntries();
	}

	public int sizeEntriesWithKey( K key ) {
		return accessPhoenixMultiMap().sizeEntriesWithKey( key );
	}

	public int sizeKeys() {
		return accessPhoenixMultiMap().sizeKeys();
	}

	public List< V > valuesList() {
		return accessPhoenixMultiMap().valuesList();
	}

	public MultiMap< K, V > freeze() {
		updatePhoenixMultiMap( accessPhoenixMultiMap().freezeByPhoenixing() );
		return this;
	}

	public MultiMap< K, V > trimToSize() {
		updatePhoenixMultiMap( accessPhoenixMultiMap().trimToSize() );
		return this;
	}
	
}
