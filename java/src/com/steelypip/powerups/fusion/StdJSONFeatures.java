package com.steelypip.powerups.fusion;

import com.steelypip.powerups.hydranode.Named;

public interface StdJSONFeatures extends JSONFeatures, LiteralConstants, Named {
	
	boolean hasAttribute( final String type );
	boolean hasAttribute( final String type, final String value );
	String getValue( final String type );
	
	default boolean isConstant( final String type ) {
		return this.hasName( this.nameConstant() ) && this.hasAttribute( this.keyType(), type ) && this.hasAttribute( this.keyValue() );
	}
	
	default String getConstantValueAsString() {
		return this.getValue( this.keyValue() );
	}

	@Override
	default Long integerValue() {
		try {
			return this.isConstant( this.constTypeInteger() ) ? Long.parseLong( this.getConstantValueAsString() ) : null;
		} catch ( NumberFormatException _e ) {
			return null;
		}
	}

	@Override
	default Double floatValue() {
		try {
			return this.isConstant( this.constTypeFloat() ) ? Double.parseDouble( this.getConstantValueAsString() ) : null;
		} catch ( NumberFormatException _e ) {
			return null;
		}
	}

	@Override
	default String stringValue() {
		return this.isConstant( this.constTypeString() ) ? this.getConstantValueAsString() : null;
	}

	@Override
	default Boolean booleanValue() {
		return this.isConstant( this.constTypeBoolean() ) ? Boolean.parseBoolean( this.getConstantValueAsString() ) : null;
	}

	@Override
	default boolean isNull() {
		return this.isConstant( this.constTypeNull() );
	}

	@Override
	default boolean isArray() {
		return this.hasName( this.constTypeArray() );
	}

	@Override
	default boolean isObject() {
		return this.hasName( this.constTypeObject() );
	}

	@Override
	default boolean isJSONItem() {
		return (
			( 	this.hasName( this.nameConstant() ) &&
				this.hasAttribute( this.keyType() ) &&
				this.hasAttribute( this.keyValue() )
			) || 
			this.hasName( this.constTypeArray() ) || 
			this.hasName( this.constTypeObject() )
		);
	}
	
}
