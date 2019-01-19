package com.steelypip.powerups.json;

import com.steelypip.powerups.alert.Alert;

/**
 * This exception should be thrown when there a JSONBuilder
 * runs into a problem building a tree. It should definitely be
 * thrown if there is an attempt to parse multiple expressions
 * or there is an attempt to build a tree before a complete expression
 * has been consumed.
 * 
 * However, a particular JSONBuilder may also elect to throw this exception
 * if the JSON expression does not meet its own requirements, typically
 * because it is trying to unmarshall a particular datatype and some
 * components are missing.
 *
 */
public class JSONBuildFailedException extends Alert {

	private static final long serialVersionUID = 126523936624164196L;

	public JSONBuildFailedException() {
		super();
	}

	public JSONBuildFailedException( String message, Throwable cause ) {
		super( message, cause );
	}

	public JSONBuildFailedException( String message ) {
		super( message );
	}

	public JSONBuildFailedException( Throwable cause ) {
		super( cause );
	}	
	
}
