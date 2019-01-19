package com.steelypip.powerups.util.phoenixmultimap;

import java.util.Iterator;
import java.util.Map;

public abstract class AbsPhoenixMultiMap< K, V > implements PhoenixMultiMap< K, V > {
	
	@SuppressWarnings("unchecked")
	public boolean equals( Object obj ) {
		PhoenixMultiMap< K, V > that = (PhoenixMultiMap< K, V >)obj;
		if ( this.sizeEntries() !=  that.sizeEntries() ) return false;
		Iterator< Map.Entry< K, V > > entries = this.iterator();
		while ( entries.hasNext() ) {
			Map.Entry< K, V > e = entries.next();
			if (! ( that.hasEntry( e.getKey(), e.getValue() ) ) ) {
				return false;
			}
		}
		return true;
	}

}
