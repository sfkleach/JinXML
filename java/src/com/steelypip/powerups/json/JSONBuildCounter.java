package com.steelypip.powerups.json;

public class JSONBuildCounter {
	
	private int level = 0;
	private int count_back_to_zero = 0;
	
	public final void increment() {
		this.level += 1;
		if ( this.count_back_to_zero > 0 ) {
			throw new JSONBuildFailedException( "More than one JSON expression encountered" );
		}
	}
	
	public final void decrement() {
		this.level -= 1;
		if ( this.level == 0 ) {
			this.count_back_to_zero += 1;
		}
	}


	public final void buildCheck() {
		if ( this.level == 0 ) {
			if ( this.count_back_to_zero == 0 ) {
				throw new JSONBuildFailedException( "No expression read" );
			} else if ( this.count_back_to_zero != 1 ) {
				throw new JSONBuildFailedException( "More than one JSON expression encountered" );
			}
		} else {
			throw new JSONBuildFailedException( "Calling build before the end of a JSON expression" );
		}
	}

}
