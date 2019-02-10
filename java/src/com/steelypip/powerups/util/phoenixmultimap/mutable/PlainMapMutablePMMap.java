package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.phoenixmultimap.MutableMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.frozen.PlainMapFrozenPMMap;

public class PlainMapMutablePMMap< Key, Value > extends AbsPlainMapMutablePMMap< Key, Value > implements MutableMarkerInterface {
	
	private static final long serialVersionUID = -2697005333497258559L;
	
	public PlainMapMutablePMMap() {
		super();
	}

	public PlainMapMutablePMMap( Map< ? extends Key, ? extends Value > m ) {
		super( m );
	}

	@SuppressWarnings("unchecked")
	@Override
	public PhoenixMultiMap< Key, Value > clearAllEntries() {
		return (EmptyMutablePMMap< Key, Value >)EmptyMutablePMMap.INSTANCE;
	}

	@Override
	public PhoenixMultiMap< Key, Value > add( @NonNull Key key, Value value ) {
		final Value v = this.get( key );
		if ( v == value ) {
			return this;
		} else if ( v == null && ! this.containsKey( key ) ) {
			this.put( key, value );
			return this;
		} else {
			return new FlexiMutablePMMap< Key, Value >( this ).add( key, value );
		}
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntry( @NonNull Key key, Value value ) {
		final Value v = this.getOrDefault( key, value );
		if ( v == null ? value == null : v.equals( value ) ) {
			this.remove( key );
		}
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntryAt( @NonNull Key key, int N ) {
		if ( N == 0 ) {
			return this.removeEntries( key );
		} else {
			return this;
		}
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntries( @NonNull Key key ) {
		this.remove( key );
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > setValues( @NonNull Key key, Iterable< ? extends Value > values ) {
		final Iterator< ? extends Value > it = values.iterator();
		if ( ! it.hasNext() ) return this;
		Value v = it.next();
		if ( it.hasNext() ) {
			return new FlexiMutablePMMap< Key, Value >( this ).setValues( key, values );
		} else {
			this.put( key, v );
			return this;
		}
	}

	@Override
	public PhoenixMultiMap< Key, Value > setSingletonValue( @NonNull Key key, Value value ) {
		this.put( key, value );
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > updateValue( @NonNull Key key, int n, Value value ) throws IllegalArgumentException {
		if ( n != 0 || ! this.hasKey( key ) ) throw new IllegalArgumentException();
		this.put( key, value );
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > freezeByPhoenixing() {
		return new PlainMapFrozenPMMap< Key, Value >( this );
	}	

}
