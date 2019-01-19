package com.steelypip.powerups.fusion.evolvingproxy;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.fusion.FlexiFusion;
import com.steelypip.powerups.fusion.Fusion;


public class EvolvingProxyFusion implements Fusion {

	protected Fusion fusion;

	public EvolvingProxyFusion( Fusion fusion ) {
		super();
		this.fusion = fusion;
	}
	
	protected Fusion evolve() {
		this.fusion = new FlexiFusion( this.fusion );
		return this;
	}

	public String defaultField() {
		return fusion.defaultField();
	}

	// JSON Features
	
	public @Nullable Long integerValue() {
		return fusion.integerValue();
	}

	public boolean isInteger() {
		return fusion.isInteger();
	}

	public long integerValue( long otherwise ) {
		return fusion.integerValue( otherwise );
	}

	public @Nullable Double floatValue() {
		return fusion.floatValue();
	}

	public boolean isFloat() {
		return fusion.isFloat();
	}

	public double floatValue( double otherwise ) {
		return fusion.floatValue( otherwise );
	}

	public boolean isBoolean() {
		return fusion.isBoolean();
	}

	public @Nullable String stringValue() {
		return fusion.stringValue();
	}

	public boolean isString() {
		return fusion.isString();
	}

	public String stringValue( String otherwise ) {
		return fusion.stringValue( otherwise );
	}

	public @Nullable Boolean booleanValue() {
		return fusion.booleanValue();
	}

	public boolean booleanValue( boolean otherwise ) {
		return fusion.booleanValue( otherwise );
	}
	
	public boolean isNull() {
		return fusion.isNull();
	}

	public boolean isArray() {
		return fusion.isArray();
	}

	public boolean isObject() {
		return fusion.isObject();
	}

	public boolean isJSONItem() {
		return fusion.isJSONItem();
	}

	//	Named

	public @NonNull String getInternedName() {
		return fusion.getInternedName();
	}


	public @NonNull String getName() {
		return fusion.getName();
	}

