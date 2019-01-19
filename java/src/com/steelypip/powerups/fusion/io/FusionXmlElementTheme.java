package com.steelypip.powerups.fusion.io;

import java.util.List;
import java.util.Set;

import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.hydraxml.AbsXmlElementTheme;

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
public class FusionXmlElementTheme extends AbsXmlElementTheme< Fusion > {
	
	final static FusionXmlElementTheme INSTANCE = new FusionXmlElementTheme();

	@Override
	public String getName( Fusion x ) {
		return x.getName();
	}

	@Override
	public boolean hasAnyAttributes( Fusion x ) {
		return x.hasAnyAttributes();
	}

	@Override
	public boolean hasAnyLinks( Fusion x ) {
		return x.hasAnyLinks();
	}

	@Override
	public Set< String > keysToSet( Fusion x ) {
		return x.keysToSet();
	}

	@Override
	public Set< String > fieldsToSet( Fusion x ) {
		return x.fieldsToSet();
	}

	@Override
	public List< String > valuesToList( Fusion x, String key ) {
		return x.valuesToList( key );
	}

	@Override
	public List< Fusion > childrenToList( Fusion x, String field ) {
		return x.childrenToList( field );
	}

}
