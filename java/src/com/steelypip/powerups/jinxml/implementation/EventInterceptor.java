package com.steelypip.powerups.jinxml.implementation;

import com.steelypip.powerups.jinxml.EventHandler;

import java.util.ArrayDeque;
import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Event;

public abstract class EventInterceptor implements EventHandler, Consumer< Event > {
	
	public abstract void intercept( Event r );
	
	ConstructingEventHandler handler;
	ArrayDeque< Event > deque = new ArrayDeque<>();
	
	@Override
	public void accept( Event t ) {
		deque.addLast( t );
	}
	
	public void startTagEvent( final @NonNull String selector, @NonNull String key ) {
		this.handler.startTagEvent( selector, key );
	}

	public void endTagEvent( String key ) {
		this.handler.endTagEvent( key );
	}
	
	public void attributeEvent( @NonNull  String key, @NonNull String value, boolean repeat_ok ) { 
		this.handler.attributeEvent( key, value, repeat_ok );
	}
	
	public void startArrayEvent( final @NonNull String selector ) {
		this.handler.startArrayEvent( selector );
	}
	
	public void endArrayEvent() {
		this.handler.endArrayEvent();
	}
	
	public void startObjectEvent( final @NonNull String selector ) {
		this.handler.startObjectEvent( selector );
	}
	
	public void endObjectEvent() {
		this.handler.endObjectEvent();
	}
	
	public void stringEvent( @NonNull String selector, @NonNull String value ) {
		this.handler.stringEvent( selector, value );
	}
	
	public void nullEvent( @NonNull String selector, @NonNull String value ) {
		this.handler.nullEvent( selector, value );
	}
	
	public void booleanEvent( @NonNull String selector, @NonNull String value ) {
		this.handler.booleanEvent( selector, value );
	}
	
	public void intEvent( @NonNull String selector, @NonNull String value ) {
		this.handler.intEvent( selector, value );
	}
	
	public void floatEvent( @NonNull String selector, @NonNull String value ) {
		this.handler.floatEvent( selector, value );
	}
	
	
}
