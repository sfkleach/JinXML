package com.steelypip.powerups.fusion;

import com.steelypip.powerups.hydranode.BadHydraNode;

/**
 * An implementation of Fusion that always fails. Primary use is where a non-null Fusion
 * lightweight object is needed but doesn't implement any functionality.
 */
public class BadFusion extends BadHydraNode< String, String, String, Fusion > implements Fusion, StdJSONFeatures {
	
	@Override
	public void freeze() {
		throw new UnsupportedOperationException();
	}
	
}
