package com.steelypip.powerups.hydraxml;

import java.util.Map.Entry;

public interface HydraXMLAsMultiTree extends AsMultiTree< String, HydraXML > {

	default Iterable< Entry< String, HydraXML > > entries( HydraXML v ) {
		return v;
	}

	default String defaultKey() {
		return "";
	}
	
	static HydraXML bad_value = new BadHydraXML();

	default HydraXML badValue() {
		return bad_value;
	}

	default Iterable< Entry< String, HydraXML > > linksToIterable( HydraXML v ) {
		return v.linksToIterable();
	}	
	
}
