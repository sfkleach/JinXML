package com.steelypip.powerups.jinxml.stdmodel;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrimaryPath implements Path {

	Path _parent;
	String _selector;
	int _position;
	Element _child;
	Map< Element, Path > _tracker = new HashMap< Element, Path >();

	private PrimaryPath(Path parent, String selector, int position, Element child ) {
		this._parent = parent;
		this._selector = selector;
		this._position = position;
		this._child = child;
	}

	static public Path fromElement( Element root ) {
		return new PrimaryPath( null, Element.DEFAULT_SELECTOR, 0, root );
	}

	@Override
	public Element getElement() {
		return this._child;
	}

	@Override
	public String getSelector() {
		return this._selector;
	}

	@Override
	public int getPosition() {
		return this._position;
	}

	@Override
	public Path getParent() {
		return this._parent;
	}

	@Override
	public Iterable< Path > generatePaths() {
		final ArrayList< Path > paths = new ArrayList<>();
		int position = 0;
		for ( Element e : this._child.getChildrenAsList() ) {
			Path p = this.fetchPath( Element.DEFAULT_SELECTOR, position++, e );
			paths.add( p );
		}
		return paths;
	}

	@Override
	public Iterable< Path > generatePaths( String selector ) {
		final ArrayList< Path > paths = new ArrayList<>();
		int position = 0;
		for ( Element e : this._child.getChildrenAsList( selector ) ) {
			Path p = this.fetchPath( selector, position++, e );
			paths.add( p );
		}
		return paths;
	}

	private Path fetchPath( String selector, int position, Element e ) {
		Path p = this._tracker.get(e);
		if ( p == null ) {
			Path q = new PrimaryPath(this, selector, position, e);
			this._tracker.put(e, q);
			return q;
		} else {
			return new SecondaryPath( p );
		}
	}

	@Override
	public boolean isAlreadyGenerated() {
		return false;
	}

	@Override
	public boolean isOwnAncestor() {
		Element starting_element = this.getElement();
		Path ancestor = this.getParent();
		while ( ancestor != null ) {
			if ( ancestor.getElement() == starting_element ) return true;
		}
		return false;
	}

}
