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

	
	final String example3a = "fred";
	@Test( expected=RuntimeException.class )
	public void UnknownIdentifierA() {
		Element foo = Element.readElement( new StringReader( example3a ) );
	}
	
	final String example3b = "fred true";
	@Test( expected=RuntimeException.class )
	public void UnknownIdentifierB() {
		Element foo = Element.readElement( new StringReader( example3b ) );
	}
	
	final String example4 = "fred()";
	@Test
	public void Nullary_ApplyLike() {
		Element foo = Element.readElement( new StringReader( example4 ) );
		assertEquals( "fred", foo.getName() );
		assertEquals( 0, foo.countMembers() );
		assertEquals( 0, foo.countAttributes() );
	}
	
	final String example5 = "fred</>";
	@Test
	public void FusedEmpty_ApplyLike() {
		Element foo = Element.readElement( new StringReader( example5 ) );
		assertEquals( "fred", foo.getName() );
		assertEquals( 0, foo.countMembers() );
		assertEquals( 0, foo.countAttributes() );
	}
	
	final String example6 = "fred<>()";
	@Test
	public void Empty_Nullary_ApplyLike() {
		Element foo = Element.readElement( new StringReader( example6 ) );
		assertEquals( "fred", foo.getName() );
		assertEquals( 0, foo.countMembers() );
		assertEquals( 0, foo.countAttributes() );
	}
	
	final String example7 = "fred<gabuzoe='meu'/>";
	@Test
	public void FusedNonEmpty_ApplyLike() {
		Element foo = Element.readElement( new StringReader( example7 ) );
		assertEquals( "fred", foo.getName() );
		assertEquals( 0, foo.countMembers() );
		assertEquals( 1, foo.countAttributes() );
		assertEquals( "meu", foo.getValue("gabuzoe") );
	}
	
	final String example8 = "fred(99)";
	@Test
	public void Unary_ApplyLike() {
		Element foo = Element.readElement( new StringReader( example8 ) );
		assertEquals( "fred", foo.getName() );
		assertEquals( 1, foo.countMembers() );
		assertEquals( 0, foo.countAttributes() );
	}
	
	final String example9 = "fred(99, 88, 77)";
	@Test
	public void Trinary_ApplyLike() {
		Element foo = Element.readElement( new StringReader( example9 ) );
		assertEquals( "fred", foo.getName() );
		assertEquals( 3, foo.countMembers() );
		assertEquals( 3, foo.countChildren() );
		assertEquals( "int", foo.getChild(0).getName() );
		assertEquals( "99", foo.getChild(0).getValue( "value" ) );
		assertEquals( 0, foo.countAttributes() );
	}
	
	final String example10 = "fred<a=\"x\" b='y'>(99, 88, field: 77)";
	@Test
	public void FullExpresssion_ApplyLike() {
		Element foo = Element.readElement( new StringReader( example10 ) );
		assertEquals( "fred", foo.getName() );
		assertEquals( 3, foo.countMembers() );
		assertEquals( 2, foo.countChildren() );
		assertEquals( "int", foo.getChild(0).getName() );
		assertEquals( "99", foo.getChild(0).getValue( "value" ) );
		assertEquals( "int", foo.getChild("field").getName() );
		assertEquals( "77", foo.getChild("field").getValue( "value" ) );
		assertEquals( 2, foo.countAttributes() );
		assertEquals( "x", foo.getValue( "a" ) );
		assertEquals( "y", foo.getValue( "b" ) );
	}
	
}
