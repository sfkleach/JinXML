package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class Test_Member {

	@Test
	public void with_NullFilter() {
		Element e = Element.fromString( "<Test-Attribute> key1: [], key2: 99 </&>" );
		Member.Iterable seq = e.members();
		Iterator<Member> it = seq.with( a -> true ).iterator();
		it.next();
		it.next();
		assertTrue( ! it.hasNext() );
	}
}
