package com.steelypip.powerups.jinxml.implementation;

import java.util.stream.Stream;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.EventHandler;
import com.steelypip.powerups.jinxml.PushParser;

public class StdPushParser implements PushParser {

	@Override
	public < T > T readHandledEvent( EventHandler< T > handler, T otherwise ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public < T > Stream< T > readHandledExpression(
			EventHandler< T > handler ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public < T > Stream< T > readHandledInput( EventHandler< T > handler ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream< Element > readElementStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element readElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element readElement( boolean solo ) {
		// TODO Auto-generated method stub
		return null;
	}

}
