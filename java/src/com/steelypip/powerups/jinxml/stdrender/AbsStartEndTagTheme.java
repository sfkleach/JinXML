package com.steelypip.powerups.jinxml.stdrender;

/**
 * 		<		doStartElement( String name, boolean hasAttributes, boolean hasMembers )
 *		NAME	doName( String name )
 *				doStartAttributes( boolean hasAttributes, boolean hasMembers )
 *				doStartAttributeGroup( String key )
 *				doStartAttribute( String key, String Value )
 *		KEY		doKey( String key )
 *		=|+=	doAttributeBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doValue( String value )
 *		...		// repeat Key/Binding/Value
 *				doEndAttribute( String key, String value )
 *				doEndAttributeGroup( String key )
 *		...		// repeat
 *		>		doEndAttributes( boolean hasAttributes, boolean hasMembers )
 *				doStartMembers( boolean hasAttributes, boolean hasMembers )
 *				doStartMemberGroup( String selector )
 *		FIELD	doSelector( String selector )
 *		:		doMemberBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doChild( HydraXML child )
 *		...		//	 repeat previous 3 steps
 *				doEndMemberGroup( String selector )
 *		...		// repeat
 *				doEndMembers( boolean hasAttributes, boolean hasMembers )
 *		</NAME>	doEndElement( String name, boolean hasAttributes, boolean hasMembers )
 *
 */
public abstract class AbsStartEndTagTheme< U > extends AbsElementTheme< U > {
		
			
	@Override
	public void doStartElement( ThemeableWriter< U > fwriter, String name, boolean hasAttributes, boolean hasMembers ) {
		fwriter.print( '<' );
	}

	@Override
	public void doName( ThemeableWriter< U > fwriter, String name ) {
		fwriter.renderElementName( name );
	}

	@Override
	public void doStartAttributes( ThemeableWriter< U > fwriter, boolean hasAttributes, boolean hasMembers ) {
	}

	@Override
	public void doStartAttributeGroup( ThemeableWriter< U > fwriter, String key ) {
	}

	@Override
	public void doAttribute( ThemeableWriter< U > fwriter, String key, String value, boolean first_in_group, boolean last_in_group ) {
		fwriter.print( ' ' );
		fwriter.print( key );
		fwriter.print( first_in_group ? "=" : "+=" );
		fwriter.renderAsXMLString( value );
	}

	@Override
	public void doEndAttributeGroup( ThemeableWriter< U > fwriter, String key ) {
	}

	@Override
	public void doEndAttributes( ThemeableWriter< U > fwriter, boolean hasAttributes, boolean hasMembers ) {
		if ( hasMembers ) {
			fwriter.print( '>' );
		} else {
			fwriter.print( "/>" );
		}
	}

	@Override
	public void doStartMembers( ThemeableWriter< U > fwriter, boolean hasAttributes, boolean hasMembers ) {
	}

	@Override
	public void doStartMemberGroup( ThemeableWriter< U > starw, String selector ) {
	}

	@Override
	public void doMember( ThemeableWriter< U > fwriter, String selector, U child, boolean first_in_group, boolean last_in_group ) {
		if ( ! selector.isEmpty() ) {
			fwriter.print( selector );
			fwriter.print( first_in_group ? ":" : "+:" );
		}
		fwriter.print( child );
	}

	@Override
	public void doEndMemberGroup( ThemeableWriter< U > fwriter, String selector ) {
	}

	@Override
	public void doEndMembers( ThemeableWriter< U > fwriter, boolean hasAttributes, boolean hasMembers ) {
	}

	@Override
	public void doEndElement( ThemeableWriter< U > fwriter, String name, boolean hasAttributes, boolean hasMembers ) {
		if ( hasMembers ) {
			fwriter.print( "</" );
			fwriter.renderElementName( name );
			fwriter.print( ">" );
		}
	}

}
