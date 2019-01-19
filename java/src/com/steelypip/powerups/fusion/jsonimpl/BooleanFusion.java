package com.steelypip.powerups.fusion.jsonimpl;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class BooleanFusion extends AbsConstantFusion {
	
	private boolean flag;

	public BooleanFusion( boolean n ) {
		this.flag = n;
	}

//	@Override
//	public @NonNull Fusion shallowCopy() {
//		return new IntegerFusion( this.number );
//	}

	@Override
	protected @NonNull String internedType() {
		return this.constTypeBoolean();
	}

	@SuppressWarnings("null")
	@Override
	protected @NonNull String literalValue() {
		return Boolean.toString( this.flag );
	}

	@Override
	protected void setValueAttribute( String new_value ) throws UnsupportedOperationException {
		try {
			this.flag = Boolean.parseBoolean( new_value );
		} catch ( NumberFormatException _e ) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public @Nullable Boolean booleanValue() {
		return this.flag;
	}

	@Override
	public boolean booleanValue( boolean otherwise ) {
		return this.flag;
	}
	
	

}
