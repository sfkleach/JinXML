package com.steelypip.powerups.jinxml;

public abstract class Event {
	
	public abstract <T> T sendTo( EventHandler< T > handler );
	
	abstract static class SelectorEvent extends Event {
		String selector;
		SelectorEvent( String selector ) {
			this.selector = selector;
		}
	}

	public static class StartTagEvent extends SelectorEvent {
		
		String key;

		public StartTagEvent( String key ) {
			this( "", key );
		}
		
		public StartTagEvent( String selector, String key ) {
			super( selector );
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		@Override
		public <T> T sendTo( EventHandler< T > handler ) {
			return handler.startTagEvent( this.selector, this.key );
		}
		
	}
	
	public static class AttributeEvent extends Event {
		
		String key;
		String value;
		boolean solo = true;
		
		public AttributeEvent( String key, String value ) {
			super();
			this.key = key;
			this.value = value;
		}
		
		public AttributeEvent( String key, String value, boolean solo ) {
			this( key, value );
			this.solo = solo;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

		public boolean isSolo() {
			return solo;
		}

		@Override
		public <T> T sendTo( EventHandler< T > handler ) {
			return handler.attributeEvent( key, value, solo );
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
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.endTagEvent( key );
		}
		
	}
	
	public static class StartArrayEvent extends SelectorEvent {		
		public StartArrayEvent() {
			super( "" );
		}
		public StartArrayEvent( String selector ) {
			super( selector );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.startArrayEvent( this.selector );
		}		
	}
	
	public static class EndArrayEvent extends Event {		
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.endArrayEvent();
		}		
	}
	
	public static class StartObjectEvent extends SelectorEvent {
		public StartObjectEvent() {
			super( "" );
		}
		public StartObjectEvent( String selector ) {
			super( selector );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.startObjectEvent( this.selector );
		}		
	}
	
	public static class EndObjectEvent extends Event {
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.endObjectEvent();
		}		
	}
	
	public static abstract class LiteralConstantEvent extends SelectorEvent {
		
		String value;
		
		public LiteralConstantEvent( String value ) {
			this( "", value );
		}

		public LiteralConstantEvent( String selector, String value ) {
			super( selector );
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
	}

	public static class IntEvent extends LiteralConstantEvent {
		
		public IntEvent( String value ) {
			super( "", value );
		}

		public IntEvent( String selector, String value ) {
			super( selector, value );
		}

		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.intEvent( value );
		}
		
	}
	
	public static class FloatEvent extends LiteralConstantEvent {
		public FloatEvent( String value ) {
			super( "", value );
		}
		public FloatEvent( String selector, String value ) {
			super( selector, value );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.floatEvent( value );
		}		
	}
	
	public static class StringEvent extends LiteralConstantEvent {
		public StringEvent( String value ) {
			super( "", value );
		}
		public StringEvent( String selector, String value ) {
			super( selector, value );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.stringEvent( value );
		}	
	}
	
	public static class BooleanEvent extends LiteralConstantEvent {
		public BooleanEvent( String value ) {
			super( "", value );
		}
		public BooleanEvent( String selector, String value ) {
			super( selector, value );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.booleanEvent( value );
		}			
	}
	
	public static class NullEvent extends LiteralConstantEvent {
		public NullEvent( String value ) {
			super( "", value );
		}
		public NullEvent( String selector, String value ) {
			super( selector, value );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.booleanEvent( value );
		}	
	}
		
}
