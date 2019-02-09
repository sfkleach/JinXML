package com.steelypip.powerups.jinxml;

import java.util.stream.Stream;

import com.steelypip.powerups.jinxml.implementation.ConstructingEventHandler;

public interface PushParser {
	
	default Event readEvent() {
		return this.readEvent( null );
	}
	
	Event readEvent( Event otherwise );
		
	Stream< Event > readExpression();
	
	Stream< Event > readInput();
		
	void sendEvent( EventHandler handler );
	
	void sendExpression( EventHandler handler );
	
	void sendInput( EventHandler handler );
	
	Stream< Element > readElementStream();
	Element readElement();
	Element readElement( boolean solo );
		
}
