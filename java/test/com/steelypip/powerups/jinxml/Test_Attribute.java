package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import com.steelypip.powerups.common.Sequence;

public class Test_Attribute {

	@Test
	public void with_NullFilter() {
		Element e = Element.fromString( "<Test-Attribute key1='value1' key2='value2'/>" );
		Sequence<Attribute> seq = e.attributes();
		Iterator<Attribute> it = seq.filter( a -> true ).iterator();
		it.next();
		it.next();
		assertTrue( ! it.hasNext() );
	}
}
