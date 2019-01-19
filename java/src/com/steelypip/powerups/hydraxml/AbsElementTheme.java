package com.steelypip.powerups.hydraxml;

import java.util.List;
import java.util.Set;

/**
 * Elements have three
 * parts: name, attributes and links. The general pattern they print 
 * to is:
 * 
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
 */

public abstract class AbsElementTheme< U > implements Theme< U > {
	
	public abstract String getName( U x );
	public abstract boolean hasAnyAttributes( U x );
	public abstract boolean hasAnyLinks( U x );
	public abstract Set< String > keysToSet( U x );
	public abstract Set< String > fieldsToSet( U x );
	public abstract List< String > valuesToList( U x, String key );
	public abstract List< U > childrenToList( U x, String field );
	
	@Override
	public boolean tryRender( ThemeableWriter< U > fwriter, U x ) {
		
		final String name = this.getName( x );
		final boolean has_any_attributes = this.hasAnyAttributes( x );
		final boolean has_any_links = this.hasAnyLinks( x );
		
		fwriter.getIndenter().indent();
		this.doStartElement( fwriter, name, has_any_attributes, has_any_links );
		this.doName( fwriter, name );
		
		this.doStartAttributes( fwriter, has_any_attributes, has_any_links );
		for ( String key : this.keysToSet( x ) ) {
			this.doStartAttributeGroup( fwriter, key );
			final List< String > values = this.valuesToList( x, key );
			int n = 0;
			for ( String value : values ) {
				n += 1;
				this.doAttribute( fwriter, key, value,  n == 1, n == values.size() );
			}
			this.doEndAttributeGroup( fwriter, key );
		}
		this.doEndAttributes( fwriter, has_any_attributes, has_any_links );
		
		this.doStartLinks( fwriter, has_any_attributes, has_any_links );
		for ( String field : this.fieldsToSet( x ) ) {
			final List< U > children = this.childrenToList( x, field );
			this.doStartLinkGroup( fwriter, field );
			int n = 0;
			for ( U child : children) {
				n += 1;
				this.doLink( fwriter, field, child, n == 1, n == children.size() );
			}
			this.doEndLinkGroup( fwriter, field );
		}
		this.doEndLinks( fwriter, has_any_attributes, has_any_links ); 
		
		this.doEndElement( fwriter, name, has_any_attributes, has_any_links );
		
		return true;
	}
		
	public abstract void doStartElement( ThemeableWriter< U > starw, String name, boolean hasAttributes, boolean hasLinks );
	public abstract void doName( ThemeableWriter< U > starw, String name );
	public abstract void doStartAttributes( ThemeableWriter< U > starw, boolean hasAttributes, boolean hasLinks );
	public abstract void doStartAttributeGroup( ThemeableWriter< U > starw, String key );
	public abstract void doAttribute( ThemeableWriter< U > starw, String key, String value, boolean first_in_group, boolean last_in_group );
	public abstract void doEndAttributeGroup( ThemeableWriter< U > starw, String key );
	public abstract void doEndAttributes( ThemeableWriter< U > starw, boolean hasAttributes, boolean hasLinks );
	public abstract void doStartLinks( ThemeableWriter< U > starw, boolean hasAttributes, boolean hasLinks );
	public abstract void doStartLinkGroup( ThemeableWriter< U > starw, String field );
	public abstract void doLink( ThemeableWriter< U > starw, String field, U child, boolean first_in_group, boolean last_in_group );
	public abstract void doEndLinkGroup( ThemeableWriter< U > starw, String field );
	public abstract void doEndLinks( ThemeableWriter< U > starw, boolean hasAttributes, boolean hasLinks );
	public abstract void doEndElement( ThemeableWriter< U > starw, String name, boolean hasAttributes, boolean hasLinks );

	public abstract static class Selector< U > {
		
		public abstract static class Factory< U > {
			public abstract Selector< U > newInstance( ThemeableWriter< U > w );
		}
		
		public abstract AbsElementTheme< U > select( U element );
		
		public Selector< U > compose( final Selector< U > alternative ) {
			return(
				new Selector< U >() {
				
					final Selector< U > first_choice = this;

					@Override
					public AbsElementTheme< U > select( U element ) {
						AbsElementTheme< U > t = this.first_choice.select( element );
						if ( t == null ) {
							return t;
						} else {
							return alternative.select( element );
						}
					}
					
				}
			);
		}
	}
	
}
