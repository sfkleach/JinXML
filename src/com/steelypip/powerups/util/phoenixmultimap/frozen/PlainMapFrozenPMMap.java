package com.steelypip.powerups.util.phoenixmultimap.frozen;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsSingleValueMutablePMMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.EmptyMutablePMMap;

public class SingleValueFrozenPMMap< Key, Value > extends AbsSingleValueMutablePMMap< Key, Value > implements PhoenixMultiMap< Key, Value > {
	
	private static final long serialVersionUID = -2697005333497258559L;
	
	public SingleValueFrozenPMMap( AbsSingleValueMutablePMMap< Key, Value > that ) {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PhoenixMultiMap< Key, Value > clearAllEntries() {
		return (EmptyMutablePMMap< Key, Value >)EmptyMutablePMMap.INSTANCE;
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
	public PhoenixMultiMap< Key, Value > freezeByMutation() {
		return this;
	}
	
}
