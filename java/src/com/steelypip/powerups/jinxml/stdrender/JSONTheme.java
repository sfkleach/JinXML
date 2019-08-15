package com.steelypip.powerups.jinxml.stdrender;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Member;

public class JSONTheme implements Theme< Element > {

	@Override
	public boolean tryRender( ThemeableWriter< Element > fwriter, Element x ) {
		if ( x.isJSONItem() ) {
			if ( x.isIntValue() ) {
				fwriter.print( x.getIntValue( 0L ) );
				return true;				
			} else if ( x.isFloatValue() ) {
				fwriter.print( x.getFloatValue( 0.0 ) );
				return true;				
			} else if ( x.isStringValue() ) {
				fwriter.renderAsJSONString( x.getStringValue() );
				return true;				
			} else if ( x.isBooleanValue() ) {
				fwriter.print( x.getBooleanValue( false ) );
				return true;				
			} else if ( x.isNullValue() ) {
				fwriter.print( "null" );
				return true;				
			} else if ( x.isArray() ) {
				fwriter.print( '[' );
				boolean is_first = true;
				for ( Member m : x.members().filter( Member::hasDefaultSelector ) ) {
					if ( ! is_first ) {
						fwriter.print( "," );
					}
					Element c = m.getChild();
					fwriter.print( c );
					is_first = false;
				}
				fwriter.print( ']' );
				return true;				
			} else if ( x.isObject() ) {
				fwriter.print( '{' );
				boolean is_first = true;
				for ( Member m : x.firstMembers() ) {
					if ( ! is_first ) {
						fwriter.print( "," );
					}
					fwriter.renderSelector( m.getSelector() );
					fwriter.print( ":" );
					Element c = m.getChild();
					fwriter.print( c );
					is_first = false;
				}
				fwriter.print( '}' );
				return true;				
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
