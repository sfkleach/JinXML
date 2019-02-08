package com.steelypip.powerups.jinxml.implementation;

import java.util.ArrayDeque;

public interface LevelTracker {
	
	static enum Context {
		ELEMENT( "element" ),
		ARRAY( "array" ),
		OBJECT( "object" );
		
		public String name;
		Context( String name ) {
			this.name= name;
		}
	}

	ArrayDeque< Context > contexts();
	
	default boolean isAtTopLevel() {
		return contexts().isEmpty();
	}
	
	default void pop( Context actualCategory ) {
		Context expecting = contexts().removeLast();
		if ( actualCategory != expecting ) {
			throw new IllegalStateException(String.format( "Found {} but was expecting {}", actualCategory.name, expecting.name ) );
		}
	}
	
	default void popElement() {
		pop( Context.ELEMENT );
	}
	
	default void pushElement() {
		contexts().addLast( Context.ELEMENT );
	}
	
	default void popArray() {
		pop( Context.ARRAY );
	}
	
	default void pushArray() {
		contexts().addLast( Context.ARRAY );
	}
	
	default void popObject() {
		pop( Context.OBJECT );
	}
	
	default void pushObject() {
		contexts().addLast( Context.OBJECT );
	}
	
}
