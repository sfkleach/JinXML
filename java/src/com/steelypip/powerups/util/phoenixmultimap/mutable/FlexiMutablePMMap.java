package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.steelypip.powerups.util.phoenixmultimap.MutableMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.frozen.FlexiFrozenPMMap;

public class FlexiMutablePMMap< Key, Value > extends AbsFlexiMutablePMMap< Key, Value > implements MutableMarkerInterface {

	private static final long serialVersionUID = 7434046523595764233L;

	public FlexiMutablePMMap( PhoenixMultiMap< Key, Value > mmm ) {
		mmm.entriesToList().forEach( ( Map.Entry< Key, Value > p ) -> add( p ) );
	}

	public FlexiMutablePMMap() {
	}

	@Override
	public PhoenixMultiMap< Key, Value > clearAllEntries() {
		return EmptyMutablePMMap.getInstance();
	}

	@Override
	public PhoenixMultiMap< Key, Value > add( Key key, Value value ) {
		List< Value > list = this.get( key );
		if ( list == null ) {
			list = new ArrayList< Value >();
			this.put( key, list );
		}
		list.add( value );
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntry( Key key, Value value ) {
		final List< Value > list = this.get( key );
		if ( list != null ) {
			list.remove( value );
		}
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntryAt( Key key, int N ) {
		final List< Value > list = this.get( key );
		if ( list != null ) {
			list.remove( N );
		}
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntries( Key key ) {
		this.remove( key );
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > setValues( Key key, Iterable< ? extends Value > values ) {
		List< Value > list = this.get( key );
		if ( list == null ) {
			list = new ArrayList<>();
			this.put( key, list );
		} else {
			list.clear();
		}
		for ( Value v : values ) {
			list.add( v );
		}
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > setSingletonValue( Key key, Value value ) {
		List< Value > list = this.get( key );
		if ( list == null ) {
			list = new ArrayList<>();
			this.put( key, list );
		} else {
			list.clear();
		}
		list.add( value );
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > updateValue( Key key, int n, Value value ) throws IllegalArgumentException {
		List< Value > list = this.get( key );
		if ( list == null ) throw new IllegalArgumentException();
		try {
			list.set( n, value );
		} catch ( IndexOutOfBoundsException e ) {
			throw new IllegalArgumentException();
		}
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > freezeByPhoenixing() {
		return new FlexiFrozenPMMap<>( this );
	}


}
