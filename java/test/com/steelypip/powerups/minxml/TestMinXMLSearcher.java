package com.steelypip.powerups.minxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.minxml.TestMinXMLWalker.IdWalker;

public class TestMinXMLSearcher {

	private static final String source1 = "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>";

	@Before
	public void setUp() throws Exception {
	}
	
	static class CountAttributes extends MinXMLSearcher {
		
		private int count = 0;

		public int getCount() {
			return count;
		}

		@Override
		public boolean startSearch( MinXML subject ) {
			this.count += subject.sizeAttributes();
			return false;
		}

		@Override
		public boolean endSearch( MinXML subject, boolean found ) {
			return false;
		}
		
	}

	@Test
	public void testSource1() {
		final CountAttributes c = new CountAttributes();
		c.search( new MinXMLParser( new StringReader( source1 ) ).readElement() );
		assertEquals( 2, c.getCount() );
	}
	
	static class EarlyExit extends MinXMLSearcher {
		int count_visited = 0;
		
		public int getCount() {
			return count_visited;
		}

		@Override
		public boolean startSearch( MinXML subject ) {
			this.count_visited += 1;
			return subject.hasAttribute( "early.exit" );
		}

		@Override
		public boolean endSearch( MinXML subject, boolean found ) {
			return found;
		}
		
	}
	
	private static final String source2 = (
		"<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo><bar early.exit=''/><gort/></outer>"
	);
	
	@Test
	public void testSource2() {
		final EarlyExit e = new EarlyExit();
		e.search( new MinXMLParser( new StringReader( source2 ) ).readElement() );
		assertEquals( 3, e.getCount() );
	}
	
	static class IdSearcher extends MinXMLSearcher {

		@Override
		public boolean startSearch( MinXML subject ) {
			return false;
		}

		@Override
		public boolean endSearch( MinXML subject, boolean cutoff ) {
			return false;
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
	
	
	static class StopOnBSearcher extends MinXMLSearcher {

		@Override
		public boolean startSearch( MinXML subject ) {
			return "b".equals( subject.getName() );
		}

		@Override
		public boolean endSearch( MinXML subject, boolean cutoff ) {
			return false;
		}
		
	}

	@Test
	public void PreOrderStopOnB() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		MinXML subject = new MinXMLParser( new StringReader( tree ) ).readElement();
		StringBuilder b = new StringBuilder();
		for ( MinXML m : new StopOnBSearcher().preOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "abefg", b.toString() );
	}
	
	@Test
	public void postOrderStopOnB() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		MinXML subject = new MinXMLParser( new StringReader( tree ) ).readElement();
		StringBuilder b = new StringBuilder();
		for ( MinXML m : new StopOnBSearcher().postOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "bfgea", b.toString() );
	}
	
	
	
	static class SkipGSearcher extends MinXMLSearcher {

		@Override
		public boolean startSearch( MinXML subject ) {
			return false;
		}

		@Override
		public boolean endSearch( MinXML subject, boolean cutoff ) {
			return "f".equals( subject.getName() );
		}
		
	}

	@Test
	public void preOrderSkipGSearcher() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		MinXML subject = new MinXMLParser( new StringReader( tree ) ).readElement();
		StringBuilder b = new StringBuilder();
		for ( MinXML m : new SkipGSearcher().preOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "abcdef", b.toString() );
	}
	
	@Test
	public void postOrderSkipGSearcher() {
		String tree = "<a><b><c/><d/></b><e><f/><g/></e></a>";
		MinXML subject = new MinXMLParser( new StringReader( tree ) ).readElement();
		StringBuilder b = new StringBuilder();
		for ( MinXML m : new SkipGSearcher().postOrder( subject ) ) {
			b.append( m.getName() );
		}
		assertEquals( "cdbfea", b.toString() );
	}
	
	static class FindB extends MinXMLSearcher {
		
        StringBuilder visited = new StringBuilder();

		public String getVisited() {
			return visited.toString();
		}
		
		@Override
		public boolean startSearch( MinXML subject ) {
			visited.append( subject.getName() );
			return "B".equals( subject.getName() );
		}
		
		@Override
		public boolean endSearch( MinXML subject, boolean found ) {
			return found;
		}		
		
	}

	@Test
	public void search() {
		String tree = "<A><B><C/><D/></B><E><F/><G/></E></A>";
		MinXML subject = new MinXMLParser( new StringReader( tree ) ).readElement();
		FindB f = new FindB();
		@Nullable MinXML b = f.search( subject );
		assertNotNull( b );
		assertEquals( "B", b.getName() );
		assertEquals( "AB", f.getVisited() );
	}
	
	
}
