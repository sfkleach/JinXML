package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.util.phoenixmultimap.TreeMapPhoenixMultiMap;

public abstract class AbsFlexiMutablePMMap< Key, Value > extends TreeMapPhoenixMultiMap< Key, Value > {

	private static final long serialVersionUID = 2918737403536388504L;

	public AbsFlexiMutablePMMap() {
		super();
	}

	@Override
	public List< Map.Entry< Key, Value > > entriesToList() {
		final List< Map.Entry< Key, Value > > list = new ArrayList<>();
		for ( Map.Entry< Key, List< Value > > e : this.entrySet() ) {
			for ( Value v : e.getValue() ) {
				list.add( new StdPair<>( e.getKey(), v ) );
			}
		}
		return list;
	}

	@Override
	public List< Value > getAll( Key key ) {
		final List< Value > list = this.get( key );
		if ( list == null ) {
			return Collections.emptyList();
		} else {
			return Collections.unmodifiableList( list );			
		}
	}

	@Override
	public Value getOrFail( @NonNull Key key ) throws IllegalArgumentException {
		final List< Value > list = this.get( key );
		if ( list == null ) throw new IllegalArgumentException();
		return list.get( 0 );
	}

	@Override
	public Value getElse( @NonNull Key key, Value otherwise ) throws IllegalArgumentException {
		final List< Value > list = this.get( key );
		if ( list == null ) return otherwise;
		return list.get( 0 );
	}

	@Override
	public Value getOrFail( @NonNull Key key, int N ) throws IllegalArgumentException {
		final List< Value > list = this.get( key );
		if ( list == null ) throw new IllegalArgumentException();
		try {
			return list.get( N );
		} catch ( IndexOutOfBoundsException e ) {
			throw new IllegalArgumentException( e );
		}
	}

	@Override
	public Value getElse( Key key, int N, Value otherwise ) throws IllegalArgumentException {
		final List< Value > list = this.get( key );
		try {
			return list == null ? otherwise : list.get( N );
		} catch ( IndexOutOfBoundsException e ) {
			return otherwise;
		}
	}

	@Override
	public Value getElse( Key key, boolean reverse, int N, Value otherwise ) throws IllegalArgumentException {
		if ( reverse ) {
			final List< Value > list = this.get( key );
			if ( list == null ) {
				return otherwise;
			} else {
				N = list.size() - N - 1;
				return this.getElse( key, N, otherwise );
			}
		} else {
			return this.getElse( key, N, otherwise );
		}
	}

	@Override
	public int sizeEntries() {
		int n = 0;
		for ( Map.Entry< Key, List< Value > > e : this.entrySet() ) {
			n += e.getValue().size();
		}
		return n;
	}

	@Override
	public int sizeEntriesWithKey( Key _key ) {
		List< Value > list = this.get( _key );
		return list == null ? 0 : list.size();
	}

	@Override
	public int sizeKeys() {
		return this.size();
	}

}
