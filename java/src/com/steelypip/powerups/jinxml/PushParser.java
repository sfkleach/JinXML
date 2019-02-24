package com.steelypip.powerups.jinxml;

import java.io.Reader;
import java.util.stream.Stream;

import com.steelypip.powerups.jinxml.stdparse.StdPushParser;

public interface PushParser {
	
	default Event readEvent() {
		return this.readEvent( null );
	}
	
	Event readEvent( Event otherwise );
		
	Stream< Event > readExpression();
	
	Stream< Event > readInput();
		
	void sendEvent( EventHandler handler );
	
	void sendExpression( EventHandler handler );
	
	void sendInput( EventHandler handler );
	
	Stream< Element > readElementStream();
	Element readElement();
	Element readElement( boolean solo );

	static PushParser newPushParser( Reader reader ) {
		return new StdPushParser( reader, true );
	}
		
	static PushParser newPushParser( Reader reader, boolean expandLiterals ) {
		return new StdPushParser( reader, expandLiterals );
	}
		
}
