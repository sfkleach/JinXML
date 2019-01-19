package com.steelypip.powerups.hydraxml;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.Pair;

public class BadHydraXML implements HydraXML {

	@Override
	public String getValue( String key ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue( String key, int index ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue( String key, String otherwise ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getValue( String key, int index, String otherwise ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateValue( String key, int index, String value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue( String key, String value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllValues( String key, Iterable< String > values ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addValue( String key, String value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeValue( String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeValue( String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearAttributes( String key ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int sizeAttributes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoAttributes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAttribute( String key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasValueAt( String key, int index ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAttribute( String key, String value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAttribute( String key, int index, String value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int sizeKeys() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoKeys() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int sizeValues( String key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasSizeValues( String key, int n ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set< String > keysToSet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List< Entry< String, String > > attributesToList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List< String > valuesToList( String key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map< String, String > firstValuesToMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map< Pair< String, Integer >, String > attributesToPairMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public HydraXML getChild() throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public HydraXML getChild( int index ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public HydraXML getChild( String field ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public HydraXML getChild( String field, int index ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public HydraXML getChild( String field, HydraXML otherwise ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public HydraXML getChild( String field, int index, HydraXML otherwise ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setChild( String field, HydraXML value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateChild( String field, int index, HydraXML value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllChildren( String field, Iterable< HydraXML > values ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild( String field, HydraXML value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild( HydraXML value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild( String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild( String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearLinks( String key ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoLinks() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int sizeLinks() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLink( String field ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLink( String field, int index ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLink( String field, HydraXML value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLink( String field, int index, HydraXML value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int sizeFields() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoFields() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int sizeChildren( String field ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoChildren( String field ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set< String > fieldsToSet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List< Entry< String, HydraXML > > linksToIterable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List< HydraXML > childrenToList( String field ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map< String, HydraXML > firstChildrenToMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map< Pair< String, Integer >, HydraXML > linksToPairMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator< Entry< String, HydraXML > > iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public @NonNull String getInternedName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void freeze() {
		throw new UnsupportedOperationException();
	}

	
	
}
