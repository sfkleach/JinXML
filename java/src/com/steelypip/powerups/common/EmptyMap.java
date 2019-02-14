package com.steelypip.powerups.common;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;


/**
 * Implements the special case of an empty, immutable map from 
 * any type T to any type U.
 * @param <T> the type of the domain
 * @param <U> the type of the codomain
 */
public class EmptyMap< T, @Nullable U > extends AbstractMap< T, U > {
	
	static EmptySet< Object > empty_set = new EmptySet< Object >();
	static EmptyList< Object > empty_list = new EmptyList< Object >();


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Set< Map.Entry< T, U > > entrySet() {
		return (Set)empty_set;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public boolean containsValue( Object value ) {
		return false;
	}

	@Override
	public boolean containsKey( Object key ) {
		return false;
	}

	@Override
	public @Nullable U getOrDefault( Object key, @Nullable U defaultValue ) {
		return defaultValue;
	}

	@Override
	public U put( T key, U value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public U remove( Object key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll( Map< ? extends T, ? extends U > m ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set< T > keySet() {
		return (Set<T>)empty_set;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection< U > values() {
		return (Collection<U>)empty_list;
	}

	
}
