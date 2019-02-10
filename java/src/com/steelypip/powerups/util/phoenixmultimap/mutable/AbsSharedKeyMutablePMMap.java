package com.steelypip.powerups.util.phoenixmultimap.mutable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.util.phoenixmultimap.AbsPhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;

public abstract class AbsSharedKeyMutablePMMap< Key, Value > extends AbsPhoenixMultiMap< Key, Value > implements PhoenixMultiMap< Key, Value > {
	
	@SuppressWarnings("null")
	protected List< Value > values_list = new ArrayList<>();


	abstract protected @NonNull Key getSharedKey() ;
	abstract protected void setSharedKey( @NonNull Key shared_key );
	
	
	@Override
	public boolean hasEntry( @NonNull Key key, Value value ) {
		return (
			this.hasKey( key ) && 
			this.hasValue( value )
		);
	}

	@Override
	public boolean hasKey( @NonNull Key key ) {
		return key == null ? this.getSharedKey() == null : key.equals( this.getSharedKey()  );
	}

	@Override
	public boolean hasValue( Value value ) {
		return this.values_list.contains( value );
	}

	@Override
	public List< Map.Entry< Key, Value > > entriesToList() {
		return this.values_list.stream().map( ( Value v ) -> new StdPair< Key, Value >( this.getSharedKey(), v ) ).collect( Collectors.toList() );
	}

	@Override
	public List< Value > getAll( @NonNull Key _key ) {
		if ( this.hasKey( _key ) ) {
			return Collections.unmodifiableList( this.values_list );
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public Value getOrFail( @NonNull Key key ) throws IllegalArgumentException {
		if ( this.hasKey( key ) && ! this.values_list.isEmpty() ) {
			return this.values_list.get( 0 );
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Value getOrFail( @NonNull Key key, int N ) throws IllegalArgumentException {
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
		return Collections.singleton( this.getSharedKey() );
	}

	@Override
	public int sizeEntries() {
		return this.values_list.size();
	}

	@Override
	public int sizeKeys() {
		return this.values_list.isEmpty() ? 0 : 1;
	}

	@Override
	public int sizeEntriesWithKey( @NonNull Key key ) {
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
	public Value getElse( @NonNull Key key, Value otherwise ) throws IllegalArgumentException {
		if ( this.hasKey( key ) && ! this.values_list.isEmpty() ) {
			return this.values_list.get( 0 );
		} else {
			return otherwise;
		}
	}

	@Override
	public Value getElse( @NonNull Key key, int N, Value otherwise ) throws IllegalArgumentException {
		if ( this.hasKey( key ) && ! this.values_list.isEmpty() && 0 <= N && N < this.values_list.size() ) {
			return this.values_list.get( N );
		} else {
			return otherwise;
		}
	}

	@Override
	public Value getElse( @NonNull Key key, boolean reverse, int N, Value otherwise ) throws IllegalArgumentException {
		if ( reverse ) {
			if ( this.hasKey( key ) ) {
				N = this.values_list.size() - N - 1;
				return this.getElse( key, N, otherwise );
			} else {
				return otherwise;
			}
		} else {
			return this.getElse(  key, N, otherwise );
		}
	}


}
