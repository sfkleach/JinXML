package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

public class ConstructingEventHandler implements EventHandler< Event > {
	
	public static ConstructingEventHandler INSTANCE = new ConstructingEventHandler(); 

	@Override
	public Event startTagEvent( @NonNull String key ) {
		return new Event.StartTagEvent( key );
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
	public Event endArrayEvent() {
		return new Event.EndArrayEvent();
	}

	@Override
	public Event startObjectEvent() {
		return new Event.StartObjectEvent();
	}

	@Override
	public Event startEntryEvent( String key, Boolean solo ) {
		return new Event.StartEntryEvent( key, solo );
	}

	@Override
	public Event endEntryEvent() {
		return new Event.EndEntryEvent();
	}

	@Override
	public Event endObjectEvent() {
		return new Event.EndObjectEvent();
	}

	@Override
	public Event intEvent( String value ) {
		return new Event.IntEvent( value );
	}

	@Override
	public Event floatEvent( String value ) {
		return new Event.FloatEvent( value );
	}

	@Override
	public Event stringEvent( String value ) {
		return new Event.StringEvent( value );
	}

	@Override
	public Event booleanEvent( String value ) {
		return new Event.BooleanEvent( value );
	}

	@Override
	public Event nullEvent( String value ) {
		return new Event.NullEvent( value );
	}

}
