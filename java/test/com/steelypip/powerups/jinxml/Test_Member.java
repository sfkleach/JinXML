package com.steelypip.powerups.jinxml;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import com.steelypip.powerups.common.Sequence;

public class Test_Member {

	@Test
	public void with_NullFilter() {
		Element e = Element.fromString( "<Test-Attribute> key1: [], key2: 99 </&>" );
		Sequence<Member> seq = e.members();
		Iterator<Member> it = seq.filter( a -> true ).iterator();
		it.next();
		it.next();
		assertTrue( ! it.hasNext() );
	}
}
