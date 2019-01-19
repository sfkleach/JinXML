package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

public class FlexiFusionFactory implements FusionFactory, LiteralConstants {

	@Override
	public @NonNull Fusion newIntegerFusion( final long n ) {
		final Fusion f = new FlexiFusion( this.nameConstant() );
		f.addValue( this.keyType(), this.constTypeInteger() );
		f.addValue( this.keyValue(), Long.toString( n ) );
		return f;
	}

	@Override
	public @NonNull Fusion newFloatFusion( final double d ) {
		final Fusion f = new FlexiFusion( this.nameConstant() );
		f.addValue( this.keyType(), this.constTypeFloat() );
		f.addValue( this.keyValue(), Double.toString( d ) );
		return f;
	}

	@Override
	public @NonNull Fusion newStringFusion( final @NonNull String s ) {
		final Fusion f = new FlexiFusion( this.nameConstant() );
		f.addValue( this.keyType(), this.constTypeString() );
		f.addValue( this.keyValue(), s );
		return f;
	}

	@Override
	public @NonNull Fusion newBooleanFusion( final boolean b ) {
		final Fusion f = new FlexiFusion( this.nameConstant() );
		f.addValue( this.keyType(), this.constTypeBoolean() );
		f.addValue( this.keyValue(), Boolean.toString( b ) );
		return f;
	}

	@Override
	public @NonNull Fusion newNullFusion() {
		final Fusion f = new FlexiFusion( this.nameConstant() );
		f.addValue( this.keyType(), this.constTypeNull() );
		f.addValue( this.keyValue(), this.constValueNull() );
		return f;
	}

	@Override
	public @NonNull Fusion newMutableArrayFusion() {
		return new FlexiFusion( this.constTypeArray() );
	}

	@Override
	public @NonNull Fusion newMutableObjectFusion() {
		return new FlexiFusion( this.constTypeObject() );
	}

	@Override
	public @NonNull Fusion newMutableElementFusion( @NonNull String name ) {
		return new FlexiFusion( name );
	}

}
