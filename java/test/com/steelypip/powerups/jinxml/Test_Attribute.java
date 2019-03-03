package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class Test_Attribute {

	@Test
	public void with_NullFilter() {
		Element e = Element.fromString( "<Test-Attribute key1='value1' key2='value2'/>" );
		Attribute.Iterable seq = e.attributes();
		Iterator<Attribute> it = seq.with( a -> true ).iterator();
		it.next();
		it.next();
		assertTrue( ! it.hasNext() );
	}
}
