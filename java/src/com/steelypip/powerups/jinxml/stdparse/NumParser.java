package com.steelypip.powerups.jinxml.stdparse;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;

class NumParser {
	TagParser fparser;
	
	StringBuilder b = new StringBuilder();
	
	int radix = 10;
	boolean floating_point = false;
	
	public NumParser( TagParser fparser ) {
		super();
		this.fparser = fparser;
	}
	
	@SuppressWarnings("null")
	@NonNull String gatherNumber() {
		while ( this.processChar( fparser.peekChar( '\0' ) ) ) {
		}
		return b.toString();
//		return FusionParser.convertStringToNumber( radix, floating_point, b.toString() );			
	}
	
	boolean isFloatingPoint() {
		return this.floating_point;
	}
	
	boolean processChar( char pch ) {
		switch ( pch ) {
		case '.':
			return processDot( pch );
		case '-':
		case '+':
			return processSign( pch );
		case '0':
			return processZero( pch );
		default:
			return processOthers( pch );
		}
	}

	void acceptNextChar( char x ) {
		this.b.append( this.fparser.nextChar() );
	}
	
	boolean isDigit( char ch ) {
		if ( radix == 10 ) {
			return Character.isDigit( ch );
		} else if ( radix == 16 ) {
			return Character.isDigit( ch ) || ( "ABCDEF".indexOf( ch ) != -1 );
		} else if ( radix == 2 ) {
			return ch == '0' || ch == '1';
		} else {
			throw new Alert( "Invalid digit" ).culprit(  "Radix", this.radix ).culprit( "Digit", ch );
		}
	}

	private boolean processOthers( char pch ) {
		if ( this.isDigit( pch ) ) {
			this.acceptNextChar( pch );
			return true;
		} else {
			return false;
		}
	}

	private boolean processZero( char pch ) {
		if ( b.length() == 0 || b.length() == 1 && ( "+-".indexOf( b.charAt( 0 ) ) != -1 ) ) {
			this.fparser.skipChar();
			radix = fparser.tryReadChar( 'b' ) ? 2 : this.fparser.tryReadChar( 'x' ) ? 16 : 10;
			if ( radix == 10 ) {
				b.append( '0' );
			}
		} else {
			this.acceptNextChar( pch );
		}
		return true;
	}

	private boolean processSign( char pch ) {
		if ( b.length() == 0 ) {
			this.acceptNextChar( pch );
			return true;
		} else {
			return false;
		}
	}

	private boolean processDot( char pch ) {
		if ( floating_point ) {
			return false;
		} else {
			floating_point = true;
			this.acceptNextChar( pch );
			return true;
		}
	}
	
}