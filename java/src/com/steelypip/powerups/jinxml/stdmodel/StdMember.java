package com.steelypip.powerups.jinxml.stdmodel;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Member;

public class StdMember implements Member {
	
	String selector;
	Element child;
	
	public StdMember( String selector, Element child ) {
		super();
		this.selector = selector;
		this.child = child;
	}

	@Override
	public String getSelector() {
		return selector;
	}

	@Override
	public Element getChild() {
		return child;
	}	

}