	public boolean hasName( @Nullable String name ) {
		return fusion.hasName( name );
	}

	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		try {
			fusion.setName( x );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve();
			this.setName( x );
		}
	}


	//	Attributes & Links
	
	public String getValue( String key ) throws IllegalArgumentException {
		return fusion.getValue( key );
	}

	public Fusion getChild() throws IllegalArgumentException {
		return fusion.getChild();
	}

	public Fusion getChild( int index ) throws IllegalArgumentException {
		return fusion.getChild( index );
	}

	public Fusion getChild( String field ) throws IllegalArgumentException {
		return fusion.getChild( field );
	}

	public Fusion getChild( String field, int index ) throws IllegalArgumentException {
		return fusion.getChild( field, index );
	}

	public Fusion getChild( String field, Fusion otherwise ) {
		return fusion.getChild( field, otherwise );
	}

	public Iterator< Entry< String, Fusion > > iterator() {
		return fusion.iterator();
	}

	public String getValue( String key, int index ) throws IllegalArgumentException {
		return fusion.getValue( key, index );
	}

	public Fusion getChild( String field, int index, Fusion otherwise ) {
		return fusion.getChild( field, index, otherwise );
	}

	public void setChild( String field, Fusion value ) throws UnsupportedOperationException {
		try {
			fusion.setChild( field, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().setChild( field, value );
		}
	}

	public void updateChild( String field, int index, Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		try {
			fusion.updateChild( field, index, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().updateChild( field, index, value );
		}
	}

	public void setAllChildren( String field, Iterable< Fusion > values ) throws UnsupportedOperationException {
		try {
			fusion.setAllChildren( field, values );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().setAllChildren( field, values );
		}
	}

	public void addChild( String field, Fusion value ) throws UnsupportedOperationException {
		try {
			fusion.addChild( field, value );
		} catch ( UnsupportedOperationException _e ) { 
			this.evolve().addChild( field, value );
		}
	}

	public String getValue( String key, String otherwise ) {
		return fusion.getValue( key, otherwise );
	}

	public void addChild( Fusion value ) throws UnsupportedOperationException {
		try {
			fusion.addChild( value );
		} catch ( UnsupportedOperationException _e ) { 
			this.evolve().addChild( value );
		}
	}

	public void removeChild( String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		try {
			fusion.removeChild( key );
		} catch ( UnsupportedOperationException _e ) { 
			this.evolve().removeChild( key );
		}
	}

	public void removeChild( String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		try {
			fusion.removeChild( key, index );
		} catch ( UnsupportedOperationException _e ) { 
			this.evolve().removeChild( key, index );
		}
	}

	public void clearLinks( String key ) throws UnsupportedOperationException {
		try {
			fusion.clearLinks( key );
		} catch ( UnsupportedOperationException e ) {
			this.evolve().clearLinks( key );
		}
	}

	public void clearAllLinks() throws UnsupportedOperationException {
		try {
			fusion.clearAllLinks();
		} catch ( UnsupportedOperationException e ) {
			this.evolve().clearAllLinks();
		}
	}

	public String getValue( String key, int index, String otherwise ) {
		return fusion.getValue( key, index, otherwise );
	}

	public boolean hasNoLinks() {
		return fusion.hasNoLinks();
	}

	public boolean hasAnyLinks() {
		return fusion.hasAnyLinks();
	}

	public void trimToSize() {
		fusion.trimToSize();
	}

	public int sizeLinks() {
		return fusion.sizeLinks();
	}

	public boolean hasSizeLinks( int n ) {
		return fusion.hasSizeLinks( n );
	}

	public boolean hasLink( String field ) {
		return fusion.hasLink( field );
	}

	public boolean hasLink( String field, int index ) {
		return fusion.hasLink( field, index );
	}

	public boolean hasLink( String field, Fusion value ) {
		return fusion.hasLink( field, value );
	}

	public boolean hasLink( String field, int index, Fusion value ) {
		return fusion.hasLink( field, index, value );
	}

	public boolean hasOneChild( String field ) {
		return fusion.hasOneChild( field );
	}

	public void updateValue( String key, int index, String value ) throws IllegalArgumentException, UnsupportedOperationException {
		try {
			fusion.updateValue( key, index, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().updateValue( key, index, value );
		}
	}

	public int sizeFields() {
		return fusion.sizeFields();
	}

	public boolean hasSizeFields( int n ) {
		return fusion.hasSizeFields( n );
	}

	public void setValue( String key, String value ) throws UnsupportedOperationException {
		try {
			fusion.setValue( key, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().setValue( key, value );
		}
	}

	public boolean hasNoFields() {
		return fusion.hasNoFields();
	}

	public boolean hasAnyFields() {
		return fusion.hasAnyFields();
	}

	public void setAllValues( String key, Iterable< String > values ) throws UnsupportedOperationException {
		try {
			fusion.setAllValues( key, values );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().setAllValues( key, values );
		}
	}

	public int sizeChildren( String field ) {
		return fusion.sizeChildren( field );
	}

	public boolean hasSizeChildren( String field, int n ) {
		return fusion.hasSizeChildren( field, n );
	}

	public void addValue( String key, String value ) throws UnsupportedOperationException {
		try {
			fusion.addValue( key, value );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().addValue( key, value );
		}
	}

	public void removeValue( String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		try {
			fusion.removeValue( key );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().removeValue( key );
		}
	}

	public boolean hasNoChildren( String field ) {
		return fusion.hasNoChildren( field );
	}

	public boolean hasChildren( String field ) {
		return fusion.hasChildren( field );
	}

	public void removeValue( String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		try {
			fusion.removeValue( key, index );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().removeValue( key, index );
		}
	}

	public Set< String > fieldsToSet() {
		return fusion.fieldsToSet();
	}

	public Iterable< Entry< String, Fusion > > linksToIterable() {
		return fusion.linksToIterable();
	}

	public void print( Writer w ) {
		fusion.print( w );
	}

	public void clearAttributes( String key ) throws UnsupportedOperationException {
		try {
			fusion.clearAttributes( key );
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().clearAttributes( key );
		}
	}

	public List< Fusion > childrenToList( String field ) {
		return fusion.childrenToList( field );
	}

	public void clearAllAttributes() throws UnsupportedOperationException {
		try {
			fusion.clearAllAttributes();
		} catch ( UnsupportedOperationException _e ) {
			this.evolve().clearAllAttributes();
		}
	}

	public Map< String, Fusion > firstChildrenToMap() {
		return fusion.firstChildrenToMap();
	}

	public Map< Pair< String, Integer >, Fusion > linksToPairMap() {
		return fusion.linksToPairMap();
	}

	public int sizeAttributes() {
		return fusion.sizeAttributes();
	}

	public boolean hasSizeAttributes( int n ) {
		return fusion.hasSizeAttributes( n );
	}

	public boolean hasAnyAttributes() {
		return fusion.hasAnyAttributes();
	}

	public boolean hasNoAttributes() {
		return fusion.hasNoAttributes();
	}

	public boolean hasAttribute( String key ) {
		return fusion.hasAttribute( key );
	}

	public boolean hasValueAt( String key, int index ) {
		return fusion.hasValueAt( key, index );
	}

	public boolean hasAttribute( String key, String value ) {
		return fusion.hasAttribute( key, value );
	}

	public boolean hasAttribute( String key, int index, String value ) {
		return fusion.hasAttribute( key, index, value );
	}

	public boolean hasOneValue( String key ) {
		return fusion.hasOneValue( key );
	}

	public int sizeKeys() {
		return fusion.sizeKeys();
	}

	public boolean hasSizeKeys( int n ) {
		return fusion.hasSizeKeys( n );
	}

	public boolean hasNoKeys() {
		return fusion.hasNoKeys();
	}

	public boolean hasAnyKeys() {
		return fusion.hasAnyKeys();
	}

	public int sizeValues( String key ) {
		return fusion.sizeValues( key );
	}

	public boolean hasSizeValues( String key, int n ) {
		return fusion.hasSizeValues( key, n );
	}

	public boolean hasNoValues( String key ) {
		return fusion.hasNoValues( key );
	}

	public boolean hasAnyValues( String key ) {
		return fusion.hasAnyValues( key );
	}

	public Set< String > keysToSet() {
		return fusion.keysToSet();
	}

	public List< Entry< String, String > > attributesToList() {
		return fusion.attributesToList();
	}

	public List< String > valuesToList( String key ) {
		return fusion.valuesToList( key );
	}

	public Map< String, String > firstValuesToMap() {
		return fusion.firstValuesToMap();
	}

	public Map< Pair< String, Integer >, String > attributesToPairMap() {
		return fusion.attributesToPairMap();
	}

	@Override
	public Iterator< Entry< String, String > > attributesIterator() {
		return fusion.attributesIterator();
	}

	@Override
	public Iterator< Entry< String, Fusion > > linksIterator() {
		return fusion.linksIterator();
	}

	@Override
	public void freeze() {
		this.fusion.freeze();
	}
	
}
