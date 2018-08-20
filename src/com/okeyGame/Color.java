/**
 * @author Said Alýr - 20.08.18
 * 	There could be five different types of colors in this game.
 * 	Fake okey is may not be a color however, it definitely not belongs others.
 */
package com.okeyGame;

public enum Color {
		
	BLACK("BLACK"),
	BLUE("BLUE"),
	RED("RED"),
	YELLOW("YELLOW"),
	FAKE_OKEY("FAKE OKEY");
	
	private String name;
	
	Color(String n) {
		name = n;
	}
	
	public String getName() {
		
		return name;
	}
}
