package com.steelypip.powerups.jinxml.implementation;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Event;
import com.steelypip.powerups.jinxml.EventHandler;

public class ConstructingEventHandler implements EventHandler< Event > {
	
	@Override
	public Event startTagEvent( @NonNull String key ) {
		return new Event.StartTagEvent( key );
	}

	@Override
	public Event startTagEvent( String selector, @NonNull String key ) {
		return new Event.StartTagEvent( selector, key );
	}

	@Override
	public Event attributeEvent( @NonNull String key, String value, boolean solo ) {
		return new Event.AttributeEvent( key, value, solo );
	}

	@Override
	public Event endTagEvent( String key ) {
		return new Event.EndTagEvent( key );
	}

	@Override
	public Event startArrayEvent() {
		return new Event.StartArrayEvent();
	}

	@Override
	public Event startArrayEvent( String selector ) {
		return new Event.StartArrayEvent( selector );
	}

	@Override
	public Event endArrayEvent() {
		return new Event.EndArrayEvent();
	}

	@Override
	public Event startObjectEvent() {
		return new Event.StartObjectEvent();
	}

	@Override
	public Event startObjectEvent( String selector ) {
		return new Event.StartObjectEvent( selector );
	}

	@Override
	public Event endObjectEvent() {
		return new Event.EndObjectEvent();
	}

	@Override
	public Event intEvent( String selector, String value ) {
		return new Event.IntEvent( selector, value );
	}

	@Override
	public Event floatEvent( String selector, String value ) {
		return new Event.FloatEvent( selector, value );
	}

	@Override
	public Event stringEvent( String selector, String value ) {
		return new Event.StringEvent( selector, value );
	}

	@Override
	public Event booleanEvent( String selector, String value ) {
		return new Event.BooleanEvent( selector, value );
	}

	@Override
	public Event nullEvent( String selector, String value ) {
		return new Event.NullEvent( selector, value );
	}

}
