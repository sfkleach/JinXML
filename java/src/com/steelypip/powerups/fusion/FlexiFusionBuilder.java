package com.steelypip.powerups.fusion;

public class FlexiFusionBuilder extends AbsFusionBuilder {
	
	private static FusionFactory DEFAULT_FACTORY = new FlexiFusionFactory();
	private FusionFactory _factory;
	private boolean is_making_mutable;
	
	public FlexiFusionBuilder( final boolean is_making_mutable ) {
		super();
		this.is_making_mutable = is_making_mutable;
	}
	
	@Override
	public boolean isMakingMutable() {
		return this.is_making_mutable;
	}

	public FlexiFusionBuilder() {
		this( true );
	}

	@Override
	public FusionFactory factory() {
		if ( this._factory == null ) {
			this._factory = DEFAULT_FACTORY;
		}
		return this._factory;
	}

	
}
