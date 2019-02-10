package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.Map.Entry;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.util.phoenixmultimap.MutableMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.frozen.SharedKeyFrozenPMMap;

public class SharedKeyMutablePMMap< Key, Value > extends AbsSharedKeyMutablePMMap< Key, Value > implements MutableMarkerInterface {

	private @NonNull Key sharedKey;	//	Will be initialised in the concrete constructors.
	
	public @NonNull Key getSharedKey() {
		return sharedKey;
	}
 
	public void setSharedKey( @NonNull Key sharedKey ) {
		this.sharedKey = sharedKey;
	}
	
	public SharedKeyMutablePMMap( @NonNull Key key ) {
		super();
		this.sharedKey = key;
	}
	
	@Override
	public PhoenixMultiMap< Key, Value > clearAllEntries() {
		return EmptyMutablePMMap.getInstance();
	}

	@Override
	public PhoenixMultiMap< Key, Value > add( @NonNull Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.add( _value );
			return this;
		} else {
			return new FlexiMutablePMMap< Key, Value >().addAll( this.getSharedKey(), this.values_list ).add( _key, _value );
		}
	}
	
	public SharedKeyMutablePMMap< Key, Value > add( Value value ) {
		this.values_list.add( value );
		return this;
	}
	

	@Override
	public PhoenixMultiMap< Key, Value > addAll( @NonNull Key _key, Iterable< ? extends Value > _values ) {
		return new FlexiMutablePMMap< Key, Value >().addAll( this.getSharedKey(), this.values_list ).addAll( _key, _values );
	}
	
	public SharedKeyMutablePMMap< Key, Value > addAll( Iterable< ? extends Value > values ) {
		for ( Value v : values ) {
			this.values_list.add( v );
		}
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > addAllEntries( Iterable< ? extends Entry< ? extends Key, ? extends Value > > values ) {
		return new FlexiMutablePMMap< Key, Value >().addAll( this.getSharedKey(), this.values_list ).addAllEntries( values );
	}

	@Override
	public PhoenixMultiMap< Key, Value > addAll( PhoenixMultiMap< ? extends Key, ? extends Value > multimap ) {
		return new FlexiMutablePMMap< Key, Value >().addAll( this.getSharedKey(), this.values_list ).addAll( multimap );
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntry( @NonNull Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.remove( _value );
		}
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntryAt( @NonNull Key _key, int N ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.remove( N );
		}
		return this;
	}

	@Override
	public PhoenixMultiMap< Key, Value > removeEntries( @NonNull Key _key ) {
		if ( this.hasKey( _key ) ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public PhoenixMultiMap< Key, Value > setValues( @NonNull Key _key, Iterable< ? extends Value > _values ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.clear();
			for ( Value v : _values ) {
				this.values_list.add( v );
			}
			return this;
		} else {
			return new FlexiMutablePMMap< Key, Value >().addAll( this.getSharedKey(), this.values_list ).setValues( _key, _values );
		}
	}

	@Override
	public PhoenixMultiMap< Key, Value > setSingletonValue( @NonNull Key _key, Value _value ) {
		if ( this.hasKey( _key ) ) {
			this.values_list.clear();
			this.values_list.add( _value );
			return this;
		} else {
			return new FlexiMutablePMMap<>( this ).add( _key, _value );
		}
	}

	@Override
	public PhoenixMultiMap< Key, Value > updateValue( @NonNull Key _key, int n, Value _value ) throws IllegalArgumentException {
		if ( this.hasKey( _key ) ) {
			try {
				this.values_list.set( n, _value );
				return this;
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException( _e );
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public PhoenixMultiMap< Key, Value > freezeByPhoenixing() {
		return new SharedKeyFrozenPMMap< Key, Value >( this.getSharedKey(), this.valuesList() );
	}
	
	
}
