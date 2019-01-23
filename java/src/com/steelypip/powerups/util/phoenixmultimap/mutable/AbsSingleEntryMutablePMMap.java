package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.List;
import java.util.Map;

import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.SingletonList;
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.util.phoenixmultimap.AbsPhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;

public abstract class AbsSingleEntryMutablePMMap< K, V > extends AbsPhoenixMultiMap< K, V > implements PhoenixMultiMap< K, V > {

	public abstract K getKey();
	public abstract void setKey( K key );
	public abstract V getValue();
	public abstract void setValue( V value );

	public AbsSingleEntryMutablePMMap() {
		super();
	}

	@Override
	public boolean hasEntry( K _key, V _value ) {
		return this.hasKey( _key ) && this.hasValue( _value );
	}

	@Override
	public boolean hasKey( K _key ) {
		return _key == null ? this.getKey() == null : _key.equals( this.getKey() );
	}

	@Override
	public boolean hasValue( V _value ) {
		return  _value == null ? this.getValue() == null : _value.equals( this.getValue() );
	}

	@Override
	public List< Map.Entry< K, V > > entriesToList() {
		return new SingletonList<>( new StdPair<>( this.getKey(), this.getValue() ) );
	}

	@Override
	public List< V > getAll( K _key ) {
		if ( _key == null ? this.getKey() == null : _key.equals( this.getKey() ) ) {
			return new SingletonList<>( this.getValue() );
		} else {
			return new EmptyList<>();
		}
	}

	@Override
	public V getOrFail( K _key ) throws IllegalArgumentException {
		if ( _key == null ? this.getKey() == null : _key.equals( this.getKey() ) ) {
			return this.getValue();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public V getElse( K _key, V otherwise ) throws IllegalArgumentException {
		if ( _key == null ? this.getKey() == null : _key.equals( this.getKey() ) ) {
			return this.getValue();
		} else {
			return otherwise;
		}
	}

	@Override
	public V getOrFail( K _key, int N ) throws IllegalArgumentException {
		if ( N == 0 && ( _key == null ? this.getKey() == null : _key.equals( this.getKey() ) ) ) {
			return this.getValue();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public V getElse( K _key, int N, V otherwise ) throws IllegalArgumentException {
		if ( N == 0 && ( _key == null ? this.getKey() == null : _key.equals( this.getKey() ) ) ) {
			return this.getValue();
		} else {
			return otherwise;
		}
	}

	@Override
	public V getElse( K _key, boolean reverse, int N, V otherwise ) throws IllegalArgumentException {
		//	If there is a single entry it makes no difference which end we are searching from.
		return this.getElse( _key,  N, otherwise );
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int sizeEntries() {
		return 1;
	}

	@Override
	public int sizeKeys() {
		return 1;
	}

	@Override
	public int sizeEntriesWithKey( K _key ) {
		return this.hasKey( _key ) ? 1 : 0;
	}

}
