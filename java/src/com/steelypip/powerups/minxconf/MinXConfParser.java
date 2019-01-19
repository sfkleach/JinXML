package com.steelypip.powerups.minxconf;

import java.io.Reader;

import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.minxml.MinXML;
import com.steelypip.powerups.minxml.MinXMLBuilder;
import com.steelypip.powerups.minxson.MinXSONParser;

/**
 * This class implements a parser that consumes MinXConf expressions, 
 * which are designed to be suitable for writing simple settings files. 
 * It is a very thin layer on top of MinXSONParser, effectively translating
 * a MinXConfParser#read() into a MinXSONParser#readBindings().
 */
public class MinXConfParser {
	
	private MinXSONParser parser;

	public MinXConfParser( CharRepeater rep, MinXMLBuilder parent, char... extensions ) {
		this.parser = new MinXSONParser( rep, parent, extensions );
	}

	public MinXConfParser( Reader rep, char... extensions ) {
		this.parser = new MinXSONParser( rep, extensions );
	}

	public MinXConfParser( Reader rep, MinXMLBuilder parent, char... extensions ) {
		this.parser = new MinXSONParser( rep, parent, extensions );
	}
	
	public MinXML read() {
		return this.parser.readBindings();
	}

}
