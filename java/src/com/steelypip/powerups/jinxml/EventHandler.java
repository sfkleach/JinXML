package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

public interface EventHandler {
	
	default void handleEvent( Event e ) {
		e.sendTo( this );
	}
	
	void startTagEvent( @NonNull String selector, @NonNull String key );
	default void startTagEvent( @NonNull String key ) {
		this.startTagEvent( "", key );
	}
	
	default void attributeEvent( @NonNull String key, String value ) {
		this.attributeEvent( key, value, true );
	}
	
	void attributeEvent( @NonNull String key, @NonNull String value, boolean solo );
			
	void endTagEvent( String key );	
	default void endTagEvent() {
		this.endTagEvent( null );
	}
		
	void startArrayEvent( @NonNull String selector );
	default void startArrayEvent() {
		this.startArrayEvent( "" );
	}
	
	void endArrayEvent();
	
	void startObjectEvent( @NonNull String selector );
	default void startObjectEvent() {
		this.startObjectEvent( "" );
	}	
	
	void endObjectEvent();
	
	void intEvent( @NonNull String selector, @NonNull String value );
	default void intEvent( @NonNull String value ) {
		this.intEvent( "", value );
	}
	
	void floatEvent( @NonNull String selector, @NonNull String value );
	default void floatEvent( String value ) {
		this.floatEvent( value );
	}
	
	void stringEvent( @NonNull String selector, @NonNull String value );
	default void stringEvent( @NonNull String value ) {
		this.stringEvent( "", value );
	}
	
	void booleanEvent( @NonNull String selector, @NonNull String value );
	default void booleanEvent( @NonNull String value ) {
		this.booleanEvent( "", value );
	}
	
	void nullEvent( @NonNull String selector, @NonNull String value );	
	default void nullEvent( @NonNull String value ) {
		this.nullEvent( "", value );
	}

}
