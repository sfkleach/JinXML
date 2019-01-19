package com.steelypip.powerups.fusion.jsonimpl;

import org.eclipse.jdt.annotation.NonNull;

public class NullFusion extends AbsConstantFusion {

	public NullFusion() {
	}

	@Override
	protected @NonNull String internedType() {
		return this.constTypeNull();
	}

	@Override
	protected @NonNull String literalValue() {
		return this.constValueNull();
	}

	@Override
	protected void setValueAttribute( String new_value ) throws UnsupportedOperationException {
		if ( ! this.constValueNull().equals( new_value ) ) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public boolean isNull() {
		return true;
	}
	
}
