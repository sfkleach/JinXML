package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

public interface FusionFactory {
	
	public @NonNull Fusion newIntegerFusion( long n );
	public @NonNull Fusion newFloatFusion( double d );
	public @NonNull Fusion newStringFusion( @NonNull String s );
	public @NonNull Fusion newBooleanFusion( boolean b );
	public @NonNull Fusion newNullFusion();

	public @NonNull Fusion newMutableArrayFusion();
	public @NonNull Fusion newMutableObjectFusion();
	
	public @NonNull Fusion newMutableElementFusion( final @NonNull String name );
	
	default @NonNull Fusion release( final @NonNull Fusion fusion ) {
		return fusion;
	}
	
}
