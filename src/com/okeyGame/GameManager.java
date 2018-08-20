/**
 * @author Said Alýr - 20.08.18
 * This class manage whole game.
 * Instantiate okey set and players is this class' responsibility
 * Also after deliver all players tiles, decide winner and announce.
 */
package com.okeyGame;

import java.util.ArrayList;

public class GameManager {
	
	private OkeySet okeySet;
	private ArrayList<Player> players;
	public static OkeyTile indicatorTile; //In turkish "Gösterge Taþý"
	public static OkeyTile fakeOkey;
	
	public GameManager() {
		
		initializeOkeySet();
		initializePlayers();
		
	}
	
	/**
	 * Actually showPlayersHand and announceWinner methods should not be here.
	 * At least they should not I/O. 
	 * It harms the Data Access Layer - Bussiness Layer - Presentation Layer construction.
	 * However I don't have enough time to fix this problem.
	 */
	public void showPlayersHands() {
		
		for(Player p : players) {
			
			System.out.println(" ");
			System.out.println(p);
			System.out.println("----------------------");
			System.out.println(" ");
			System.out.println("Remaining tiles =>" + p.getPlayerTile());
			System.out.println("Groups          =>" + p.getGroups());
			System.out.println("Remainder pairs =>" + p.getRemainderPairs());
			System.out.println("Remaining tile number =>" + p.getRemainder());
			System.out.println("Does he has okey? " + p.doesPlayerHasOkey());
			System.out.println("-----------------------------");
			System.out.println(" ");
		}
	}
	
	public void announceWinner() {
		System.out.println("Winner is " + decideWinner());
	}
	
	/**
	 * To decide best hand
	 * First check how many irrelevant tiles each player has.. 
	 * If there is a draw then check is there any player that has okey..
	 * If still draw check which one has more groups.
	 * If still draw then check which player has more tiles that can be run or group easier.
	 * If still draw then pick first one as a best possibility of finish game.
	 * 
	 * @return winner
	 */
	private Player decideWinner() {
		
		ArrayList<Player> winnerCandidates = compareRemainders();
		if(winnerCandidates.size() == 1) {
			return winnerCandidates.get(0);
		} else {
			winnerCandidates = comparePlayersOkey(winnerCandidates);
			if(winnerCandidates.size() == 1) {
				return winnerCandidates.get(0);
			} else {
				winnerCandidates = compareGroupNumbers(winnerCandidates);
				if(winnerCandidates.size() == 1) {
					return winnerCandidates.get(0);
				} else {		
					winnerCandidates = compareRemainderPairs(winnerCandidates);
					return winnerCandidates.get(0);
				}
			}
		}
	}
	
	private ArrayList<Player> compareGroupNumbers(ArrayList<Player> players) {
		
		ArrayList<Player> winnerCandidates = new ArrayList<>();
		int max = 0;
		
		for(Player p: players) {
			if(max < p.getGroups().size()) {
				max = p.getGroups().size();
			}
		}
		
		for(Player p : players) {
			if(max == p.getGroups().size()) {
				winnerCandidates.add(p);
			}
		}
		
		return winnerCandidates;
	}

	private ArrayList<Player> compareRemainderPairs(ArrayList<Player> players) {
		
		ArrayList<Player> winnerCandidates = new ArrayList<>();
		int max = 0;
		
		for(Player p : players) {
			if(p.getNumberOfRemainderPairs() > max) {
				max = p.getNumberOfRemainderPairs();
			}
		}
		
		for(Player p : players) {
			if(max == p.getNumberOfRemainderPairs()) {
				winnerCandidates.add(p);
			}
		}
		
		return winnerCandidates;
	}

	private ArrayList<Player> comparePlayersOkey(ArrayList<Player> players) {
		
		ArrayList<Player> winnerCandidates = new ArrayList<>();
		
		for(Player p : players) {
			if(p.doesPlayerHasOkey())
				winnerCandidates.add(p);
		}
		
		if(winnerCandidates.isEmpty()) 
			return players;	
		
		return winnerCandidates;
		
	}

	private ArrayList<Player> compareRemainders() {
		
		int min = 15;
		ArrayList<Player> winnerCandidates = new ArrayList<>();
		for(Player p : players) {
			if(p.getRemainder() < min) {
				min = p.getRemainder();
			}
		}
		for(Player p : players) {
			if(p.getRemainder() == min) {
				winnerCandidates.add(p);
			}
		}
		return winnerCandidates;
	}

	private void initializeOkeySet() {
		
		okeySet = new OkeySet();
		okeySet.shuffleSet();
		indicatorTile = okeySet.getTile(); 
		
		while(indicatorTile.getColor().equals(Color.FAKE_OKEY)) {
			
			okeySet.shuffleSet();
			indicatorTile = okeySet.getTile();
		}
		
		if(indicatorTile.getNumber() == 13) {
			fakeOkey = new OkeyTile(1, indicatorTile.getColor());
		} else {
			
			fakeOkey = new OkeyTile(indicatorTile.getNumber() +1 , indicatorTile.getColor());
		}
	}

	private void initializePlayers() {
		
		players = new ArrayList<Player>(4);
		for(int i = 1; i < 5; i ++) {
			Player p = new Player("Player " + i);
			
			if(i == 1) {
				p.setPlayerTile(okeySet.getPlayerTile(15));
			}
			
			p.setPlayerTile(okeySet.getPlayerTile(14));
			p.alignTileSerial();
			players.add(p);
		}
		
	}

}
