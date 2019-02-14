package com.steelypip.powerups.jimxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.jinxml.Element;


public class Test_Shebang_Syntax {
	
	final String example1 = ( 
		"#!/usr/bin/env whoop\n" +
		"<foo/>\n"
	);

	@Test
	public void test_SheBangAtStartOfFile() {
		Element foo = Element.readElement( new StringReader( example1 ) );
		assertTrue( foo.hasName( "foo" ) );
	}
	
	final String example2 = ( 
			"<foo>\n" +		
			"#!/usr/bin/env whoop\n" +
			"<foo/>\n"
		);

	@Test( expected=RuntimeException.class )
	public void test_SheBangAsComment() {
		Element foo = Element.readElement( new StringReader( example2 ) );
		assertTrue( foo.hasName( "foo" ) );
	}

}
