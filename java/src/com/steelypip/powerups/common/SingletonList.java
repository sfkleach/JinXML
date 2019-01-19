package com.steelypip.powerups.common;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.List;

public class SingletonList< Item > extends AbstractList< Item > implements List< Item > {
	
	private Item item;
	
	public SingletonList( Item item ) {
		super();
		this.item = item;
	}

	@Override
	public Item get( final int index ) {
		if ( index == 0 ) {
			return this.item;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public void sort( Comparator< ? super Item > c ) {
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
	
	

}
