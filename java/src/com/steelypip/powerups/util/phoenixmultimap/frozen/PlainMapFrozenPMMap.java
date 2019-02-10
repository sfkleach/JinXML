package com.steelypip.powerups.util.phoenixmultimap.frozen;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.phoenixmultimap.FrozenMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsPlainMapMutablePMMap;

public class PlainMapFrozenPMMap< Key, Value > extends AbsPlainMapMutablePMMap< Key, Value > implements PhoenixMultiMap< Key, Value >, FrozenMarkerInterface {
	
	private static final long serialVersionUID = -2697005333497258559L;
	
	public PlainMapFrozenPMMap( AbsPlainMapMutablePMMap< Key, Value > that ) {
		super();
		Iterator< Map.Entry< Key, Value > > it = that.iterator();
		while ( it.hasNext() ) {
			Map.Entry< Key, Value > e = it.next();
			this.put( e.getKey(), e.getValue() );
		}
	}

	@Override
	public PhoenixMultiMap< Key, Value > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > add( @NonNull Key key, Value value ) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public PhoenixMultiMap< Key, Value > addAll( PhoenixMultiMap< ? extends Key, ? extends Value > multimap ) {
		throw new UnsupportedOperationException();
	}


	@Override
	public PhoenixMultiMap< Key, Value > removeEntry( @NonNull Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntryAt( @NonNull Key key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntries( @NonNull Key key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > setValues( @NonNull Key key, Iterable< ? extends Value > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > setSingletonValue( @NonNull Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > updateValue( @NonNull Key key, int n, Value value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > freezeByPhoenixing() {
		return this;
	}
	
}
