package com.steelypip.powerups.util.phoenixmultimap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

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
		PhoenixMultiMap< K, V > that = (PhoenixMultiMap< K, V >)obj;
		if ( this.sizeEntries() !=  that.sizeEntries() ) return false;
		Iterator< Map.Entry< K, V > > entries = this.iterator();
		while ( entries.hasNext() ) {
			Map.Entry< K, V > e = entries.next();
			K key = e.getKey();
			if ( ! ( that.hasEntry( Objects.requireNonNull( key ), e.getValue() ) ) ) {
				return false;
			}
		}
		return true;
	}


}
