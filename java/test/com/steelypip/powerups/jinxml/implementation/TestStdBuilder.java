package com.steelypip.powerups.jinxml.implementation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.jinxml.Builder;
import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.stdmodel.StdBuilder;

public class TestStdBuilder {
	
	StdBuilder builder;

	@Before
	public void setup() {
		builder = new StdBuilder( false, true );		
	}

	@Test
	public void StdBuilder_Constructor() {
		assertFalse( builder.hasNext() );
		assertFalse( builder.isInProgress() );
	}
	
	@Test
	public void startTagEvent_endTagEvent_Null() {
		assertFalse( builder.hasNext() );
		assertFalse( builder.isInProgress() );
		builder.startTagEvent( "foo" );
		assertFalse( builder.hasNext() );
		assertTrue( builder.isInProgress() );
		builder.endTagEvent();
		assertTrue( builder.hasNext() );
		assertFalse( builder.isInProgress() );
		Element e = builder.next();
		assertEquals( "foo", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 0, e.countMembers() );
	}

	@Test
	public void startTagEvent_endTagEvent_Match() {
		builder.startTagEvent( "foo" );
		builder.endTagEvent( "foo" );
		Element e = builder.next();
		assertEquals( "foo", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 0, e.countMembers() );
	}

	@Test( expected=IllegalArgumentException.class )
	public void startTagEvent_endTagEvent_Mismatch() {
		builder.startTagEvent( "foo" );
		builder.endTagEvent( "bar" );
	}

	@Test( expected=IllegalStateException.class )
	public void endTagEvent_Invalid() {
		assertFalse( builder.hasNext() );
		assertFalse( builder.isInProgress() );
		builder.endTagEvent();
	}

	@Test
	public void attributeEvent_Valid() {
		builder.startTagEvent( "foo" );
		builder.attributeEvent( "alpha", "A" );
		builder.endTagEvent();
		assertTrue( builder.hasNext() );
		assertFalse( builder.isInProgress() );
		Element e = builder.next();
		assertEquals( "foo", e.getName() );
		assertEquals( 1, e.countAttributes() );
		assertEquals( "A", e.getValue( "alpha" ) );
		assertEquals( 0, e.countMembers() );
	}

	@Test( expected=Exception.class )
	public void attributeEvent_InvalidSolo() {
		builder.startTagEvent( "foo" );
		builder.attributeEvent( "alpha", "A", true );
		builder.attributeEvent( "alpha", "B", true );
	}

	@Test
	public void attributeEvent_ValidSolo() {
		builder.startTagEvent( "foo" );
		builder.attributeEvent( "alpha0", "A", true );
		builder.attributeEvent( "alpha1", "B", true );
	}

	@Test( expected=IllegalStateException.class )
	public void attributeEvent_Invalid() {
		builder.attributeEvent( "alpha", "A" );
	}
	
	public void nestingElements() {
		builder.startTagEvent( "foo" );
		builder.startTagEvent( "bar" );
		builder.endTagEvent();				
		builder.startTagEvent( "gort" );
		builder.endTagEvent();				
		builder.endTagEvent();				
		Element e = builder.next();
		assertEquals( "foo", e.getName() );
		assertEquals( 0, e.countAttributes() );
		assertEquals( 2, e.countMembers() );
	}

	@Test
	public void testSelectors() {
		builder.startTagEvent( "ElementName" );
//		builder.startEntryEvent( "Selector" );
		builder.startTagEvent( "Selector", "SubElementName" );
		builder.endTagEvent();
//		builder.endEntryEvent();
		builder.endTagEvent();				
		Element e = builder.next();
		Element s = e.getChild( "Selector" );
		assertEquals( "SubElementName", s.getName() );
		assertNull( e.getChild() );
	}
	
	@Test
	public void testPrimitives() {
		builder.intEvent( "123" );
		builder.booleanEvent( "true" );
		builder.nullEvent( "null" );
		builder.stringEvent( "hey!" );
		int count = 0;
		for ( Element e : (Iterable<Element>)(() -> this.builder ) ) {
			count += 1;
			switch ( count ) {
			case 1: 
				assertTrue( e.isIntValue() );
				assertFalse( e.isNullValue() );
				break;
			case 2:
				assertTrue( e.isBooleanValue() );
				assertFalse( e.isFloatValue() );
				break;
			case 3:
				assertTrue( e.isNullValue() );
				assertFalse( e.isStringValue() );
				break;
			case 4:
				assertTrue( e.isStringValue() );
				assertFalse( e.isBooleanValue() );
				break;
			default:
				throw new RuntimeException();
			}
		}
	}
	
	@Test
	public void hasNext_AfterPrimitive() {
		builder.intEvent( "456" );
		assertTrue( builder.hasNext() );
	}

	
}
