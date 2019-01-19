package com.steelypip.powerups.hydraxml;

import org.eclipse.jdt.annotation.NonNull;

public class FlexiHydraXMLFactory implements HydraXMLFactory {

	@Override
	public @NonNull HydraXML newMutableElement( @NonNull String name ) {
		return new FlexiHydraXML( name );
	}

}
