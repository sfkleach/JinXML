package com.steelypip.powerups.hydraxml;

import java.util.ArrayDeque;

public class LevelTracker {
	
	static enum Context {
		ELEMENT( "element" ),
		ARRAY( "array" ),
		OBJECT( "object" );
		
		public String name;
		Context( String name ) {
			this.name= name;
		}
	}
	
	ArrayDeque< Context > contexts = new ArrayDeque<>();
	
	public boolean isAtTopLevel() {
		return contexts.isEmpty();
	}
	
	void pop( Context actual ) {
		Context expecting = contexts.removeLast();
		if ( actual != expecting ) {
			throw new IllegalStateException(String.format( "Found {} but was expecting {}", actual.name, expecting.name ) );
		}
	}
	
	protected void popElement() {
		pop( Context.ELEMENT );
	}
	
	protected void pushElement() {
		contexts.add( Context.ELEMENT );
	}
	
	protected void popArray() {
		pop( Context.ARRAY );
	}
	
	protected void pushArray() {
		contexts.add( Context.ARRAY );
	}
	
	protected void popObject() {
		pop( Context.OBJECT );
	}
	
	protected void pushObject() {
		contexts.add( Context.OBJECT );
	}
	
}
