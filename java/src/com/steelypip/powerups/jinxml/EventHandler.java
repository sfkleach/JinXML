package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

public interface EventHandler {
	
	default void handleEvent( Event e ) {
		e.sendTo( this );
	}
	
	void startTagEvent( String selector, @NonNull String key );
	default void startTagEvent( @NonNull String key ) {
		this.startTagEvent( "", key );
	}
	
	default void attributeEvent( @NonNull String key, String value ) {
		this.attributeEvent( key, value, true );
	}
	
	void attributeEvent( @NonNull String key, String value, boolean solo );
			
	default void endTagEvent() {
		this.endTagEvent( null );
	}
	
	void endTagEvent( String key );	
		
	void startArrayEvent( String selector );
	default void startArrayEvent() {
		this.startArrayEvent( "" );
	}
	
	void endArrayEvent();
	
	void startObjectEvent( String selector );
	default void startObjectEvent() {
		this.startObjectEvent( "" );
	}	
	
	void endObjectEvent();
	
	void intEvent( String selector, String value );
	default void intEvent( String value ) {
		this.intEvent( "", value );
	}
	
	void floatEvent( String selector, String value );
	default void floatEvent( String value ) {
		this.floatEvent( value );
	}
	
	void stringEvent( String selector, String value );
	default void stringEvent( String value ) {
		this.stringEvent( "", value );
	}
	
	void booleanEvent( String selector, String value );
	default void booleanEvent( String value ) {
		this.booleanEvent( "", value );
	}
	
	void nullEvent( String selector, String value );	
	default void nullEvent( String value ) {
		this.nullEvent( "", value );
	}

}
