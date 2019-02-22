package com.steelypip.powerups.jinxml.stdrender;

public interface Theme< T > {
	
	boolean tryRender( ThemeableWriter< T > w, T x );
	
	default Theme< T > compose( Theme< T > alternative ) {
		return(
			new Theme< T >() {
				@Override
				public boolean tryRender( ThemeableWriter< T > w, T x ) {
					return Theme.this.tryRender( w, x ) || alternative.tryRender( w, x );
				}
			}
		);
	}

}
