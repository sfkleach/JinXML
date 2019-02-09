package com.steelypip.powerups.jinxml.implementation;

import java.util.ArrayDeque;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Event;
import com.steelypip.powerups.jinxml.EventHandler;

public class ConstructingEventHandler implements EventHandler {
	
	ArrayDeque< Event > consumer = new ArrayDeque< Event >();
	
	private void accept( Event t ) {
		this.consumer.addLast( t );
	}
	
	public Event getEvent() {
		return this.consumer.removeFirst();
	}
	
	@Override
	public void startTagEvent( @NonNull String key ) {
		this.accept( new Event.StartTagEvent( key ) );
	}

	@Override
	public void startTagEvent( String selector, @NonNull String key ) {
		this.accept( new Event.StartTagEvent( selector, key ) );
	}

	@Override
	public void attributeEvent( @NonNull String key, String value, boolean solo ) {
		this.accept( new Event.AttributeEvent( key, value, solo ) );
	}

	@Override
	public void endTagEvent( String key ) {
		this.accept( new Event.EndTagEvent( key ) );
	}

	@Override
	public void startArrayEvent() {
		this.accept( new Event.StartArrayEvent() );
	}

	@Override
	public void startArrayEvent( String selector ) {
		this.accept( new Event.StartArrayEvent( selector ) );
	}

	@Override
	public void endArrayEvent() {
		this.accept( new Event.EndArrayEvent() );
	}

	@Override
	public void startObjectEvent() {
		this.accept( new Event.StartObjectEvent() );
	}

	@Override
	public void startObjectEvent( String selector ) {
		this.accept( new Event.StartObjectEvent( selector ) );
	}

	@Override
	public void endObjectEvent() {
		this.accept( new Event.EndObjectEvent() );
	}

	@Override
	public void intEvent( String selector, String value ) {
		this.accept( new Event.IntEvent( selector, value ) );
	}

	@Override
	public void floatEvent( String selector, String value ) {
		this.accept( new Event.FloatEvent( selector, value ) );
	}

	@Override
	public void stringEvent( String selector, String value ) {
		this.accept( new Event.StringEvent( selector, value ) );
	}

	@Override
	public void booleanEvent( String selector, String value ) {
		this.accept( new Event.BooleanEvent( selector, value ) );
	}

	@Override
	public void nullEvent( String selector, String value ) {
		this.accept( new Event.NullEvent( selector, value ) );
	}

}
