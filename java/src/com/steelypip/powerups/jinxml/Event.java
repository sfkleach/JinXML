package com.steelypip.powerups.jinxml;

public abstract class Event {
	
	public abstract <T> T sendTo( EventHandler< T > handler );

	public static class StartTagEvent extends Event {
		
		String key;

		public StartTagEvent( String key ) {
			super();
			this.key = key;
		}

		public String getKey() {
			return key;
		}

		@Override
		public <T> T sendTo( EventHandler< T > handler ) {
			return handler.startTagEvent( key );
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
	
	public static class StartArrayEvent extends Event {
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.startArrayEvent();
		}		
	}
	
	public static class EndArrayEvent extends Event {		
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.endArrayEvent();
		}		
	}
	
	public static class StartObjectEvent extends Event {
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.startObjectEvent();
		}		
	}
	
	public static class EndObjectEvent extends Event {
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.endObjectEvent();
		}		
	}
	
	public static class StartEntryEvent extends Event {
		
		String key = null;
		boolean solo = true;
		
		public StartEntryEvent() {
			super();
		}
	
		public StartEntryEvent( String key ) {
			super();
			this.key = key;
		}
	
		public StartEntryEvent( String key, boolean solo ) {
			super();
			this.key = key;
			this.solo = solo;
		}

		public String getKey() {
			return key;
		}

		public boolean isSolo() {
			return solo;
		}
		
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.startEntryEvent( key, solo );
		}
	}
	
	public static class EndEntryEvent extends Event {
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.endEntryEvent();
		}		
	}
	
	public static abstract class LiteralConstantEvent extends Event {
		
		String value;

		public LiteralConstantEvent( String value ) {
			super();
			this.value = value;
		}

		public String getValue() {
			return value;
		}
		
	}

	public static class IntEvent extends LiteralConstantEvent {
		
		public IntEvent( String value ) {
			super( value );
		}

		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.intEvent( value );
		}
		
	}
	
	public static class FloatEvent extends LiteralConstantEvent {
		public FloatEvent( String value ) {
			super( value );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.floatEvent( value );
		}		
	}
	
	public static class StringEvent extends LiteralConstantEvent {
		public StringEvent( String value ) {
			super( value );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.stringEvent( value );
		}	
	}
	
	public static class BooleanEvent extends LiteralConstantEvent {
		public BooleanEvent( String value ) {
			super( value );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.booleanEvent( value );
		}			
	}
	
	public static class NullEvent extends LiteralConstantEvent {
		public NullEvent( String value ) {
			super( value );
		}
		@Override
		public <T> T sendTo( EventHandler<T> handler ) {
			return handler.booleanEvent( value );
		}	
	}
		
}
