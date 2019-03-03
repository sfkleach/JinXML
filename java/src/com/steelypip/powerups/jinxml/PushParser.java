package com.steelypip.powerups.jinxml;

import java.io.Reader;
import java.util.stream.Stream;

import com.steelypip.powerups.jinxml.stdparse.StdPushParser;

/**
 * A PushParser is a wrapper around a Reader. On request, the push
 * parser draws from that reader, incrementally parsing the input,
 * and generates Events that can be directed to an EventHandler or 
 * simply returned. 
 * 
 * A particularly elegant use of a push-parser is to return an 
 * event stream, which will incrementally draw from the reader as events 
 * are taken from the stream.
 */
public interface PushParser {
	
	/**
	 * Draws from the input source to generate the next Event.
	 * If the end of the event stream has been encountered then
	 * null is returned.
	 * @return the next event
	 */
	default Event readEvent() {
		return this.readEvent( null );
	}
	
	/**
	 * Draws from the input source to generate the next Event.
	 * If the end of the event stream has been encountered then
	 * otherwise is returned.
	 * @param otherwise a default event (or null) to return at end of input
	 * @return the next event
	 */
	Event readEvent( Event otherwise );
		
	/**
	 * Returns a stream that will draw-on-demand from the input to return
	 * the events corresponding to a single JinXML expression.
	 * @return
	 */
	Stream< Event > readExpression();
	
	/**
	 * Returns a stream that will draw-on-demand from the input to return
	 * events until the input is exhausted.
	 * @return
	 */
	Stream< Event > readInput();
		
	/**
	 * Draws from the input source to generate the next Event
	 * which is sent to the handler.
	 * If the end of the event stream has been encountered then
	 * no action is taken.
	 * @param handler the event handler
	 */
	void sendEvent( EventHandler handler );
	
	/**
	 * Draws from the input source to send the events of
	 * a single JinXML expression to an event handler.
	 * @param handler the event handler
	 */
	void sendExpression( EventHandler handler );

	/**
	 * Draws from the input source to send the events of
	 * to an event handler. Continues until the input is
	 * exhausted.
	 * @param handler the event handler
	 */
	void sendInput( EventHandler handler );
	
	/**
	 * Returns a stream of immutable elements constructed from
	 * the input stream, which is drawn-from on-demand. 
	 * @return a stream of immutable elements
	 */
	Stream< Element > readElementStream();
		
	/**
	 * Returns a single element constructed from
	 * the input stream, which is drawn-from on-demand. 
	 * @return a single immutable element (or null if the stream is exhausted).
	 */
	Element readElement();

	/**
	 * A convenience method that constructs a default push parser from a reader.
	 * JSON Literals are automatically expanded into start/end-tag events. 
	 * @param reader
	 * @return a push parser
	 */
	static PushParser newPushParser( Reader reader ) {
		return new StdPushParser( reader, true );
	}
		
	/**
	 * A convenience method that constructs a default push parser from a reader.
	 * JSON Literals are automatically expanded into start/end-tag events depending 
	 * on expandLiterals 
	 * @param reader
	 * @param expandLiterals if true then JSON literals are automatically treated as start/end tags.
	 * @return a push parser
	 */
	static PushParser newPushParser( Reader reader, boolean expandLiterals ) {
		return new StdPushParser( reader, expandLiterals );
	}
		
}
