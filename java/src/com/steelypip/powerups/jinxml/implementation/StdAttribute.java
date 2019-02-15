package com.steelypip.powerups.jinxml.implementation;

import com.steelypip.powerups.jinxml.Attribute;

public class StdAttribute implements Attribute {
	
	String key;
	int position;
	String value;

	public StdAttribute( String key, int position, String value ) {
		super();
		this.key = key;
		this.position = position;
		this.value = value;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public int getPosition() {
		return this.position;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
