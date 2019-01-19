package com.steelypip.powerups.hydraxml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface AsMultiTree<Key, Value > {
	
	Iterable< Map.Entry< Key, Value > > entries( Value v );
	
	Key defaultKey();
	
	Value badValue();
	
	Iterable< Map.Entry< Key, Value > > linksToIterable( Value v );
	
	default List< Map.Entry< Key, Value > > linksToList( Value v ) {
		final List< Map.Entry< Key, Value > > a = new ArrayList<>();
		for ( Map.Entry< Key, Value > e : this.linksToIterable( v ) ) {
			a.add( e );
		}
		return a;
	}

}
