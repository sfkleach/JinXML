package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.TreeMapSingleValuePhoenixMultiMap;

public abstract class AbsSingleValueMutablePMMap< Key, Value > extends TreeMapSingleValuePhoenixMultiMap< Key, Value > implements PhoenixMultiMap< Key, Value > {

	private static final long serialVersionUID = 1L;
	
	public AbsSingleValueMutablePMMap() {
		super();
	}

	public AbsSingleValueMutablePMMap( Map< ? extends Key, ? extends Value > m ) {
		super( m );
	}

	@Override
	public List< Map.Entry< Key, Value > > entriesToList() {
		return new ArrayList<>( this.entrySet() );
	}

	@Override
	public List< Value > getAll( Key key ) {
		final Value v = this.get( key );
		if ( v == null && ! this.containsKey( key ) ) {
			return Collections.emptyList();
		} else {
			return Collections.singletonList( v );
		}
	}

	@Override
	public Value getOrFail( Key key ) throws IllegalArgumentException {
		final Value v = this.get( key );
		if ( v == null && ! this.containsKey( key ) ) {
			throw new IllegalArgumentException();
		} else {
			return v;
		}
	}

	@Override
	public Value getElse( Key key, Value otherwise ) throws IllegalArgumentException {
		return this.getOrDefault( key, otherwise );
	}

	@Override
	public Value getOrFail( Key key, int N ) throws IllegalArgumentException {
		Value v = this.get( key );
		if ( N != 0 || ( v == null && ! this.containsKey( key ) ) ) {
			throw new IllegalArgumentException();
		} else {
			return v;
		}
	}

	@Override
	public Value getElse( Key key, int N, Value otherwise ) throws IllegalArgumentException {
		if ( N == 0 ) {
			return this.getOrDefault( key, otherwise );
		} else {
			throw new IllegalArgumentException();			
		}
	}

	@Override
	public int sizeEntries() {
		return this.size();
	}

	@Override
	public int sizeKeys() {
		return this.size();
	}

	@Override
	public int sizeEntriesWithKey( Key _key ) {
		return this.hasKey( _key ) ? 1 : 0;
	}

}
