package com.steelypip.powerups.hydraxml;

import org.eclipse.jdt.annotation.NonNull;

public interface HydraXMLFactory {

	public @NonNull HydraXML newMutableElement( final @NonNull String name );
	
	default @NonNull HydraXML release( final @NonNull HydraXML x ) {
		return x;
	}
	
}
