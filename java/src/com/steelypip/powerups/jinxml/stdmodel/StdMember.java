package com.steelypip.powerups.jinxml.stdmodel;

import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Member;

public class StdMember implements Member {
	
	@NonNull String selector;
	@NonNull Element child;
	
	public StdMember( @NonNull String selector, @NonNull Element child ) {
		super();
		this.selector = selector;
		this.child = child;
	}

	public StdMember( Map.Entry< String, Element > m ) {
		super();
		final String s = m.getKey();
		final Element c = m.getValue();
		this.selector = Objects.requireNonNull( s );
		this.child = Objects.requireNonNull( c );
	}

	@Override
	public @NonNull String getSelector() {
		return selector;
	}

	@Override
	public @NonNull Element getChild() {
		return child;
	}	
	
	public int hashCode() {
		return this.getSelector().hashCode() * 89 + this.getChild().hashCode();
	}
	
	public boolean equals( final Object that_object ) {
		if ( that_object == null || that_object.getClass() != StdMember.class ) return false;
		final StdMember that = (StdMember)that_object;
		return this.selector.equals( that.selector ) && this.child.equals(  that.child );
	}

}
