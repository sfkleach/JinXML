package com.steelypip.powerups.jinxml.stdmodel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.StdPair;
import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Event;

public class InOrderTraversal {
	
	static abstract class Action {
		public abstract void doAction( InOrderTraversal t );
	}
	
	static class VisitAction extends Action {
		
		final Map.Entry<String, Element> entry;

		public VisitAction( Map.Entry<String, Element> entry ) {
			this.entry = entry;
		}
		
		public void doAction( InOrderTraversal t ) {
			t.visitAction( entry );
		}
	}
	
	static class EndAction extends Action {
		
		final @NonNull Element element;

		public EndAction( @NonNull Element element ) {
			this.element = element;
		}
		
		public void doAction( InOrderTraversal t ) {
			t.endAction( element );
		}
	}

	ArrayDeque< Event > buffer = new ArrayDeque< Event >();
	ArrayDeque< Action > actions = new ArrayDeque< Action >();
	
	public InOrderTraversal( final @NonNull Element element ) {
		this.actions.addFirst( new VisitAction( new StdPair<>( "", element ) ) );
	}
	
	public Event getEvent() {
		if ( this.buffer.isEmpty() ) {
			this.advance();
		}
		if ( this.buffer.isEmpty() ) {
			return null;
		}
		return this.buffer.removeFirst();
	}
	
	void advance() {
		if ( this.actions.isEmpty() ) return;
		Action action = this.actions.removeLast();
		action.doAction( this );
	}
	
	void visitAction( Map.Entry< String, Element > entry ) {
		String selector = entry.getKey();
		Element element = entry.getValue();
		this.buffer.addLast( new Event.StartTagEvent( selector, element.getName() ) );
		for ( Map.Entry<String,String> attr : element.getAttributesAsMultiMap().entriesToList() ) {
			this.buffer.addLast( new Event.AttributeEvent( attr.getKey(), attr.getValue() ) );
		}
		this.actions.addLast( new EndAction( element ) );
		List< Map.Entry<String, Element> > members = new ArrayList<>( element.getMembersAsMultiMap().entriesToList() );
		Collections.reverse( members );
		for ( Map.Entry<String, Element> member : members ) {
			this.actions.addLast( new VisitAction( member ) );
		}
	}
	
	void endAction( @NonNull Element element ) {
		this.buffer.addLast( new Event.EndTagEvent( element.getName() ) );
	}
	
}
