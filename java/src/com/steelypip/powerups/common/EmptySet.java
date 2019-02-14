package com.steelypip.powerups.common;

import java.util.Collection;
import java.util.Iterator;
import java.util.AbstractSet;

/**
 * Implements the special case of an empty, immutable set 
 * of values of any type T.
 * @param <T> the type of members (if there were any)
 */
public class EmptySet< T > extends AbstractSet< T > {
	
	static Iterator< Object > empty_iterator = new EmptyIterator< Object >();

	@SuppressWarnings("unchecked")
	@Override
	public Iterator< T > iterator() {
		return ( Iterator< T > )empty_iterator;
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
	public boolean contains( Object o ) {
		return false;
	}

	@Override
	public boolean containsAll( Collection< ? > c ) {
		return false;
	}	

}
