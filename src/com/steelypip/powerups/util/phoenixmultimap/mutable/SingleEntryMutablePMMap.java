package com.steelypip.powerups.util.phoenixmultimap.mutable;

import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.frozen.SingleEntryFrozenPMMap;

public class SingleEntryMutablePMMap< K, V > extends AbsSingleEntryMutablePMMap< K, V > {
	
	private K key;
	private V value;

	public K getKey() {
		return key;
	}

	public void setKey( K key ) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue( V value ) {
		this.value = value;
	}

	public SingleEntryMutablePMMap( K key, V value ) {
		super();
		this.key = key;
		this.value = value;
	}
	
	@Override
	public PhoenixMultiMap< K, V > clearAllEntries() {
		return EmptyMutablePMMap.getInstance();
	}

	@Override
	public PhoenixMultiMap< K, V > add( K _key, V _value ) {
		if ( _key == null ? this.key == null : _key.equals(  this.key ) ) {
			return new SharedKeyMutablePMMap< K, V >( key ).add( this.value ).add(  _value );
		} else {
			return new PlainMapMutablePMMap< K, V >().add( this.key, this.value ).add( _key, _value );
		}
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntry( K _key, V _value ) {
		if ( this.hasEntry( _key, _value ) ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntryAt( K _key, int N ) {
		if ( this.hasKey( _key ) && N == 0 ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntries( K _key ) {
		if ( this.hasKey( _key ) ) {
			return this.clearAllEntries();
		} else {
			return this;
		}
	}

	@Override
	public PhoenixMultiMap< K, V > setValues( K _key, Iterable< ? extends V > values ) {
		if ( values.iterator().hasNext() ) {
			return new SharedKeyMutablePMMap< K, V >( _key ).addAll( values ).add( this.key, this.value );
		} else {
			return this;
		}
	}

	@Override
	public PhoenixMultiMap< K, V > setSingletonValue( K _key, V _value ) {
		if ( this.hasKey( _key ) ) {
			this.value = _value;
			return this;
		} else {
			return new PlainMapMutablePMMap<  K, V  >().setSingletonValue( _key, _value ).setSingletonValue( this.key, this.value );
		}
	}

	@Override
	public PhoenixMultiMap< K, V > updateValue( K _key, int n, V _value ) throws IllegalArgumentException {
		if ( n != 0 ) throw new IllegalArgumentException();
		if ( this.hasKey( _key ) ) {
			this.value = _value;
			return this;
		} else {
			throw new IllegalArgumentException();
		}
	}


	@Override
	public PhoenixMultiMap< K, V > freezeByPhoenixing() {
		return new SingleEntryFrozenPMMap< K, V >( this );
	}
	
}
