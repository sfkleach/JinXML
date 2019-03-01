package com.steelypip.powerups.jinxml.stdmodel;

import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Attribute;

public class StdAttribute implements Attribute {
	
	@NonNull String key;
	@NonNull String value;

	public StdAttribute( @NonNull String key, @NonNull String value ) {
		super();
		this.key = key;
		this.value = value;
	}

	public StdAttribute( Map.Entry< String, String > e ) {
		super();
		String k = e.getKey();
		String v = e.getValue();
		this.key = Objects.requireNonNull( k );
		this.value = Objects.requireNonNull( v );
	}

	@Override
	public @NonNull String getKey() {
		return this.key;
	}

	@Override
	public @NonNull String getValue() {
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
