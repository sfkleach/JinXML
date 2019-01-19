package com.steelypip.powerups.fusion;

import java.util.Map.Entry;

import com.steelypip.powerups.hydraxml.AsMultiTree;

public interface FusionAsMultiTree extends AsMultiTree< String, Fusion > {

		default Iterable< Entry< String, Fusion > > entries( Fusion v ) {
			return v;
		}

		default String defaultKey() {
			return "";
		}
		
		static Fusion bad_value = new BadFusion();

		default Fusion badValue() {
			return bad_value;
		}

		default Iterable< Entry< String, Fusion > > linksToIterable( Fusion v ) {
			return v.linksToIterable();
		}	
		
}
