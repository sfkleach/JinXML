package com.steelypip.powerups.jinxml.stdmodel;

import java.util.IdentityHashMap;

import com.steelypip.powerups.jinxml.Element;

/**
 * This class is a helper that doesn't really belong in stdmodel. However there's
 * not a better package, so this will do. 
 * 
 * The point of the class is to provide a general implementation of deep-freezing
 * oneself. 
 */
public class DeepFreezeSelf {

	/**
	 * We have to protect ourselves against re-freezing the same node repeatedly.
	 */
	final IdentityHashMap< Element, Element > visited = new IdentityHashMap<>();
	
	public void selfFreeze( Element x ) {
		if ( ! visited.containsKey( x ) ) {
			x.freezeSelf();
			visited.put( x, x );
			for ( Element child : x.children() ) {
				this.selfFreeze( child );
			}
		}
	}
	
}
