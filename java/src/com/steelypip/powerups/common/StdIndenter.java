/**
 * Copyright Stephen Leach, 2014
 * This file is part of the MinXML for Java library.
 * 
 * MinXML for Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinXML for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */
package com.steelypip.powerups.common;

import java.io.PrintWriter;

public class StdIndenter extends Indenter{
	
	protected final PrintWriter pw;
	protected int indent_level = 0;
	protected int tab_step = 4; 
	
	public StdIndenter( PrintWriter pw ) {
		super();
		this.pw = pw;
	}

	@Override
	public void indent() {
		for ( int n = 0; n < this.indent_level; n++ ) {
			pw.write( ' ' );
		}
	}
	
	@Override
	public void tab() {
		this.indent_level += this.tab_step;
	}
	
	@Override
	public void untab() {
		this.indent_level -= this.tab_step;
	}
	
	@Override
	public void newline() {
		pw.println();
	}
	
	public static class Factory implements IndenterFactory {

		@Override
		public Indenter newIndenter( PrintWriter pw ) {
			return new StdIndenter( pw );
		}
		
	}
	
}
