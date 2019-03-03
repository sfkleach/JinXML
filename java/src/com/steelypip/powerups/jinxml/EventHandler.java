package com.steelypip.powerups.jinxml;

import org.eclipse.jdt.annotation.NonNull;

/**
 * This is the basic interface for incremental processing of an Element,
 * especially construction. The expectation is that the handler-methods
 * will be invoked in the order of an in-order traversal of the Element
 * when viewed as a tree.
 */
public interface EventHandler {
	
	/**
	 * Given an event, this method invokes the corresponding handler-method
	 * c.f. visitor pattern.
	 * @param e The event to handle
	 */
	default void handleEvent( Event e ) {
		e.sendTo( this );
	}
	
	/**
	 * startTagEvent is a handler method that corresponds to the recognition
	 * of the start of an element
	 * @param selector the relationship with the parent
	 * @param elementName the name of the element.
	 */
	void startTagEvent( @NonNull String selector, @NonNull String elementName );
	default void startTagEvent( @NonNull String key ) {
		this.startTagEvent( Element.DEFAULT_SELECTOR, key );
	}
	
	default void attributeEvent( @NonNull String key, @NonNull String value ) {
		this.attributeEvent( key, value, true );
	}
	
	/**
	 * attributeEvent is a handler method that corresponds to the recognition
	 * of a key-value pair within a start-tag.
	 * @param key the key
	 * @param value the value
	 * @param mustBeFirst whether or not this must be the first occurrence of the key in this element.
	 */
	void attributeEvent( @NonNull String key, @NonNull String value, boolean mustBeFirst );
			
	/**
	 * endTagEvent is a handler method that corresponds to the recognition
	 * of the end of an element
	 * @param elementName the name of the element or null if the elementName is not known
	 */
	void endTagEvent( String elementName );	
	default void endTagEvent() {
		this.endTagEvent( null );
	}
		
	/**
	 * startArrayEvent is a handler method that corresponds to the recognition
	 * of the start of an array
	 * @param selector the relationship with the parent
	 */
	void startArrayEvent( @NonNull String selector );
	default void startArrayEvent() {
		this.startArrayEvent( Element.DEFAULT_SELECTOR );
	}
	
	/**
	 * endArrayEvent is a handler method that corresponds to the recognition
	 * of the end of an array.
	 */
	void endArrayEvent();
	
	/**
	 * startObjectEvent is a handler method that corresponds to the recognition
	 * of the start of an object.
	 * @param selector the relationship with the parent
	 */
	void startObjectEvent( @NonNull String selector );
	default void startObjectEvent() {
		this.startObjectEvent( Element.DEFAULT_SELECTOR );
	}	
	
	/**
	 * endObjectEvent is a handler method that corresponds to the recognition
	 * of the end of an object.
	 */
	void endObjectEvent();
	
	/**
	 * intEvent is a handler method that corresponds to the recognition of a
	 * literal integer (JSON syntax).
	 * @param selector the relationship with the parent
	 * @param value the toString value of the integer
	 */
	void intEvent( @NonNull String selector, @NonNull String value );
	default void intEvent( @NonNull String value ) {
		this.intEvent( Element.DEFAULT_SELECTOR, value );
	}
	
	/**
	 * floatEvent is a handler method that corresponds to the recognition of a
	 * literal float (JSON syntax).
	 * @param selector the relationship with the parent
	 * @param value the toString value of the float
	 */
	void floatEvent( @NonNull String selector, @NonNull String value );
	default void floatEvent( String value ) {
		this.floatEvent( Element.DEFAULT_SELECTOR, value );
	}
	
	/**
	 * stringEvent is a handler method that corresponds to the recognition of a
	 * literal string (JSON syntax).
	 * @param selector the relationship with the parent
	 * @param value the string value, escapes must have been processed
	 */
	void stringEvent( @NonNull String selector, @NonNull String value );
	default void stringEvent( @NonNull String value ) {
		this.stringEvent( Element.DEFAULT_SELECTOR, value );
	}
	
	/**
	 * booleanEvent is a handler method that corresponds to the recognition of a
	 * literal boolean (JSON syntax).
	 * @param selector the relationship with the parent
	 * @param value the toString value of the boolean
	 */
	void booleanEvent( @NonNull String selector, @NonNull String value );
	default void booleanEvent( @NonNull String value ) {
		this.booleanEvent( Element.DEFAULT_SELECTOR, value );
	}
	
	/**
	 * nullEvent is a handler method that corresponds to the recognition of a
	 * literal null (JSON syntax).
	 * @param selector the relationship with the parent
	 * @param value should be "null"
	 */
	void nullEvent( @NonNull String selector, @NonNull String value );	
	default void nullEvent( @NonNull String value ) {
		this.nullEvent( Element.DEFAULT_SELECTOR, value );
	}

}
