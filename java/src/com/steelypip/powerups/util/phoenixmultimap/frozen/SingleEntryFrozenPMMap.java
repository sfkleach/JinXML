package com.steelypip.powerups.util.phoenixmultimap.frozen;

import java.util.Iterator;
import java.util.Map.Entry;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.EmptyIterator;
import com.steelypip.powerups.common.SingletonIterator;
import com.steelypip.powerups.common.Sequence;
import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.util.phoenixmultimap.FrozenMarkerInterface;
import com.steelypip.powerups.util.phoenixmultimap.PhoenixMultiMap;
import com.steelypip.powerups.util.phoenixmultimap.mutable.AbsSingleEntryMutablePMMap;

public class SingleEntryFrozenPMMap< K, V > extends AbsSingleEntryMutablePMMap< K, V > implements FrozenMarkerInterface {
	
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

	public SingleEntryFrozenPMMap( K key, V value ) {
		super();
		this.key = key;
		this.value = value;
	}

	public SingleEntryFrozenPMMap( AbsSingleEntryMutablePMMap< K, V > that ) {
		super();
		this.key = that.getKey();
		this.value = that.getValue();
	}
	@Override
	public PhoenixMultiMap< K, V > clearAllEntries() {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > add( @NonNull K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > addAll( PhoenixMultiMap< ? extends K, ? extends V > multimap ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntry( @NonNull K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntryAt( @NonNull K _key, int N ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > removeEntries( @NonNull K _key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > setValues( @NonNull K _key, Iterable< ? extends V > values ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > setSingletonValue( @NonNull K _key, V _value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > updateValue( @NonNull K _key, int n, V _value ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public PhoenixMultiMap< K, V > freezeByPhoenixing() {
		return this;
	}
	
	private Entry< K, V > getSingleEntry() {
		return new StdPair< K, V >( this.key, this.value );
	}

	@Override
	public Sequence< Entry< K, V > > entriesToIterable() {
		return new Sequence< Entry< K, V > >() {
			@Override
			public Iterator< Entry< K, V > > iterator() {
				return new SingletonIterator< Entry< K, V > >( SingleEntryFrozenPMMap.this.getSingleEntry() );
			}
		};
	}

	@Override
	public Sequence< K > keysToIterable() {
		return new Sequence< K >() {
			@Override
			public Iterator< K > iterator() {
				return new SingletonIterator< K >( SingleEntryFrozenPMMap.this.key );
			}
		};
	}

	@Override
	public Sequence< V > valuesToIterable( K key ) {
		if ( this.key.equals( key ) ) {
			return new Sequence< V >() {
				@Override
				public Iterator< V > iterator() {
					return new SingletonIterator< V >( SingleEntryFrozenPMMap.this.value );
				}
			};
		} else {
			return new Sequence< V >() {
				@Override
				public Iterator< V > iterator() {
					return new EmptyIterator< V >();
				}
			};			
		}
	}
	
}
