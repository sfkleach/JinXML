package com.steelypip.powerups.jinxml.stdrender;

import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Element;

/*
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
public class StartEndTagTheme extends AbsStartEndTagTheme {
	
	final static StartEndTagTheme INSTANCE = new StartEndTagTheme();

	@Override
	public String getName( Element x ) {
		return x.getName();
	}

	@Override
	public boolean hasAnyAttributes( Element x ) {
		return x.hasAnyAttributes();
	}

	@Override
	public boolean hasAnyMembers( Element x ) {
		return x.hasAnyMembers();
	}

	@Override
	public Set< String > keysToSet( Element x ) {
		return x.keysToSet();
	}

	@Override
	public Set< String > selectorsToSet( Element x ) {
		return x.selectorsToSet();
	}

	@Override
	public List< String > valuesToList( Element x, @NonNull String key ) {
		return x.getValuesAsList( key );
	}

	@Override
	public List< Element > childrenToList( Element x, @NonNull String selector ) {
		return x.getChildrenAsList( selector );
	}

}
