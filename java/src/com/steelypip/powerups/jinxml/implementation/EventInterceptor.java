package com.steelypip.powerups.jinxml.implementation;

import com.steelypip.powerups.jinxml.EventHandler;

public interface EventInterceptor< Return > {
	
	void intercept( Return r );
	EventHandler< Return > handler();
	
	default void startTagEvent( final String selector, String key ) {
		this.intercept( this.handler().startTagEvent( selector, key ) );
	}

	default void endTagEvent( String key ) {
		this.intercept( this.handler().endTagEvent( key ) );
	}
	
	default void attributeEvent( String key, String value, boolean repeat_ok ) { 
		this.intercept( this.handler().attributeEvent( key, value, repeat_ok ) );
	}
	
	default void startArrayEvent( final String selector ) {
		this.intercept( this.handler().startArrayEvent( selector ) );
	}
	
	default void endArrayEvent() {
		this.intercept( this.handler().endArrayEvent() );
	}
	
	default void startObjectEvent( final String selector ) {
		this.intercept( this.handler().startObjectEvent( selector ) );
	}
	
	default void endObjectEvent() {
		this.intercept( this.handler().endObjectEvent() );
	}
	
	default void stringEvent( String selector, String value ) {
		this.intercept( this.handler().stringEvent( selector, value ) );
	}
	
	default void nullEvent( String selector, String value ) {
		this.intercept( this.handler().nullEvent( selector, value ) );
	}
	
	default void booleanEvent( String selector, String value ) {
		this.intercept( this.handler().booleanEvent( selector, value ) );
	}
	
	default void intEvent( String selector, String value ) {
		this.intercept( this.handler().intEvent( selector, value ) );
	}
	
	default void floatEvent( String selector, String value ) {
		this.intercept( this.handler().floatEvent( selector, value ) );
	}
	
	
}
