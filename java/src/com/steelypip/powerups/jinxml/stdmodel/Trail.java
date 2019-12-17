package com.steelypip.powerups.jinxml.stdmodel;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * The dump is a record of the enclosing context. Tags and entry-selectors are
 * regarded as akin to nested parentheses and brackets - they have to match. Open-tags
 * are represented by the element that they start and open-entries are represented by
 * the selector-string.
 */
public class Trail implements Iterable< Focus > {
	
	protected ArrayDeque< Focus > trail = new ArrayDeque<>();
	
	public void addLast( Focus x ) {
		this.trail.addLast( x );
	}
	
	public Focus removeLast() {
		return this.trail.removeLast();
	}
	
	public boolean isEmpty() {
		return this.trail.isEmpty();
	}
	
	public boolean isntEmpty() {
		return ! this.isEmpty();
	}
	
	public Iterator< Focus > iterator() {
		return this.trail.iterator();
	}
	
}