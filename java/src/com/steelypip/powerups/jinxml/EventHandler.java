package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

public interface EventHandler< T > {
	
	default T handleEvent( Event e ) {
		return e.sendTo( this );
	}
	
	T startTagEvent( @NonNull String key );
	
	default T attributeEvent( @NonNull String key, String value ) {
		return this.attributeEvent( key, value, true );
	}
	
	T attributeEvent( @NonNull String key, String value, boolean solo );
			
	default T endTagEvent() {
		return this.endTagEvent( null );
	}
	
	T endTagEvent( String key );	
		
	T startArrayEvent();
	
	T endArrayEvent();
	
	T startObjectEvent();
	
	default T startEntryEvent() {
		return this.startEntryEvent( null, true );
	}
	
	default T startEntryEvent( boolean solo ) {
		return this.startEntryEvent( null, solo );
	}
	
	default T startEntryEvent( String key ) {
		return this.startEntryEvent( key, true );
	}
	
	T startEntryEvent( String key, Boolean solo );
	
	T endEntryEvent();		
	
	T endObjectEvent();
	
	T intEvent( String value );
	
	T floatEvent( String value );
	
	T stringEvent( String value );
	
	T booleanEvent( String value );
	
	T nullEvent( String value );	

}
