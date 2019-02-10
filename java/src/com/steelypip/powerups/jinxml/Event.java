package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

public abstract class Event {
	
	public abstract void sendTo( EventHandler handler );
	
	abstract static class SelectorEvent extends Event {
		@NonNull String selector;
		SelectorEvent( @NonNull String selector ) {
			this.selector = selector;
		}
	}

	public static class StartTagEvent extends SelectorEvent {
		
		@NonNull String key;

		public StartTagEvent( @NonNull String key ) {
			this( "", key );
		}
		
		public StartTagEvent( @NonNull String selector, @NonNull String key ) {
			super( selector );
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		@Override
		public void sendTo( EventHandler handler ) {
			handler.startTagEvent( this.selector, this.key );
		}
		
	}
	
	public static class AttributeEvent extends Event {
		
		@NonNull String key;
		@NonNull String value;
		boolean solo = true;
		
		public AttributeEvent( @NonNull String key, @NonNull String value ) {
			super();
			this.key = key;
			this.value = value;
		}
		
		public AttributeEvent( @NonNull String key, @NonNull String value, boolean solo ) {
			this( key, value );
			this.solo = solo;
		}

		public @NonNull String getKey() {
			return key;
		}

		public @NonNull String getValue() {
			return value;
		}

		public boolean isSolo() {
			return solo;
		}

		@Override
		public void sendTo( EventHandler handler ) {
			handler.attributeEvent( key, value, solo );
		}
		
	}
	
	public static class EndTagEvent extends Event {
		
		String key = null;

		public EndTagEvent( String key ) {
			this.key = key;
		}
		
		public EndTagEvent() {
		}

		public String getKey() {
			return key;
		}

		@Override
		public void sendTo( EventHandler handler ) {
			handler.endTagEvent( key );
		}
		
	}
	
	public static class StartArrayEvent extends SelectorEvent {		
		public StartArrayEvent() {
			super( "" );
		}
		public StartArrayEvent( @NonNull String selector ) {
			super( selector );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.startArrayEvent( this.selector );
		}		
	}
	
	public static class EndArrayEvent extends Event {		
		@Override
		public void sendTo( EventHandler handler ) {
			handler.endArrayEvent();
		}		
	}
	
	public static class StartObjectEvent extends SelectorEvent {
		public StartObjectEvent() {
			super( "" );
		}
		public StartObjectEvent( @NonNull String selector ) {
			super( selector );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.startObjectEvent( this.selector );
		}		
	}
	
	public static class EndObjectEvent extends Event {
		@Override
		public void sendTo( EventHandler handler ) {
			handler.endObjectEvent();
		}		
	}
	
	public static abstract class LiteralConstantEvent extends SelectorEvent {
		
		@NonNull String value;
		
		public LiteralConstantEvent( @NonNull String value ) {
			this( "", value );
		}

		public LiteralConstantEvent( @NonNull String selector, @NonNull String value ) {
			super( selector );
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
	}

	public static class IntEvent extends LiteralConstantEvent {
		
		public IntEvent( @NonNull String value ) {
			super( "", value );
		}

		public IntEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}

		@Override
		public void sendTo( EventHandler handler ) {
			handler.intEvent( value );
		}
		
	}
	
	public static class FloatEvent extends LiteralConstantEvent {
		public FloatEvent( @NonNull String value ) {
			super( "", value );
		}
		public FloatEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.floatEvent( value );
		}		
	}
	
	public static class StringEvent extends LiteralConstantEvent {
		public StringEvent( @NonNull String value ) {
			super( "", value );
		}
		public StringEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.stringEvent( value );
		}	
	}
	
	public static class BooleanEvent extends LiteralConstantEvent {
		public BooleanEvent( @NonNull String value ) {
			super( "", value );
		}
		public BooleanEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.booleanEvent( value );
		}			
	}
	
	public static class NullEvent extends LiteralConstantEvent {
		public NullEvent( @NonNull String value ) {
			super( "", value );
		}
		public NullEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.booleanEvent( value );
		}	
	}
		
}
