package com.steelypip.powerups.util.phoenixmultimap.frozen;

import java.util.Map;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsFlexiMutablePMMap;

public class FlexiFrozenPMMap< Key, Value > extends AbsFlexiMutablePMMap< Key, Value > {

	private static final long serialVersionUID = 7434046523595764233L;

	public FlexiFrozenPMMap( PhoenixMultiMap< Key, Value > mmm ) {
		mmm.entriesToList().forEach( ( Map.Entry< Key, Value > p ) -> add( p ) );
	}

	public FlexiFrozenPMMap() {
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
