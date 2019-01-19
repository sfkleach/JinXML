package com.steelypip.powerups.fusion.jsonimpl;

import org.eclipse.jdt.annotation.NonNull;

public class IntegerFusion extends AbsConstantFusion {
	
	private long number;

	public IntegerFusion( long n ) {
		this.number = n;
	}

	@Override
	protected @NonNull String internedType() {
		return this.constTypeInteger();
	}

	@SuppressWarnings("null")
	@Override
	protected @NonNull String literalValue() {
		return Long.toString( this.number );
	}

	@Override
	protected void setValueAttribute( String new_value ) throws UnsupportedOperationException {
		try {
			this.number = Long.parseLong( new_value );
		} catch ( NumberFormatException _e ) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public long integerValue( long otherwise ) {
		return this.number;
	}
	
	@Override
	public Long integerValue() {
		return this.number;
	}
	
	

}
