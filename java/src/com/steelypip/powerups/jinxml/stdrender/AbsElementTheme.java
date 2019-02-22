package com.steelypip.powerups.jinxml.stdrender;

import java.util.List;
import java.util.Set;

import com.steelypip.powerups.jinxml.Element;

/**
 * Elements have three
 * parts: name, attributes and links. The general pattern they print 
 * to is:
 * 
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
 *		FIELD	doField( String selector )
 *		:		doMemberBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doChild( HydraXML child )
 *		...		//	 repeat previous 3 steps
 *				doEndMemberGroup( String selector )
 *		...		// repeat
 *				doEndMember( boolean hasAttributes, boolean hasMembers )
 *		</NAME>	doEndElement( String name, boolean hasAttributes, boolean hasMembers )
 */

public abstract class AbsElementTheme implements Theme< Element > {
	
	public abstract String getName( Element x );
	public abstract boolean hasAnyAttributes( Element x );
	public abstract boolean hasAnyMembers( Element x );
	public abstract Set< String > keysToSet( Element x );
	public abstract Set< String > selectorsToSet( Element x );
	public abstract List< String > valuesToList( Element x, String key );
	public abstract List< Element > childrenToList( Element x, String selector );
	
	@Override
	public boolean tryRender( ThemeableWriter< Element > fwriter, Element x ) {
		
		final String name = this.getName( x );
		final boolean has_any_attributes = this.hasAnyAttributes( x );
		final boolean has_any_members = this.hasAnyMembers( x );
		
		fwriter.getIndenter().indent();
		this.doStartElement( fwriter, name, has_any_attributes, has_any_members );
		this.doName( fwriter, name );
		
		this.doStartAttributes( fwriter, has_any_attributes, has_any_members );
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
		this.doEndAttributes( fwriter, has_any_attributes, has_any_members );
		
		if ( has_any_members ) {
			fwriter.getIndenter().newline();
			fwriter.getIndenter().tab();
			this.doStartMembers( fwriter, has_any_attributes, has_any_members );
			for ( String selector : this.selectorsToSet( x ) ) {
				final List< Element > children = this.childrenToList( x, selector );
				this.doStartMemberGroup( fwriter, selector );
				int n = 0;
				for ( Element child : children) {
					n += 1;
					this.doMember( fwriter, selector, child, n == 1, n == children.size() );
				}
				this.doEndMemberGroup( fwriter, selector );
			}
			this.doEndMembers( fwriter, has_any_attributes, has_any_members );
			fwriter.getIndenter().untab();
		}

		this.doEndElement( fwriter, name, has_any_attributes, has_any_members );
		fwriter.getIndenter().newline();
		
		return true;
	}
		
	public abstract void doStartElement( ThemeableWriter< Element > starw, String name, boolean hasAttributes, boolean hasMembers );
	public abstract void doName( ThemeableWriter< Element > starw, String name );
	public abstract void doStartAttributes( ThemeableWriter< Element > starw, boolean hasAttributes, boolean hasMembers );
	public abstract void doStartAttributeGroup( ThemeableWriter< Element > starw, String key );
	public abstract void doAttribute( ThemeableWriter< Element > starw, String key, String value, boolean first_in_group, boolean last_in_group );
	public abstract void doEndAttributeGroup( ThemeableWriter< Element > starw, String key );
	public abstract void doEndAttributes( ThemeableWriter< Element > starw, boolean hasAttributes, boolean hasMembers );
	public abstract void doStartMembers( ThemeableWriter< Element > starw, boolean hasAttributes, boolean hasMembers );
	public abstract void doStartMemberGroup( ThemeableWriter< Element > starw, String selector );
	public abstract void doMember( ThemeableWriter< Element > starw, String selector, Element child, boolean first_in_group, boolean last_in_group );
	public abstract void doEndMemberGroup( ThemeableWriter< Element > starw, String selector );
	public abstract void doEndMembers( ThemeableWriter< Element > starw, boolean hasAttributes, boolean hasMembers );
	public abstract void doEndElement( ThemeableWriter< Element > starw, String name, boolean hasAttributes, boolean hasMembers );

	public abstract static class Selector {
		
		public abstract static class Factory< U > {
			public abstract Selector newInstance( ThemeableWriter< Element > w );
		}
		
		public abstract AbsElementTheme select( Element element );
		
		public Selector compose( final Selector alternative ) {
			return(
				new Selector() {
				
					final Selector first_choice = this;

					@Override
					public AbsElementTheme select( Element element ) {
						AbsElementTheme t = this.first_choice.select( element );
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
