package com.steelypip.powerups.jinxml.stdmodel;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Attribute;

public class StdAttribute implements Attribute {
	
	@NonNull String key;
	@NonNull String value;

	public StdAttribute( @NonNull String key, int position, @NonNull String value ) {
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
	
	public int hashCode() {
		return this.getKey().hashCode() * 89 + this.getValue().hashCode();
	}
	
	public boolean equals( final Object that_object ) {
		if ( that_object == null || that_object.getClass() != StdAttribute.class ) return false;
		final StdAttribute that = (StdAttribute)that_object;
		return this.key.equals( that.key ) && this.value.equals( that.value );
	}

}
