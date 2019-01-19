package com.steelypip.powerups.fusion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.fusion.FlexiFusionBuilder;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.FusionBuilder;

public class TestFusionBuilder {
	
	FusionBuilder bldr;

	@Before
	public void setUp() throws Exception {
		this.bldr = new FlexiFusionBuilder();
	}	

	@Test
	public void testBasic() {
		this.bldr.startTag( "alpha" );
		this.bldr.endTag();
		Fusion x = this.bldr.build();
		assertNotNull( x );
		assertEquals( "alpha", x.getName() );
		assertTrue( x.hasNoAttributes() );
		assertTrue( x.hasNoLinks() );
	}

}
