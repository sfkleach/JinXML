package com.steelypip.powerups.util.phoenixmultimap;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public abstract class TreeMapSingleValuePhoenixMultiMap< K, V > extends TreeMap< K, V > implements PhoenixMultiMap< K, V > {

	private static final long serialVersionUID = -5473460060055332108L;

	public TreeMapSingleValuePhoenixMultiMap() {
		super();
	}
	
	public TreeMapSingleValuePhoenixMultiMap( Map< ? extends K, ? extends V > m ) {
		super( m );
	}

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
