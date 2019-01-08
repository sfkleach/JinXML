package com.steelypip.powerups.util.phoenixmultimap;

import java.util.Iterator;
import java.util.Map;

import com.steelypip.powerups.util.multimap.MultiMap;

public abstract class AbsPhoenixMultiMap< K, V > implements PhoenixMultiMap< K, V > {
	
	@SuppressWarnings("unchecked")
	public boolean equals( Object obj ) {
		if ( this.sizeEntries() !=  ((MultiMap<K,V>)obj).sizeEntries() ) return false;
		Iterator< Map.Entry< K, V > > entries = this.iterator();
		while ( entries.hasNext() ) {
			Map.Entry< K, V > e = entries.next();
			if (! ((MultiMap<K,V>)obj).hasEntry( e.getKey(), e.getValue() ) ) {
				return false;
			}
		}
		return true;
	}

}
