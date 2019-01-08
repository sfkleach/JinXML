package com.steelypip.powerups.util.phoenixmultimap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.steelypip.powerups.util.multimap.MultiMap;

public abstract class TreeMapPhoenixMultiMap< K, V > extends TreeMap< K, List< V > > implements PhoenixMultiMap< K, V > {

	private static final long serialVersionUID = 5134754069939323247L;

	public TreeMapPhoenixMultiMap() {
		super();
	}
	
	public TreeMapPhoenixMultiMap( Map< ? extends K, ? extends List< V > > m ) {
		super( m );
	}

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
