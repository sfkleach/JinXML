package com.steelypip.powerups.minxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import com.steelypip.powerups.alert.Alert;


public class TestMinXMLWalker {


	private static final String source1 = "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>";
	
	static class CountElements extends MinXMLWalker {
		
		int count = 0;
		
		public int getCount() {
			return count;
		}

		@Override
		public void startWalk( MinXML subject ) {
			this.count += 1;
		}

		@Override
		public void endWalk( MinXML subject ) {
		}		
		
	}
	
	@Test
	public void testCountElements() {
		final CountElements c = new CountElements();
		c.walk( new MinXMLParser( new StringReader( source1 ) ).readElement() );
		assertEquals( 2, c.getCount() );
	}
	
	static class CountAttributes extends MinXMLWalker {
		
		private int start_count = 0;
		private int end_count = 0;

		public int getStartCount() {
			return start_count;
		}

		public int getEndCount() {
			return end_count;
		}

		@Override
		public void startWalk( MinXML subject ) {
			this.start_count += 1;
		}

		@Override
		public void endWalk( MinXML subject ) {
			this.end_count += 1;
		}
		
	}

	@Test
	public void testCountStartsAndEnds() {
		final CountAttributes c = new CountAttributes();
		c.walk( new MinXMLParser( new StringReader( source1 ) ).readElement() );
		assertEquals( 2, c.getStartCount() );
		assertEquals( 2, c.getEndCount() );
	}
	
	static class IdWalker extends MinXMLWalker {

		@Override
		public void startWalk( MinXML subject ) {
		}

		@Override
		public void endWalk( MinXML subject ) {
		}
		
	}
	
	@Test
	public void testPreOrder() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		MinXML subject = new MinXMLParser( new StringReader( tree ) ).readElement();
		if ( subject == null ) throw new Alert();
		StringBuilder b = new StringBuilder();
		for ( MinXML m : new IdWalker().preOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "abcdefg", b.toString() );
	}

	@Test
	public void testPostOrder() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		MinXML subject = new MinXMLParser( new StringReader( tree ) ).readElement();
		if ( subject == null ) throw new Alert();
		StringBuilder b = new StringBuilder();
		for ( MinXML m : new IdWalker().postOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "cdbfgea", b.toString() );
	}

}
