package com.steelypip.powerups.hydraxml;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.alert.Alert;



public class TestHydraXMLWalker {


	private static final String source1 = "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>";
	
	static class CountElements extends HydraXMLWalker {
		
		int count = 0;
		
		public int getCount() {
			return count;
		}

		@Override
		public void startWalk( String field, HydraXML subject ) {
			this.count += 1;
		}

		@Override
		public void endWalk( String field, HydraXML subject ) {
		}	
		
	}
	
	@Test
	public void testCountElements() {
		final CountElements c = new CountElements();
		c.walk( new HydraXMLParser( new StringReader( source1 ) ).readElement() );
		assertEquals( 2, c.getCount() );
	}
	
	static class CountAttributes extends HydraXMLWalker {
		
		private int start_count = 0;
		private int end_count = 0;

		public int getStartCount() {
			return start_count;
		}

		public int getEndCount() {
			return end_count;
		}

		@Override
		public void startWalk( String field, HydraXML subject ) {
			this.start_count += 1;
		}

		@Override
		public void endWalk( String field, HydraXML subject ) {
			this.end_count += 1;
		}
		
	}

	@Test
	public void testCountStartsAndEnds() {
		final CountAttributes c = new CountAttributes();
		c.walk( new HydraXMLParser( new StringReader( source1 ) ).readElement() );
		assertEquals( 2, c.getStartCount() );
		assertEquals( 2, c.getEndCount() );
	}
	
	static class IdWalker extends HydraXMLWalker {

		@Override
		public void startWalk( String field, HydraXML subject ) {
		}

		@Override
		public void endWalk( String field, HydraXML subject ) {
		}
		
	}
	
	@Test
	public void testPreOrder() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		HydraXML subject = new HydraXMLParser( new StringReader( tree ) ).readElement();
		if ( subject == null ) throw new Alert();
		StringBuilder b = new StringBuilder();
		for ( HydraXML m : new IdWalker().preOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "abcdefg", b.toString() );
	}

	@Test
	public void testPostOrder() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		HydraXML subject = new HydraXMLParser( new StringReader( tree ) ).readElement();
		if ( subject == null ) throw new Alert();
		StringBuilder b = new StringBuilder();
		for ( HydraXML m : new IdWalker().postOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "cdbfgea", b.toString() );
	}

}
