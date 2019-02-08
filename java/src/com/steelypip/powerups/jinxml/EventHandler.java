package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

public interface EventHandler< T > {
	
	default T handleEvent( Event e ) {
		return e.sendTo( this );
	}
	
	T startTagEvent( String selector, @NonNull String key );
	default T startTagEvent( @NonNull String key ) {
		return this.startTagEvent( "", key );
	}
	
	default T attributeEvent( @NonNull String key, String value ) {
		return this.attributeEvent( key, value, true );
	}
	
	T attributeEvent( @NonNull String key, String value, boolean solo );
			
	default T endTagEvent() {
		return this.endTagEvent( null );
	}
	
	T endTagEvent( String key );	
		
	T startArrayEvent( String selector );
	default T startArrayEvent() {
		return this.startArrayEvent( "" );
	}
	
	T endArrayEvent();
	
	T startObjectEvent( String selector );
	default T startObjectEvent() {
		return this.startObjectEvent( "" );

	}	
	
	T endObjectEvent();
	
	T intEvent( String selector, String value );
	default T intEvent( String value ) {
		return this.intEvent( "", value );
	}
	
	T floatEvent( String selector, String value );
	default T floatEvent( String value ) {
		return this.floatEvent( value );
	}
	
	
	T stringEvent( String selector, String value );
	default T stringEvent( String value ) {
		return this.stringEvent( "", value );
	}
	
	T booleanEvent( String selector, String value );
	default T booleanEvent( String value ) {
		return this.booleanEvent( "", value );
	}
	
	T nullEvent( String selector, String value );	
	default T nullEvent( String value ) {
		return this.nullEvent( "", value );
	}

}
