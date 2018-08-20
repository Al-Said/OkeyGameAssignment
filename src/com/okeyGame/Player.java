/**
 * @author Said Alýr - 20.08.18
 *  Player has 14 or 15 okey tiles.
 *  Sort and align his tiles in optimum way.
 */
package com.okeyGame;

import java.util.ArrayList;
import java.util.Collections;

public class Player {

	private String name;
	private ArrayList<OkeyTile> playerTile;
	private ArrayList<OkeyTile> groups;
	private ArrayList<OkeyTile> remainderPairs;
	private ArrayList<OkeyTile> okeys;
	private int remainder;
	private int numberOfRemainderPairs;
	private boolean hasTile = false;
	private boolean hasOkey = false;

	
	public Player(String name) {
		
		this.name = name;
		playerTile = new ArrayList<>();
		groups = new ArrayList<>();
		remainder = 0;
		numberOfRemainderPairs = 0;
		okeys = new ArrayList<>();
	}
	
	public void setPlayerTile(ArrayList<OkeyTile> okeytile) {
		
		playerTile = okeytile;
		hasTile = true;
	}
	
	public ArrayList<OkeyTile> getPlayerTile(){

		if(hasTile) {
			
			return playerTile;
		}
		else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public void alignTileSerial() {
		
		checkIfThereIsAnOkey();
		
		if(!hasTile) {
			throw new IllegalAccessError();
		} else {
			
			sortTile();
			makeRunOrGroups();
			createPairsWithRemainders();
			remainder = playerTile.size();
			
			if(doesPlayerHasOkey()) 
				useOkey();
		}
	}

	public boolean doesPlayerHasOkey() {
		
		return hasOkey;
	}
	
	public int getRemainder() {
		
		return remainder;
	}
	
	public int getNumberOfRemainderPairs() {
		
		return numberOfRemainderPairs;
	}

	public ArrayList<OkeyTile> getGroups() {
		return groups;
	}

	public ArrayList<OkeyTile> getRemainderPairs() {
		return remainderPairs;
	}
	
	
	@Override
	public String toString() {
		return name;
	}

	private void useOkey() {
		
		if(remainderPairs.size() != 0) {
			
			groups.add(remainderPairs.get(0));
			groups.add(remainderPairs.get(1));
			groups.add(okeys.get(0));
			remainderPairs.remove(0);
			remainderPairs.remove(0);
			okeys.remove(0);
		}
		
	}

	private void createPairsWithRemainders() {
		
		OkeyTile pivot;
		remainderPairs = new ArrayList<>();
		
		for(int i=0;i<playerTile.size(); i++) {
			
			pivot = playerTile.get(i);
			ArrayList<OkeyTile> group = new ArrayList<>();
			group = makeRun(pivot);//First priority is making runs..
			if(group.size() != 2) {
				
				playerTile.addAll(group);
				sortTile();
				group = makeColorGroup(pivot);//If there is no possibility of making run with pivot then...
				
				if(group.size() != 2) {
					
					playerTile.addAll(group); //Then there is no chance to make a run or group with pivot... adding back...
					sortTile();
					
				} else {
					
					
					remainderPairs.addAll(group);
					numberOfRemainderPairs++;
				}
			} else {
				
				remainderPairs.addAll(group);
				numberOfRemainderPairs++;
			}
		}//end of loop
		
	}
	
	/**
	 * It is created to make it easier to follow.
	 */
	private void sortTile() {
		
		Collections.sort(playerTile);
	}	
	
	private void makeRunOrGroups() {
		
		OkeyTile pivot;
		
		for(int i=0;i<playerTile.size(); i++) 
		{
			
			pivot = playerTile.get(i);
			ArrayList<OkeyTile> group = makeRun(pivot);
			if(group.size() < 3) 
			{
				playerTile.addAll(group);
				sortTile();
				group = makeColorGroup(pivot);
				
				if(group.size() < 3)
				{	
					playerTile.addAll(group);
					sortTile();
				}
				else
				{
					groups.addAll(group);
				}
			} 
			else 
			{
				groups.addAll(group);
			}
			
		group.clear();
		}//end of loop
	}

	/**
	 * Creating runs starting with pivot.
	 * For example pivot is YELLOW-5 
	 * Runs can be YELLOW(5-6-7) OR YELLOW(5-6-7-8) etc.
	 * @param pivot 
	 * @return longest run from pivot.
	 */
	private ArrayList<OkeyTile> makeRun(OkeyTile pivot) {
		
		ArrayList<OkeyTile> run = new ArrayList<>();
		
		run.add(pivot);
		playerTile.remove(pivot);
		ArrayList<OkeyTile> iterationList = makeCopyOf(playerTile);
		
		for(OkeyTile o : iterationList) {
			
			if(o.getNumber() == pivot.getNumber() + 1 && o.getColor().equals(pivot.getColor())) {
				pivot = o;
				run.add(pivot);
				playerTile.remove(pivot);
			}
		}
		return run;
	}
	
	/**
	 * Creating groups start with the pivot.
	 * For example pivot is YELLOW-5
	 * Groups can be YELLOW-BLUE-RED(5) etc.
	 * @param pivot
	 * @return
	 */
	private ArrayList<OkeyTile> makeColorGroup(OkeyTile pivot) {
		
		ArrayList<OkeyTile> group = new ArrayList<>();
		
		group.add(pivot);
		playerTile.remove(pivot);
		ArrayList<OkeyTile> iterationList = makeCopyOf(playerTile); //Iterate over copy, remove from original list.
		ArrayList<Color> acceptable = new ArrayList<>();
		acceptable.add(Color.BLACK);
		acceptable.add(Color.BLUE);
		acceptable.add(Color.RED);
		acceptable.add(Color.YELLOW);
		acceptable.remove(pivot.getColor());
		
		for(OkeyTile o : iterationList) {
		
			if(o.getNumber() == pivot.getNumber() && acceptable.contains(o.getColor())) {
				group.add(o);
				playerTile.remove(o);
				acceptable.remove(o.getColor());
			}
		}
		
		return group;
	}
	
	private ArrayList<OkeyTile> makeCopyOf(ArrayList<OkeyTile> list) {
		
		ArrayList<OkeyTile> copy = new ArrayList<>();
		for(OkeyTile o : list) {
			copy.add(o);
		}
		return copy;
	}

	/**
	 * Check if there is an okey.. If player has okey, mark it as okey and reserve it.
	 */
	private void checkIfThereIsAnOkey() {
				
		for(OkeyTile o : playerTile) {
			
			if(o.getNumber() == GameManager.fakeOkey.getNumber()    && 
			   o.getColor().equals(GameManager.fakeOkey.getColor()) &&
			 !(o.toString().equals("FAKE OKEY"))) {
				o.setTileOkey();
				okeys.add(o);
				hasOkey=true;
			}
		}
		playerTile.removeAll(okeys);
		
	}
}
 	