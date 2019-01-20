package com.steelypip.powerups.util.phoenixmultimap.frozen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.steelypip.powerups.util.phoenixmultimap.FrozenMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsFlexiMutablePMMap;

public class FlexiFrozenPMMap< Key, Value > extends AbsFlexiMutablePMMap< Key, Value > implements FrozenMarkerInterface {

	private static final long serialVersionUID = 7434046523595764233L;

	public FlexiFrozenPMMap( PhoenixMultiMap< Key, Value > mmm ) {
		mmm.entriesToList().forEach( ( Map.Entry< Key, Value > p ) -> secretAdd( p.getKey(), p.getValue() ) );
	}

	public FlexiFrozenPMMap() {
	}
	
	private PhoenixMultiMap< Key, Value > secretAdd( Key key, Value value ) {
		List< Value > list = this.get( key );
		if ( list == null ) {
			list = new ArrayList<>( 1 );
			this.put( key, list );
		}
		list.add( value );
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > add( Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntry( Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntryAt( Key key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntries( Key key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > setValues( Key key, Iterable< ? extends Value > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > setSingletonValue( Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > updateValue( Key key, int n, Value value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > freezeByPhoenixing() {
		return this;
	}	

}
