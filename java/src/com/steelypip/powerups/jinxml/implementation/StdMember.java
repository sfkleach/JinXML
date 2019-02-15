package com.steelypip.powerups.jinxml.implementation;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Member;

public class StdMember implements Member {
	
	String selector;
	int position;
	Element child;
	
	public StdMember( String selector, int position, Element child ) {
		super();
		this.selector = selector;
		this.position = position;
		this.child = child;
	}

	@Override
	public String getSelector() {
		return selector;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public Element getChild() {
		return child;
	}	

}
