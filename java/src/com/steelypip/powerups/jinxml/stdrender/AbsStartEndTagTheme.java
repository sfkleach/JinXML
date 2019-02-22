package com.steelypip.powerups.jinxml.stdrender;

import com.steelypip.powerups.jinxml.Element;

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
public abstract class AbsStartEndTagTheme extends AbsElementTheme {
		
			
	@Override
	public void doStartElement( ThemeableWriter<Element > fwriter, String name, boolean hasAttributes, boolean hasMembers ) {
		fwriter.print( '<' );
	}

	@Override
	public void doName( ThemeableWriter< Element > fwriter, String name ) {
		fwriter.renderElementName( name );
	}

	@Override
	public void doStartAttributes( ThemeableWriter< Element > fwriter, boolean hasAttributes, boolean hasMembers ) {
	}

	@Override
	public void doStartAttributeGroup( ThemeableWriter< Element > fwriter, String key ) {
	}

	@Override
	public void doAttribute( ThemeableWriter< Element > fwriter, String key, String value, boolean first_in_group, boolean last_in_group ) {
		fwriter.print( ' ' );
		fwriter.print( key );
		fwriter.print( first_in_group ? "=" : "+=" );
		fwriter.renderAsXMLString( value );
	}

	@Override
	public void doEndAttributeGroup( ThemeableWriter< Element > fwriter, String key ) {
	}

	@Override
	public void doEndAttributes( ThemeableWriter< Element > fwriter, boolean hasAttributes, boolean hasMembers ) {
		if ( hasMembers ) {
			fwriter.print( '>' );
		} else {
			fwriter.print( "/>" );
		}
	}

	@Override
	public void doStartMembers( ThemeableWriter< Element > fwriter, boolean hasAttributes, boolean hasMembers ) {
	}

	@Override
	public void doStartMemberGroup( ThemeableWriter< Element > starw, String selector ) {
	}

	@Override
	public void doMember( ThemeableWriter< Element > fwriter, String selector, Element child, boolean first_in_group, boolean last_in_group ) {
		if ( ! selector.isEmpty() ) {
			fwriter.renderSelector( selector );
			fwriter.print( first_in_group ? ":" : "+:" );
		}
		fwriter.print( child );
	}

	@Override
	public void doEndMemberGroup( ThemeableWriter< Element > fwriter, String selector ) {
	}

	@Override
	public void doEndMembers( ThemeableWriter< Element > fwriter, boolean hasAttributes, boolean hasMembers ) {
	}

	@Override
	public void doEndElement( ThemeableWriter< Element > fwriter, String name, boolean hasAttributes, boolean hasMembers ) {
		if ( hasMembers ) {
			fwriter.print( "</" );
			fwriter.renderElementName( name );
			fwriter.print( ">" );
		}
	}

}
