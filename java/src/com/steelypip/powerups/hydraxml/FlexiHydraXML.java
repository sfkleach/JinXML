package com.steelypip.powerups.hydraxml;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydranode.FlexiHydraNode;

/** 
 * An all-round implementation of a mutable HydraXML class that implements all methods
 * at reasonable cost and at reasonable performance. 
 */
public class FlexiHydraXML extends FlexiHydraNode< String, String, String, HydraXML > implements HydraXML {

	public FlexiHydraXML( @NonNull String name ) {
		super( name );
	}

	public FlexiHydraXML( HydraXML hx ) {
		this( hx.getInternedName() );
		this.attributes = this.attributes.addAllEntries( hx.attributesToList() );
		this.links = this.links.addAllEntries( hx.linksToIterable() );
	}
	

}
