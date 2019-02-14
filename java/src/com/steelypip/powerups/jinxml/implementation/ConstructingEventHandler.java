package com.steelypip.powerups.jinxml.implementation;

import java.util.ArrayDeque;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Event;
import com.steelypip.powerups.jinxml.EventHandler;

public class ConstructingEventHandler implements EventHandler {
	
	ArrayDeque< Event > consumer = new ArrayDeque< Event >();
	
	private void accept( Event t ) {
		this.consumer.addLast( t );
	}
	
	public Event getEvent() {
		if ( this.consumer.isEmpty() ) {
			return null;
		} else {
			return this.consumer.removeFirst();
		}
	}
	
	@Override
	public void startTagEvent( @NonNull String key ) {
		this.accept( new Event.StartTagEvent( Objects.requireNonNull( key ) ) );
	}

	@Override
	public void startTagEvent( @NonNull String selector, @NonNull String key ) {
		this.accept( new Event.StartTagEvent( selector, key ) );
	}

	@Override
	public void attributeEvent( @NonNull String key, @NonNull String value, boolean solo ) {
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
	public void startArrayEvent( @NonNull String selector ) {
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
	public void startObjectEvent( @NonNull String selector ) {
		this.accept( new Event.StartObjectEvent( selector ) );
	}

	@Override
	public void endObjectEvent() {
		this.accept( new Event.EndObjectEvent() );
	}

	@Override
	public void intEvent( @NonNull String selector, @NonNull String value ) {
		this.accept( new Event.IntEvent( selector, value ) );
	}

	@Override
	public void floatEvent( @NonNull String selector, @NonNull String value ) {
		this.accept( new Event.FloatEvent( selector, value ) );
	}

	@Override
	public void stringEvent( @NonNull String selector, @NonNull String value ) {
		this.accept( new Event.StringEvent( selector, value ) );
	}

	@Override
	public void booleanEvent( @NonNull String selector, @NonNull String value ) {
		this.accept( new Event.BooleanEvent( selector, value ) );
	}

	@Override
	public void nullEvent( @NonNull String selector, @NonNull String value ) {
		this.accept( new Event.NullEvent( selector, value ) );
	}

}
