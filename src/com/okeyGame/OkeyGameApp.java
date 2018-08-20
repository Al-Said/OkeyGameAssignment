/**
 * @author Said Alýr - 20.08.18
 * 
 * It is the main class to see if other classes are working correctly.
 */
package com.okeyGame;

public class OkeyGameApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameManager gm = new GameManager();
		System.out.println("Indicator Tile is  ===> " + GameManager.indicatorTile);
		gm.showPlayersHands();
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		gm.announceWinner();
	}

}
