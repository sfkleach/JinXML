package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

public interface EventHandler {
	
	default void handleEvent( Event e ) {
		e.sendTo( this );
	}
	
	void startTagEvent( @NonNull String selector, @NonNull String key );
	default void startTagEvent( @NonNull String key ) {
		this.startTagEvent( Element.DEFAULT_SELECTOR, key );
	}
	
	default void attributeEvent( @NonNull String key, @NonNull String value ) {
		this.attributeEvent( key, value, true );
	}
	
	void attributeEvent( @NonNull String key, @NonNull String value, boolean solo );
			
	void endTagEvent( String key );	
	default void endTagEvent() {
		this.endTagEvent( null );
	}
		
	void startArrayEvent( @NonNull String selector );
	default void startArrayEvent() {
		this.startArrayEvent( Element.DEFAULT_SELECTOR );
	}
	
	void endArrayEvent();
	
	void startObjectEvent( @NonNull String selector );
	default void startObjectEvent() {
		this.startObjectEvent( Element.DEFAULT_SELECTOR );
	}	
	
	void endObjectEvent();
	
	void intEvent( @NonNull String selector, @NonNull String value );
	default void intEvent( @NonNull String value ) {
		this.intEvent( Element.DEFAULT_SELECTOR, value );
	}
	
	void floatEvent( @NonNull String selector, @NonNull String value );
	default void floatEvent( String value ) {
		this.floatEvent( Element.DEFAULT_SELECTOR, value );
	}
	
	void stringEvent( @NonNull String selector, @NonNull String value );
	default void stringEvent( @NonNull String value ) {
		this.stringEvent( Element.DEFAULT_SELECTOR, value );
	}
	
	void booleanEvent( @NonNull String selector, @NonNull String value );
	default void booleanEvent( @NonNull String value ) {
		this.booleanEvent( Element.DEFAULT_SELECTOR, value );
	}
	
	void nullEvent( @NonNull String selector, @NonNull String value );	
	default void nullEvent( @NonNull String value ) {
		this.nullEvent( Element.DEFAULT_SELECTOR, value );
	}

}
