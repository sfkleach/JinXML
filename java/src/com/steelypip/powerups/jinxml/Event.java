package com.steelypip.powerups.jinxml;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.jinxml.stdmodel.ConstructingEventHandler;

/**
 *  As a push-parser scans an input stream, it emits a series of Events.
 *  Each event corresponds to a handler methods from the Builder class and
 *  also corresponds to having recognised a start/end tag, an attribute, or
 *  a JSON-style literal. Another way of seeing this is that Events are 
 *  'token' like objects that push-parsers and builders share as their 
 *  common language.
 * 
 *  Importantly, Events support the Event.sendTo( EventHandler ) method
 *  that invokes the corresponding handler method of the EventHandler,
 *  which is an abstraction of the Builder class. This is an implementation
 *  of a vistor pattern and allows us to switch from an Event, which is a 
 *  kind of frozen method call, to the corresponding method call.
 *  

 */
public abstract class Event {
	
	public abstract void sendTo( EventHandler handler );

	/**
	 *  Using this method you can reverse the normal flow from Event to
	 *  method call and construct one or more Events by calling handler
	 *  methods.
	 *  
	 *  For example you can obviously construct a StartTagEvent like this:
	 *  	{@code new StartTagEvent( "selector", "elementName" ) }
	 *  Or like this:
	 *  	{@code Event.fromHandle( h -> h.startTagEvent( "selector", "elementName" ) ).findFirst().get() }
	 *  
	 * @param f function to invoke the handler
	 * @return the constructed event
	 */
	public static Stream<Event> fromHandle( Consumer< EventHandler > f ) {
		ConstructingEventHandler handler = new ConstructingEventHandler();
		f.accept( handler );
		return handler.stream();
	}
	
	abstract static class SelectorEvent extends Event {
		@NonNull String selector;
		SelectorEvent( @NonNull String selector ) {
			this.selector = selector;
		}
		public String getSelector() {
			return selector;
		}
	}

	/**
	 * This event corresponds to a start-tag, including the tag name and  
	 * the name of the selector that links it to the parent. So in the below
	 * example, the start-tag event with selector 'myselector' and name 'mytag'
	 * would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ myselector: <mytag key1='value1'> </mytag> }
	 *    ^^^^^^^^^^^^^^^^^^
	 * }</pre>
	 */
	public static class StartTagEvent extends SelectorEvent {
		
		@NonNull String name;

		public StartTagEvent( @NonNull String key ) {
			this( Element.DEFAULT_SELECTOR, key );
		}
		
		public StartTagEvent( @NonNull String selector, @NonNull String key ) {
			super( selector );
			this.name = key;
		}

		public String getName() {
			return name;
		}

		@Override
		public void sendTo( EventHandler handler ) {
			handler.startTagEvent( this.selector, this.name );
		}
		
	}
	
	/**
	 * This event corresponds to a key-value pair. So in the below
	 * example, the first attribute event would correspond to the underlined text.
	 * <pre>{@code
	 * 	<foo key1='value1' key2='value2'> </foo>
	 *       ^^^^^^^^^^^^^
	 * }</pre>
	 */
	public static class AttributeEvent extends Event {
		
		@NonNull String key;
		@NonNull String value;
		boolean solo = true;
		
		public AttributeEvent( @NonNull String key, @NonNull String value ) {
			super();
			this.key = key;
			this.value = value;
		}
		
		public AttributeEvent( @NonNull Attribute attribute ) {
			this( attribute.getKey(), attribute.getValue() );
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
	
	/**
	 * This event corresponds to an end-tag. So in the below
	 * example, the first end-tag event would correspond to the underlined text.
	 * <pre>{@code
	 * 	<foo key1='value1' key2='value2'> </foo>
	 *                                    ^^^^^^
	 * }</pre>
	 * 
	 * A standalone tag will also generate an end-event. There is no
	 * special event to mark standalone tags.
	 */
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
	
	/**
	 * This event corresponds to the start of an array. So in the below
	 * example, the first start-array event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2, 3 ] }
	 *         ^
	 * }</pre>
	 * 
	 * Handlers will typically transform handleStartArray into handleStartTag with
	 * the element-name Element.ARRAY_ELEMENT_NAME.
	 */
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
	
	/**
	 * This event corresponds to the end of an array. So in the below
	 * example, the first end-array event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2, 3 ] }
	 *                   ^
	 * }</pre>
	 * 
	 * Handlers will typically transform handleEndArray into handleEndTag with
	 * the element-name Element.ARRAY_ELEMENT_NAME.
	 */
	public static class EndArrayEvent extends Event {		
		@Override
		public void sendTo( EventHandler handler ) {
			handler.endArrayEvent();
		}		
	}
	
	/**
	 * This event corresponds to the start of an object. So in the below
	 * example, the first start-object event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2, 3 ] }
	 *  ^
	 * }</pre>
	 * 
	 * Handlers will typically transform handleStartObject into handleStartTag with
	 * the element-name Element.OBJECT_ELEMENT_NAME.
	 */
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
	
	/**
	 * This event corresponds to the end of an object. So in the below
	 * example, the first end-array event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2, 3 ] }
	 *                     ^
	 * }</pre>
	 * 
	 * Handlers will typically transform handleEndObject into handleEndTag with
	 * the element-name Element.OBJECT_ELEMENT_NAME.
	 */
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

	/**
	 * This event corresponds to an int (not a float). So in the below
	 * example, the first int event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2.0, true, null ] }
	 *           ^
	 * }</pre>
	 * 
	 * Handlers will typically transform handleIntEvent into a pair of handleStartTag
	 * handleEndTag calls with the element-name Element.INT_ELEMENT_NAME.
	 */
	public static class IntEvent extends LiteralConstantEvent {
		
		public IntEvent( @NonNull String value ) {
			super( Element.DEFAULT_SELECTOR, value );
		}

		public IntEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}

		@Override
		public void sendTo( EventHandler handler ) {
			handler.intEvent( value );
		}
		
	}
	
