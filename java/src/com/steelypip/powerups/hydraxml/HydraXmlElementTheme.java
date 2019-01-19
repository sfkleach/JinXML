package com.steelypip.powerups.hydraxml;

import java.util.List;
import java.util.Set;

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
public class HydraXmlElementTheme extends AbsXmlElementTheme< HydraXML > {
	
	final static HydraXmlElementTheme INSTANCE = new HydraXmlElementTheme();

	@Override
	public String getName( HydraXML x ) {
		return x.getName();
	}

	@Override
	public boolean hasAnyAttributes( HydraXML x ) {
		return x.hasAnyAttributes();
	}

	@Override
	public boolean hasAnyLinks( HydraXML x ) {
		return x.hasAnyLinks();
	}

	@Override
	public Set< String > keysToSet( HydraXML x ) {
		return x.keysToSet();
	}

	@Override
	public Set< String > fieldsToSet( HydraXML x ) {
		return x.fieldsToSet();
	}

	@Override
	public List< String > valuesToList( HydraXML x, String key ) {
		return x.valuesToList( key );
	}

	@Override
	public List< HydraXML > childrenToList( HydraXML x, String field ) {
		return x.childrenToList( field );
	}

}
