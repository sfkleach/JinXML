package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydranode.FlexiHydraNode;

/** 
 * An all-round implementation of a mutable Fusion class that implements all methods
 * at reasonable cost and at reasonable performance. 
 */
public class FlexiFusion extends FlexiHydraNode< String, String, String, Fusion > implements Fusion, StdJSONFeatures {

	public FlexiFusion( @NonNull String name ) {
		super( name );
	}

	public FlexiFusion( Fusion fusion ) {
		this( fusion.getInternedName() );
		this.attributes = this.attributes.addAllEntries( fusion.attributesToList() );
		this.links = this.links.addAllEntries( fusion.linksToIterable() );
	}
	

}
