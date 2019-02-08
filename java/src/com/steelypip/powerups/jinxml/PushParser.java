package com.steelypip.powerups.jinxml;

import java.util.stream.Stream;

import com.steelypip.powerups.jinxml.implementation.ConstructingEventHandler;

public interface PushParser {
	
	default Event readEvent() {
		return this.readEvent( null );
	}
	
	default Event readEvent( Event otherwise ) {
		return this.readHandledEvent( new ConstructingEventHandler(), otherwise );
	}
	
	<T> T readHandledEvent( EventHandler< T > handler, T otherwise );
	
	default Stream< Event > readExpression() {
		return this.readHandledExpression( new ConstructingEventHandler() );
	}
	
	<T> Stream< T > readHandledExpression( EventHandler< T > handler ); 
	
	default Stream< Event > readInput() {
		return this.readHandledInput( new ConstructingEventHandler() );
	}
	
	<T> Stream< T > readHandledInput( EventHandler< T > handler ); 
	
	default void sendEvent( EventHandler< Void > handler ) {
		this.readHandledEvent( handler, null );
	}
	
	default void sendExpression( EventHandler< Void > handler ) {
		this.readHandledExpression( handler );
	}
	
	default void sendInput( EventHandler< Void > handler ) {
		this.readHandledInput( handler );
	}
	
	Stream< Element > readElementStream();
	Element readElement();
	Element readElement( boolean solo );
		
}
