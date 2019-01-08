package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.util.phoenixmultimap.AbsPhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;

public abstract class AbsSharedKeyMutablePMMap< Key, Value > extends AbsPhoenixMultiMap< Key, Value > implements PhoenixMultiMap< Key, Value > {
	
	protected Key shared_key;	//	Will be initialised in the concrete constructors.
	List< Value > values_list = new ArrayList<>();

	@SuppressWarnings("null")
	public AbsSharedKeyMutablePMMap() {
		super();
	}

	@Override
	public boolean hasEntry( Key key, Value value ) {
		return (
			this.hasKey( key ) && 
			this.hasValue( value )
		);
	}

	@Override
	public boolean hasKey( Key key ) {
		return key == null ? this.shared_key == null : key.equals(  this.shared_key  );
	}

	@Override
	public boolean hasValue( Value value ) {
		return this.values_list.contains( value );
	}

	@Override
	public List< Map.Entry< Key, Value > > entriesToList() {
		return this.values_list.stream().map( ( Value v ) -> new StdPair< Key, Value >( this.shared_key, v ) ).collect( Collectors.toList() );
	}

	@Override
	public List< Value > getAll( Key _key ) {
		if ( this.hasKey( _key ) ) {
			return Collections.unmodifiableList( this.values_list );
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Value getOrFail( Key key ) throws IllegalArgumentException {
		if ( this.hasKey( key ) && ! this.values_list.isEmpty() ) {
			return this.values_list.get( 0 );
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Value getOrFail( Key key, int N ) throws IllegalArgumentException {
		if ( this.hasKey( key ) ) {
			try {
				return this.values_list.get( N );
			} catch ( IndexOutOfBoundsException _e ) {
				throw new IllegalArgumentException();
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public boolean isEmpty() {
		return this.values_list.isEmpty();
	}

	@Override
	public Set< Key > keySet() {
		return Collections.singleton( this.shared_key );
	}

	@Override
	public int sizeEntries() {
		return this.values_list.size();
	}

	@Override
	public int sizeKeys() {
		return 1;
	}

	@Override
	public int sizeEntriesWithKey( Key key ) {
		if ( this.hasKey( key ) ) {
			return this.values_list.size();
		} else {
			return 0;
		}
	}

	@Override
	public List< Value > valuesList() {
		return Collections.unmodifiableList( this.values_list );
	}

	@Override
	public Value getElse( Key key, Value otherwise ) throws IllegalArgumentException {
		if ( this.hasKey( key ) && ! this.values_list.isEmpty() ) {
			return this.values_list.get( 0 );
		} else {
			return otherwise;
		}
	}

	@Override
	public Value getElse( Key key, int N, Value otherwise ) throws IllegalArgumentException {
		if ( this.hasKey( key ) && ! this.values_list.isEmpty() && 0 <= N && N < this.values_list.size() ) {
			return this.values_list.get( N );
		} else {
			return otherwise;
		}
	}

}
