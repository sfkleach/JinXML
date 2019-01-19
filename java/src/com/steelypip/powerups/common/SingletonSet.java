package com.steelypip.powerups.common;

import java.util.AbstractSet;
import java.util.Iterator;

public class SingletonSet< Item > extends AbstractSet< Item > {
	
	private Item item;
	
	public SingletonSet( Item item ) {
		super();
		this.item = item;
	}

	@Override
	public Iterator< Item > iterator() {
		return new SingletonIterator< Item >( this.item );
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}
	
}
