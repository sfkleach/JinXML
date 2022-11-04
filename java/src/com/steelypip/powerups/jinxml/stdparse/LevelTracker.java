package com.steelypip.powerups.jinxml.stdparse;

import java.util.ArrayDeque;
import static com.steelypip.powerups.jinxml.Element.*;

public class LevelTracker {
	
	final private ArrayDeque< Object > contexts_implementation = new ArrayDeque<>();

	public boolean isAtTopLevel() {
		return this.contexts_implementation.isEmpty();
	}
	
	private void pop( Object actualCategory ) {
		final Object expecting = this.contexts_implementation.removeLast();
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
	
	final Object ARRAY_ELEMENT_PLACEHOLDER = new Object();
	final Object OBJECT_ELEMENT_PLACEHOLDER = new Object();
	
	public void popArray() {
		pop( ARRAY_ELEMENT_PLACEHOLDER );
	}
	
	public void pushArray() {
		this.contexts_implementation.addLast( ARRAY_ELEMENT_PLACEHOLDER );
	}
	
	public void popObject() {
		pop( OBJECT_ELEMENT_PLACEHOLDER );
	}
	
	public void pushObject() {
		this.contexts_implementation.addLast( OBJECT_ELEMENT_PLACEHOLDER );
	}
	
}
