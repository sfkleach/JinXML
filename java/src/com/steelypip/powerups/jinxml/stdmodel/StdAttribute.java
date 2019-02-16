package com.steelypip.powerups.jinxml.stdmodel;

import com.steelypip.powerups.jinxml.Attribute;

public class StdAttribute implements Attribute {
	
	String key;
	String value;

	public StdAttribute( String key, int position, String value ) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
