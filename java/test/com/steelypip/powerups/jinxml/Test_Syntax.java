package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class Test_Syntax {

	final String example1 = ( 
			"[ </array>\n"
		);

		@Test( expected=RuntimeException.class )
		public void MismatchedBrackets() {
			Element foo = Element.readElement( new StringReader( example1 ) );
			assertTrue( foo.hasName( "array" ) );
		}

}