	/**
	 * This event corresponds to a float (not a int). So in the below
	 * example, the first float event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2.0, true, null ] }
	 *              ^^^
	 * }</pre>
	 * 
	 * Handlers will typically transform handleFloatEvent into a pair of handleStartTag
	 * handleEndTag calls with the element-name Element.FLOAT_ELEMENT_NAME.
	 */
	public static class FloatEvent extends LiteralConstantEvent {
		public FloatEvent( @NonNull String value ) {
			super( Element.DEFAULT_SELECTOR, value );
		}
		public FloatEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.floatEvent( value );
		}		
	}
	
	/**
	 * This event corresponds to a string. So in the below
	 * example, the first float event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2.0, true, null, "i am string" ] }
	 *                               ^^^^^^^^^^^^^
	 * }</pre>
	 * 
	 * The value that is passed to the constructor will have had all escape processing 
	 * fully applied.
	 * 
	 * Handlers will typically transform handleStringEvent into a pair of handleStartTag
	 * handleEndTag calls with the element-name Element.STRING_ELEMENT_NAME.
	 */
	public static class StringEvent extends LiteralConstantEvent {
		public StringEvent( @NonNull String value ) {
			super( Element.DEFAULT_SELECTOR, value );
		}
		public StringEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.stringEvent( value );
		}	
	}
	
	/**
	 * This event corresponds to a boolean. So in the below
	 * example, the first float event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2.0, true, null, "i am string" ] }
	 *                   ^^^^
	 * }</pre>
	 * 
	 * A string value that is passed to the constructor must be "true" or "false" 
	 * 
	 * Handlers will typically transform handleStringEvent into a pair of handleStartTag
	 * handleEndTag calls with the element-name Element.BOOLEAN_ELEMENT_NAME.
	 */
	public static class BooleanEvent extends LiteralConstantEvent {
		public BooleanEvent( @NonNull String value ) {
			super( Element.DEFAULT_SELECTOR, value );
		}
		public BooleanEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, value );
		}
		@SuppressWarnings("null")
		public BooleanEvent( boolean value ) {
			super( Element.DEFAULT_SELECTOR, Boolean.toString( value ) );
		}
		@SuppressWarnings("null")
		public BooleanEvent( @NonNull String selector, boolean value ) {
			super( selector, Boolean.toString( value ) );
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.booleanEvent( value );
		}			
	}
	
	/**
	 * This event corresponds to a null. So in the below
	 * example, the first null event would correspond to the underlined text.
	 * <pre>{@code
	 * 	{ foo: [ 1, 2.0, true, null, "i am string" ] }
	 *                         ^^^^
	 * }</pre>
	 * 
	 * A string value that is passed to the constructor must be "null". 
	 * 
	 * Handlers will typically transform handleStringEvent into a pair of handleStartTag
	 * handleEndTag calls with the element-name Element.NULL_ELEMENT_NAME.
	 */
	public static class NullEvent extends LiteralConstantEvent {
		public NullEvent( @NonNull String value ) {
			this( Element.DEFAULT_SELECTOR, value );
		}
		public NullEvent( @NonNull String selector, @NonNull String value ) {
			super( selector, "null" );
			if ( ! "null".equals(  value ) ) {
				throw new Alert( "An unexpected value was passed instead of 'null'" ).culprit( "Value", value );
			}
		}
		@Override
		public void sendTo( EventHandler handler ) {
			handler.booleanEvent( value );
		}	
	}
		
}
