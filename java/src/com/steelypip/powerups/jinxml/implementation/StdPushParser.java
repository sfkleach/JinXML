package com.steelypip.powerups.jinxml.implementation;

import java.io.Reader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Event;
import com.steelypip.powerups.jinxml.EventHandler;
import com.steelypip.powerups.jinxml.PushParser;

public class StdPushParser implements PushParser {
	
	final PrototypeParser pp;
	
	public StdPushParser( Reader reader ) {
		this.pp = new PrototypeParser( reader );
	}
	
	public StdPushParser( CharRepeater rep ) {
		this.pp = new PrototypeParser( rep );
	}
	
	/*************************************************************************
	* Implementation for interception
	*************************************************************************/
	
	ConstructingEventHandler _constructor;
	
	ConstructingEventHandler constructor() {
		if ( this._constructor == null ) {
			this._constructor = new ConstructingEventHandler();
		}
		return this._constructor;
	}
	
	Event getEvent() {
		return this._constructor == null ? null : this._constructor.getEvent();
	}
	
	/*************************************************************************
	* Public methods
	*************************************************************************/
	

	@Override
	public Event readEvent( Event otherwise ) {
		Event e = this.getEvent();
		if ( e == null ) {
			if ( this.pp.readNextTag( this.constructor() ) ) {
				e = this.constructor().getEvent();
			}
		}
		return e;
	}

	/**
	 * Reads to the end of the current expression.
	 */
	@Override
	public Stream< Event > readExpression() {
		return (
			StreamSupport.stream( 
				new Spliterators.AbstractSpliterator< Event >( Long.MAX_VALUE, Spliterator.ORDERED ) {
					
					boolean started = false;

					@Override
					public boolean tryAdvance( Consumer< ? super Event > action ) {
						Event e = StdPushParser.this.getEvent();
						if ( e != null ) {
							action.accept( e );
							return true;
						} else if ( StdPushParser.this.pp.isAtTopLevel() && this.started ) {
							return false;
						} else {
							boolean progressed = pp.readNextTag( StdPushParser.this.constructor() );
							this.started = true;
							e = StdPushParser.this.getEvent();
							if ( e != null ) {
								action.accept( e );
							}
							return progressed;
						}
					}
					
				}, 
				false 
			)
		);
	}

	@Override
	public Stream< Event > readInput() {
		return (
			StreamSupport.stream( 
				new Spliterators.AbstractSpliterator< Event >( Long.MAX_VALUE, Spliterator.ORDERED ) {					
					@Override
					public boolean tryAdvance( Consumer< ? super Event > action ) {
						Event e = StdPushParser.this.getEvent();
						if ( e != null ) {
							action.accept( e );
							return true;
						} else {
							boolean progressed = pp.readNextTag( StdPushParser.this.constructor() );
							e = StdPushParser.this.getEvent();
							if ( e != null ) {
								action.accept( e );
							}
							return progressed;
						}
					}
				}, 
				false 
			)
		);
	}
	
	@Override
	public Element readElement() {
		return this.readElement( false );
	}

	@Override
	public Element readElement( boolean solo ) {
		final StdBuilder builder = new StdBuilder();
		boolean oneAlready = false;
		while ( this.pp.readNextTag( builder ) ) {
			if ( builder.hasNext() ) {
				if ( solo && oneAlready ) {
					throw new IllegalStateException(); 
				}
				oneAlready = true;
				break;
			}
		}
		return builder.tryNext();
	}

	@Override
	public Stream< Element > readElementStream() {
		final StdBuilder builder = new StdBuilder();
		return ( 
			StreamSupport.stream( 
				new Spliterators.AbstractSpliterator< Element >( Long.MAX_VALUE, Spliterator.ORDERED ) {					
					@Override
					public boolean tryAdvance( Consumer< ? super Element > action ) {
						while ( StdPushParser.this.pp.readNextTag( builder ) ) {
							if ( builder.hasNext() ) break;
						}
						Element element = builder.tryNext();
						action.accept( element );
						return element != null;
					}
				}, 
				false 
			)
		);
	}

	@Override
	public void sendEvent( EventHandler handler ) {
		Event e = this.getEvent();
		if ( e != null ) {
			e.sendTo( handler );
		} else {
			this.pp.readNextTag( handler );
		}
	}

	private void drain( EventHandler handler ) {
		for (;;) {
			Event e = StdPushParser.this.getEvent();
			if ( e == null ) break;
			e.sendTo(  handler );
		}		
	}
	
	@Override
	public void sendExpression( EventHandler handler ) {
		this.drain( handler );
		while ( this.pp.readNextTag( handler ) ) {
			if ( pp.isAtTopLevel() ) break;
		}	
	}

	@Override
	public void sendInput( EventHandler handler ) {
		this.drain( handler );
		while ( pp.readNextTag( handler ) ) {
			//	Skip.
		}
	}

}
