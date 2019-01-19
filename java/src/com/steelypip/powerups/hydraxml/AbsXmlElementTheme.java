package com.steelypip.powerups.hydraxml;

/**
 * 		<		doStartElement( String name, boolean hasAttributes, boolean hasLinks )
 *		NAME	doName( String name )
 *				doStartAttributes( boolean hasAttributes, boolean hasLinks )
 *				doStartAttributeGroup( String key )
 *				doStartAttribute( String key, String Value )
 *		KEY		doKey( String key )
 *		=|+=	doAttributeBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doValue( String value )
 *		...		// repeat Key/Binding/Value
 *				doEndAttribute( String key, String value )
 *				doEndAttributeGroup( String key )
 *		...		// repeat
 *		>		doEndAttributes( boolean hasAttributes, boolean hasLinks )
 *				doStartLinks( boolean hasAttributes, boolean hasLinks )
 *				doStartLinkGroup( String field )
 *		FIELD	doField( String field )
 *		:		doLinkBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doChild( HydraXML child )
 *		...		//	 repeat previous 3 steps
 *				doEndLinkGroup( String field )
 *		...		// repeat
 *				doEndLinks( boolean hasAttributes, boolean hasLinks )
 *		</NAME>	doEndElement( String name, boolean hasAttributes, boolean hasLinks )
 *
 */
public abstract class AbsXmlElementTheme< U > extends AbsElementTheme< U > {
		
			
	@Override
	public void doStartElement( ThemeableWriter< U > fwriter, String name, boolean hasAttributes, boolean hasLinks ) {
		fwriter.print( '<' );
	}

	@Override
	public void doName( ThemeableWriter< U > fwriter, String name ) {
		fwriter.print( name );
	}

	@Override
	public void doStartAttributes( ThemeableWriter< U > fwriter, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doStartAttributeGroup( ThemeableWriter< U > fwriter, String key ) {
	}

	@Override
	public void doAttribute( ThemeableWriter< U > fwriter, String key, String value, boolean first_in_group, boolean last_in_group ) {
		fwriter.print( ' ' );
		fwriter.print( key );
		fwriter.print( first_in_group ? "=" : "+=" );
		fwriter.print( '"' );
		fwriter.renderString( value );
		fwriter.print( '"' );
	}

	@Override
	public void doEndAttributeGroup( ThemeableWriter< U > fwriter, String key ) {
	}

	@Override
	public void doEndAttributes( ThemeableWriter< U > fwriter, boolean hasAttributes, boolean hasLinks ) {
		if ( hasLinks ) {
			fwriter.print( '>' );
		} else {
			fwriter.print( "/>" );
		}
	}

	@Override
	public void doStartLinks( ThemeableWriter< U > fwriter, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doStartLinkGroup( ThemeableWriter< U > starw, String field ) {
	}

	@Override
	public void doLink( ThemeableWriter< U > fwriter, String field, U child, boolean first_in_group, boolean last_in_group ) {
		if ( ! field.isEmpty() ) {
			fwriter.print( field );
			fwriter.print( first_in_group ? ":" : "+:" );
		}
		fwriter.print( child );
	}


	@Override
	public void doEndLinkGroup( ThemeableWriter< U > fwriter, String field ) {
	}

	@Override
	public void doEndLinks( ThemeableWriter< U > fwriter, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doEndElement( ThemeableWriter< U > fwriter, String name, boolean hasAttributes, boolean hasLinks ) {
		if ( hasLinks ) {
			fwriter.print( "</" );
			fwriter.print( name );
			fwriter.print( ">" );
		}
	}

}
