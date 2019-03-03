package com.steelypip.powerups.jinxml;

import java.util.Iterator;
import java.util.stream.Stream;

import com.steelypip.powerups.jinxml.stdmodel.StdBuilder;

public interface Builder extends Iterator< Element >, EventHandler {
	
	/**
	 * returns true if enough events have been received to construct an element.
	 * @return flag saying if it is safe to call next()
	 */
	boolean hasNext();

	/**
	 * return true if some events have been received but there are more open than close events. Note
	 * that a Builder starts off as in progress.
	 * @return true unless a complete set of events have been received.
	 */
	boolean isInProgress();
	
	/**
	 * constructs the next Element if one is ready for construction, otherwise will raise an exception. 
	 * Use this.hasNext() to determine whether it is safe to call this method.
	 * @return the next Element
	 */
	Element next();
	
	/**
	 *  If an Element is ready for construction it builds and returns it, otherwise it returns the value otherwise instead.
	 * @param otherwise the value to be returned if the Builder is in progress.
	 * @return the element
	 */
	Element tryNext( Element otherwise );
	
	default Element tryNext() {
		return this.tryNext( null );
	}

	/**
	 * All open states are automatically but temporarily completed; if an Element is ready for construction after the 
	 * auto-completion, it is constructed and the result will be returned, otherwise an exception is raised; the 
	 * temporarily closed states are restored to their previous state. Use this.isInProgress() to check whether it 
	 * is safe to call this method.
	 * @return the element
	 */
	Element snapshot(); 
	
	/**
	 * As for snapshot but never raises an exception but returns the value otherwise instead.
	 * @param otherwise the value to return if there are 
	 * @return the newly constructed element
	 */
	Element trySnapshot( Element otherwise );

	default void include( Element value ) {
		this.include( value, false );
	}
	
	/**
	 * Adds and shares this Element into the in-progress build. The mutability of the included value is checked 
	 * depending on the checkMutability flag. It is not an error to mix mutability this way but an occasionally 
	 * important feature. 
	 * @param value value to include
	 * @param checkMutability ensure that mutability is consistent
	 */
	void include( Element value, boolean checkMutability );

	/**
	 *  Replays a series of events which would construct the Element supplied but does so inside the in-progress 
	 *  build. This is effectively the same as this.processEvents( value.toEventIterator() ) a deep copy.
	 * @param value value to copy
	 */
	default void reconstruct( Element value ) {
		this.processEvents( value.toEventStream() );
	}

	/**
	 *  Processes a series of events, which is effectively the same as events.forEach( e -&lt; this.processEvent( e ) ).
	 * @param events the stream of events to process
	 */
	default void processEvents( Stream< Event > events ) {
		events.forEach( e -> e.sendTo( this ) );
	}
	
	/** 
	 * Handle a single event - uses visitor pattern to translate Event to call. 
	 * @param event the event to handle
	 */
	default void processEvent( Event event ) {
		event.sendTo( this );
	}

	/**
	 * A convenience method that creates a new default builder, which constructs immutable elements.
	 * @return a builder for Elements
	 */
	static Builder newBuilder() {
		return new StdBuilder( false, true );
	}
	
	/**
	 * A convenience method that creates a new default builder, which constructs elements. The
	 * elements are mutable/immutable depending on the flag mutable.
	 * @param mutable if true the constructed Events are mutable, other immutable
	 * @return a builder for Elements
	 */
	static Builder newBuilder( final boolean mutable ) {
		return new StdBuilder( mutable, true );
	}
	
	/**
	 * A convenience method that creates a new default builder, which constructs elements. The
	 * elements are mutable/immutable depending on the flag mutable. The builder allows the
	 * constructed elements to queue up internally if allow_queuing is true, otherwise the caller
	 * must be sure to retrieve the newly constructed element before others can be started.
	 * @param mutable if true the constructed Events are mutable, other immutable
	 * @param allows_queuing if true then the builder can construct many elements before the
	 * caller tries to retrieve them.
	 * @return a builder for Elements
	 */
	static Builder newBuilder( final boolean mutable, final boolean allows_queuing ) {
		return new StdBuilder( mutable, allows_queuing );
	}
	
}
