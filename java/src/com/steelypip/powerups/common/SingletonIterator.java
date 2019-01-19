package com.steelypip.powerups.common;

import java.util.Iterator;

public class SingletonIterator< Item > implements Iterator< Item > {
	
	private Item item;
	private boolean has_next = false;
 
	public SingletonIterator( Item item ) {
		super();
		this.item = item;
	}

	@Override
	public boolean hasNext() {
		return this.has_next;
	}

	@Override
	public Item next() {
		this.has_next = false;
		return this.item;
	}

}
