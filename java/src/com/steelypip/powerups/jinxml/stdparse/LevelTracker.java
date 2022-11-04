package com.steelypip.powerups.jinxml.stdparse;

import java.util.ArrayDeque;
import static com.steelypip.powerups.jinxml.Element.*;

public class LevelTracker {
	
	final private ArrayDeque< String > contexts_implementation = new ArrayDeque<>();

	public boolean isAtTopLevel() {
		return this.contexts_implementation.isEmpty();
	}
	
	private void pop( String actualCategory ) {
		String expecting = this.contexts_implementation.removeLast();
		if ( actualCategory == null ) {
			// No checking.
		} else if ( ! actualCategory.equals( expecting ) ) {
			throw new IllegalStateException(String.format( "Found {} but was expecting {}", actualCategory, expecting ) );
		}
	}
	
	public void popElement( String tag ) {
		pop( tag );
	}
	
	public void pushElement( String tag ) {
		this.contexts_implementation.addLast( tag );
	}
	
	public void popArray() {
		pop( ARRAY_ELEMENT_NAME );
	}
	
	public void pushArray() {
		this.contexts_implementation.addLast( ARRAY_ELEMENT_NAME );
	}
	
	public void popObject() {
		pop( OBJECT_ELEMENT_NAME );
	}
	
	public void pushObject() {
		this.contexts_implementation.addLast( OBJECT_ELEMENT_NAME );
	}
	
}
