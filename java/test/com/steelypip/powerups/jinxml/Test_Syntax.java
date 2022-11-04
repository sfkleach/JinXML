package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

public class Test_Syntax {

	final String example1 = ( 
		"[ </array>\n"
	);

	@Test( expected=RuntimeException.class )
	public void MismatchedBrackets1() {
		Element foo = Element.readElement( new StringReader( example1 ) );
	}

	final String example2 = ( 
		"{ </object>\n"
	);

	@Test( expected=RuntimeException.class )
	public void MismatchedBrackets2() {
		Element foo = Element.readElement( new StringReader( example2 ) );
	}

}
