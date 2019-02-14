package com.steelypip.powerups.util.phoenixmultimap.frozen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.phoenixmultimap.FrozenMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsSharedKeyMutablePMMap;

public class SharedKeyFrozenPMMap< Key, Value > extends AbsSharedKeyMutablePMMap< Key, Value > implements FrozenMarkerInterface {

	private @NonNull Key sharedKey;	//	Will be initialised in the concrete constructors.
	
	public @NonNull Key getSharedKey() {
		return sharedKey;
	}
 
	public void setSharedKey( @NonNull Key sharedKey ) {
		this.sharedKey = sharedKey;
	}

	public SharedKeyFrozenPMMap( @NonNull Key key, Collection< ? extends Value > values ) {
		super();
		this.sharedKey = key;
		this.values_list = new ArrayList< Value >( values );
	}
	


	@Override
	public PhoenixMultiMap< Key, Value > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > add( @NonNull Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}
	
	public SharedKeyFrozenPMMap< Key, Value > add( Value value ) {
		throw new UnsupportedOperationException();
	}
	

	@Override
	public PhoenixMultiMap< Key, Value > addAll( @NonNull Key _key, Iterable< ? extends Value > _values ) {
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
	public PhoenixMultiMap< Key, Value > removeEntry( @NonNull Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntryAt( @NonNull Key _key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntries( @NonNull Key _key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > setValues( @NonNull Key _key, Iterable< ? extends Value > _values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > setSingletonValue( @NonNull Key _key, Value _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > updateValue( @NonNull Key _key, int n, Value _value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< Key, Value > freezeByPhoenixing() {
		return this;
	}



}
