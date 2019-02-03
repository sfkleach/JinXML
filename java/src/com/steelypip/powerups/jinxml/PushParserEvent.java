package com.steelypip.powerups.jinxml;

/*
datatype Event =
  StartTagEvent( String key ) |
  AttributeEvent( String key, String value, Boolean solo = true ) |
  EndTagEvent( String? key = null ) |
  StartArrayEvent() |
  EndArrayEvent() |
  StartObjectEvent() |
  StartEntryEvent( String? key = null, Boolean solo = true ) |
  EndEntryEvent() |
  EndObjectEvent() |
  IntEvent( String value ) |
  FloatEvent( String value ) |
  StringEvent( String value ) |
  BooleanEvent( String value ) |
  NullEvent( String value )
 */

public class PushParserEvent {

	public static class StartTagEvent extends PushParserEvent {
		
		String key;

		public StartTagEvent( String key ) {
			super();
			this.key = key;
		}

		public String getKey() {
			return key;
		}
		
	}
	
	public static class AttributeEvent extends PushParserEvent {
		
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
		
	}
	
	public static class EndTagEvent extends PushParserEvent {
		
		String key = null;

		public EndTagEvent( String key ) {
			this.key = key;
		}
		
		public EndTagEvent() {
		}

		public String getKey() {
			return key;
		}
		
	}
	

	public static class StartArrayEvent extends PushParserEvent {		
	}
	
	public static class EndArrayEvent extends PushParserEvent {		
	}
	
	public static class StartObjectEvent extends PushParserEvent {
	}
	
	public static class EndObjectEvent extends PushParserEvent {
	}
	
	public static class StartEntryEvent extends PushParserEvent {
		
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
		
	}
	
	public static class EndEntryEvent extends PushParserEvent {
	}
	
//	  IntEvent( String value ) |
//	  FloatEvent( String value ) |
//	  StringEvent( String value ) |
//	  BooleanEvent( String value ) |
//	  NullEvent( String value )
	
	public static class LiteralConstantEvent extends PushParserEvent {
		
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
		
	}
	
	public static class FloatEvent extends LiteralConstantEvent {
		public FloatEvent( String value ) {
			super( value );
		}
	}
	
	public static class StringEvent extends LiteralConstantEvent {
		public StringEvent( String value ) {
			super( value );
		}
	}
	
	public static class BooleanEvent extends LiteralConstantEvent {
		public BooleanEvent( String value ) {
			super( value );
		}
	}
	
	public static class NullEvent extends LiteralConstantEvent {
		public NullEvent( String value ) {
			super( value );
		}
	}
		
}
