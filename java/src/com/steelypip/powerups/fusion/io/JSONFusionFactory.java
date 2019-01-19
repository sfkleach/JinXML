package com.steelypip.powerups.fusion.io;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.fusion.FusionFactory;
import com.steelypip.powerups.fusion.LiteralConstants;
import com.steelypip.powerups.fusion.evolvingproxy.EvolvingProxyFusion;
import com.steelypip.powerups.fusion.jsonimpl.ArrayFusion;
import com.steelypip.powerups.fusion.jsonimpl.BooleanFusion;
import com.steelypip.powerups.fusion.jsonimpl.FloatFusion;
import com.steelypip.powerups.fusion.jsonimpl.IntegerFusion;
import com.steelypip.powerups.fusion.jsonimpl.NullFusion;
import com.steelypip.powerups.fusion.jsonimpl.ObjectFusion;
import com.steelypip.powerups.fusion.jsonimpl.StringFusion;
import com.steelypip.powerups.fusion.FlexiFusion;
import com.steelypip.powerups.fusion.Fusion;

public class JSONFusionFactory implements FusionFactory, LiteralConstants {
	
	private boolean mutable = false;
	
	public JSONFusionFactory() {	
	}
	
	public JSONFusionFactory( final boolean _mutable ){
		this.mutable = _mutable;
	}
	
	private @NonNull Fusion makeMutable( @NonNull Fusion x ) {
		return this.mutable ? new EvolvingProxyFusion( x ) : x;
	}

	@Override
	public @NonNull Fusion newIntegerFusion( final long n ) {
		return makeMutable( new IntegerFusion( n ) );
	}

	@Override
	public @NonNull Fusion newFloatFusion( final double d ) {
		return makeMutable( new FloatFusion( d ) );
	}

	@Override
	public @NonNull Fusion newStringFusion( final @NonNull String s ) {
		return makeMutable( new StringFusion( s ) );
	}

	@Override
	public @NonNull Fusion newBooleanFusion( final boolean b ) {
		return makeMutable( new BooleanFusion( b ) );
	}

	@Override
	public @NonNull Fusion newNullFusion() {
		return makeMutable( new NullFusion() );
	}

	@Override
	public @NonNull Fusion newMutableArrayFusion() {
		return makeMutable( new ArrayFusion() );
	}

	@Override
	public @NonNull Fusion newMutableObjectFusion() {
		return makeMutable( new ObjectFusion() );
	}

	@Override
	public @NonNull Fusion newMutableElementFusion( @NonNull String name ) {
		return new FlexiFusion( name );
	}

}
