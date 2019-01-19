package com.steelypip.powerups.fusion.jsonimpl;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class FloatFusion extends AbsConstantFusion {
	
	private double number;

	public FloatFusion( double n ) {
		this.number = n;
	}

//	@Override
//	public @NonNull Fusion shallowCopy() {
//		return new FloatFusion( this.number );
//	}

	@Override
	protected @NonNull String internedType() {
		return this.constTypeFloat();
	}

	@SuppressWarnings("null")
	@Override
	protected @NonNull String literalValue() {
		return Double.toString( this.number );
	}

	@Override
	protected void setValueAttribute( String new_value ) throws UnsupportedOperationException {
		try {
			this.number = Double.parseDouble( new_value );
		} catch ( NumberFormatException _e ) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public @Nullable Double floatValue() {
		return this.number;
	}

	@Override
	public double floatValue( double otherwise ) {
		return this.number;
	}

}