package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.TreeMapSingleValuePhoenixMultiMap;

public abstract class AbsPlainMapMutablePMMap< Key, Value > extends TreeMapSingleValuePhoenixMultiMap< Key, Value > implements PhoenixMultiMap< Key, Value > {

	private static final long serialVersionUID = 1L;
	
	public AbsPlainMapMutablePMMap() {
		super();
	}

	public AbsPlainMapMutablePMMap( Map< ? extends Key, ? extends Value > m ) {
		super( m );
	}

	@Override
	public List< Map.Entry< Key, Value > > entriesToList() {
		return new ArrayList<>( this.entrySet() );
	}

	@Override
	public List< Value > getAll( @NonNull Key key ) {
		final Value v = this.get( key );
		if ( v == null && ! this.containsKey( key ) ) {
			return Collections.emptyList();
		} else {
			return Collections.singletonList( v );
		}
	}

	@Override
	public Value getOrFail( @NonNull Key key ) throws IllegalArgumentException {
		final Value v = this.get( key );
		if ( v == null && ! this.containsKey( key ) ) {
			throw new IllegalArgumentException();
		} else {
			return v;
		}
	}

	@Override
	public Value getElse( @NonNull Key key, Value otherwise ) throws IllegalArgumentException, NullPointerException {
		return this.getOrDefault( key, otherwise );
	}

	@Override
	public Value getOrFail( @NonNull Key key, int N ) throws IllegalArgumentException {
		Value v = this.get( key );
		if ( N != 0 || ( v == null && ! this.containsKey( key ) ) ) {
			throw new IllegalArgumentException();
		} else {
			return v;
		}
	}

	@Override
	public Value getElse( @NonNull Key key, int N, Value otherwise ) {
		if ( N == 0 ) {
			return this.getOrDefault( key, otherwise );
		} else {
			return otherwise;
		}
	}

	@Override
	public Value getElse( @NonNull Key key, boolean reverse, int N, Value otherwise ) throws IllegalArgumentException {
		//	There is either 1 or 0 entries, so reverse makes no difference. If there are no entries then
		//	return the default.
		return this.getElse( key, N, otherwise );
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
	public int sizeEntriesWithKey( @NonNull Key _key ) {
		return this.hasKey( _key ) ? 1 : 0;
	}

}
