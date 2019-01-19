package com.steelypip.powerups.fusion.io;

import com.steelypip.powerups.fusion.AbsFusionBuilder;
import com.steelypip.powerups.fusion.FusionFactory;

public class JSONFusionBuilder extends AbsFusionBuilder {

	private FusionFactory _factory;
	private boolean is_making_mutable;
	
	public JSONFusionBuilder( final boolean mutable ) {
		this.is_making_mutable = mutable;
	}

	public JSONFusionBuilder() {
		this( true );
	}
	
	@Override
	public boolean isMakingMutable() {
		return this.is_making_mutable;
	}

	@Override
	public FusionFactory factory() {
		if ( this._factory == null ) {
			this._factory = new JSONFusionFactory( this.is_making_mutable );
		}
		return this._factory;
	}
	
}