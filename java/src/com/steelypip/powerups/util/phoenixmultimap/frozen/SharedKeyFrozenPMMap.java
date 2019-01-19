package com.steelypip.powerups.util.phoenixmultimap.frozen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsSharedKeyMutablePMMap;

public class SharedKeyFrozenPMMap< Key, Value > extends AbsSharedKeyMutablePMMap< Key, Value > {
	
	public SharedKeyFrozenPMMap( Key key, Collection< ? extends Value > values ) {
		super();
		this.shared_key = key;
		this.values_list = new ArrayList< Value >( values );
	}

	@Override
	public PhoenixMultiMap< Key, Value > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > add( Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}
	
	public SharedKeyFrozenPMMap< Key, Value > add( Value value ) {
		throw new UnsupportedOperationException();
	}
	

	@Override
	public PhoenixMultiMap< Key, Value > addAll( Key _key, Iterable< ? extends Value > _values ) {
		throw new UnsupportedOperationException();
	}
	
	public SharedKeyFrozenPMMap< Key, Value > addAll( Iterable< ? extends Value > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > addAllEntries( Iterable< ? extends Entry< ? extends Key, ? extends Value > > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > addAll( PhoenixMultiMap< ? extends Key, ? extends Value > multimap ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntry( Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntryAt( Key _key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntries( Key _key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > setValues( Key _key, Iterable< ? extends Value > _values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > setSingletonValue( Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > updateValue( Key _key, int n, Value _value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > freezeByPhoenixing() {
		return this;
	}



}
