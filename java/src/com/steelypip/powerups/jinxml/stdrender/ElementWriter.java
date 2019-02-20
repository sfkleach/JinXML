package com.steelypip.powerups.jinxml.stdrender;

import java.io.PrintWriter;
import java.io.Writer;

import com.steelypip.powerups.common.IndenterFactory;
import com.steelypip.powerups.common.NullIndenter;
import com.steelypip.powerups.jinxml.Element;

public class ElementWriter extends ThemeableWriter< Element > {

	public ElementWriter( PrintWriter pw, IndenterFactory indenter_factory, Theme< Element > theme ) {
		super( pw, indenter_factory, theme );
	}
	
	private static NullIndenter.Factory DEFAULT_INDENT_FACTORY = new NullIndenter.Factory();
	private static Theme< Element > DEFAULT_THEME = new JSONTheme().compose( new StartEndTagTheme() );
	
	public ElementWriter( PrintWriter pw ) {
		this( pw, DEFAULT_INDENT_FACTORY, DEFAULT_THEME );
	}
	
	public ElementWriter( PrintWriter pw, IndenterFactory indenter_factory ) {
		super( pw, indenter_factory, DEFAULT_THEME );
	}
	
	public ElementWriter( Writer w ) {
		this( new PrintWriter( w ) );
	}

	
}
