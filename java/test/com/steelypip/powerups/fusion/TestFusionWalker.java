package com.steelypip.powerups.fusion;

import static org.junit.Assert.*;
import java.io.StringReader;
import org.junit.Test;
import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.fusion.io.FusionParser;

public class TestFusionWalker {


	private static final String source1 = "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>";
	
	static class CountElements extends FusionWalker {
		
		int count = 0;
		
		public int getCount() {
			return count;
		}

		@Override
		public void startWalk( String field, Fusion subject ) {
			this.count += 1;
		}

		@Override
		public void endWalk( String field, Fusion subject ) {
		}		
		
	}
	
	@Test
	public void testCountElements() {
		final CountElements c = new CountElements();
		c.walk( new FusionParser( new StringReader( source1 ) ).readElement() );
		assertEquals( 2, c.getCount() );
	}
	
	static class CountAttributes extends FusionWalker {
		
		private int start_count = 0;
		private int end_count = 0;

		public int getStartCount() {
			return start_count;
		}

		public int getEndCount() {
			return end_count;
		}

		@Override
		public void startWalk( String field, Fusion subject ) {
			this.start_count += 1;
		}

		@Override
		public void endWalk( String field, Fusion subject ) {
			this.end_count += 1;
		}
		
	}

	@Test
	public void testCountStartsAndEnds() {
		final CountAttributes c = new CountAttributes();
		c.walk( new FusionParser( new StringReader( source1 ) ).readElement() );
		assertEquals( 2, c.getStartCount() );
		assertEquals( 2, c.getEndCount() );
	}
	
	static class IdWalker extends FusionWalker {

		@Override
		public void startWalk( String field, Fusion subject ) {
		}

		@Override
		public void endWalk( String field, Fusion subject ) {
		}
		
	}
	
	@Test
	public void testPreOrder() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		Fusion subject = new FusionParser( new StringReader( tree ) ).readElement();
		if ( subject == null ) throw new Alert();
		StringBuilder b = new StringBuilder();
		for ( Fusion m : new IdWalker().preOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "abcdefg", b.toString() );
	}

	@Test
	public void testPostOrder() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		Fusion subject = new FusionParser( new StringReader( tree ) ).readElement();
		if ( subject == null ) throw new Alert();
		StringBuilder b = new StringBuilder();
		for ( Fusion m : new IdWalker().postOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "cdbfgea", b.toString() );
	}

}
