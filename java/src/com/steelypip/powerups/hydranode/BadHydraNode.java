package com.steelypip.powerups.hydranode;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.Pair;

/**
 * The purpose of this class is to be a non-null initial object. It does not support any operation at all.
 */
public class BadHydraNode< Key extends Comparable< Key >, Value, Field extends Comparable< Field >, Child  > implements HydraNode< Key, Value, Field, Child > {

	@Override
	public @NonNull String getName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public @NonNull String getInternedName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasName( @Nullable String name ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Value getValue( Key key ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Value getValue( Key key, int index ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Value getValue( Key key, Value otherwise ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Value getValue( Key key, int index, Value otherwise ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue( Key key, Value value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateValue( Key key, int index, Value value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllValues( Key key, Iterable< Value > values ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addValue( Key key, Value value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeValue( Key key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeValue( Key key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearAttributes( Key key ) throws UnsupportedOperationException {
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
	public boolean hasAttribute( Key key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasValueAt( Key key, int index ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAttribute( Key key, Value value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasAttribute( Key key, int index, Value value ) {
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
	public int sizeValues( Key key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasSizeValues( Key key, int n ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set< Key > keysToSet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List< Map.Entry< Key, Value > > attributesToList() {
		throw new UnsupportedOperationException();
	}

	@Override
	public List< Value > valuesToList( Key key ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map< Key, Value > firstValuesToMap() {
		throw new UnsupportedOperationException();
	}

//	@Override
//	public StarMap< Key, Value > attributesToStarMap() {
//		throw new UnsupportedOperationException();
//	}

	@Override
	public Map< Pair< Key, Integer >, Value > attributesToPairMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Field defaultField() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Child getChild() throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Child getChild( int index ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Child getChild( Field field ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Child getChild( Field field, int index ) throws IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Child getChild( Field field, Child otherwise ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Child getChild( Field field, int index, Child otherwise ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setChild( Field field, Child value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateChild( Field field, int index, Child value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllChildren( Field field, Iterable< Child > values ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild( Field field, Child value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild( Child value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild( Field key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild( Field key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearLinks( Field key ) throws UnsupportedOperationException {
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
	public boolean hasLink( Field field ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLink( Field field, int index ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLink( Field field, Child value ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasLink( Field field, int index, Child value ) {
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
	public int sizeChildren( Field field ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoChildren( Field field ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set< Field > fieldsToSet() {
		throw new UnsupportedOperationException();
	}

//	@Override
//	public List< Map.Entry< Field, Child > > linksToList() {
//		throw new UnsupportedOperationException();
//	}

	@Override
	public List< Child > childrenToList( Field field ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map< Field, Child > firstChildrenToMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map< Pair< Field, Integer >, Child > linksToPairMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator< Map.Entry< Field, Child > > iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator< Entry< Key, Value > > attributesIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator< Entry< Field, Child > > linksIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable< Entry< Field, Child > > linksToIterable() {
		throw new UnsupportedOperationException();
	}
	
	

}
