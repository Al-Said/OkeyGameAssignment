/**
 * @author Said Alýr - 20.08.18
 * 
 * Okey set consists of 106 Okey Tile.
 * 4 different color (declared in enum)
 * and each color has 13 number.
 * and 2 fake okey.
 * 
 *  This class initialize okey set, shuffle and give Game Manager or Player a specific number of tile/s
 */
package com.okeyGame;

import java.util.ArrayList;
import java.util.Collections;


public class OkeySet {

	private ArrayList<OkeyTile> okey_set;
	
	public OkeySet() {
		
		initializeOkeySet();
	}
	
	private void initializeOkeySet() {
		
		Color color = Color.BLACK;
		OkeyTile tile;
		okey_set = new ArrayList<OkeyTile>(106);
		
		for(int k=0; k<2; k++) {
			
			//Since there are four types of color except fake okey
			for(int i=0; i<4; i++) {
				
				switch(i) {
				case 1:
					color = Color.BLUE;
					break;
				case 2:
					color = Color.RED;
					break;
				case 3:
					color = Color.YELLOW;
					break;
				default:	
					color = Color.BLACK;
					break;
				}//end of switch
				
				for(int j = 1; j<14; j++) {
					
					tile = new OkeyTile(j, color);
					okey_set.add(tile);
				}//end of inner loop
				
			}//end of outer loop.. 
		
			okey_set.add(new OkeyTile(0, Color.FAKE_OKEY));
		}	
	}
	
	public void shuffleSet() {
		
		Collections.shuffle(okey_set);
	}
	
	/**
	 * 
	 * @return only one tile from set.
	 */
	public OkeyTile getTile() {
		
		return getPlayerTile(1).get(0);
	}
	
	/**
	 * This method returns specific number of okey tile
	 * Method remove the tile which will be returned.
	 * 
	 * @param n : Number of okey tile
	 * @return ArrayList that contains okey tiles.
	 */
	public ArrayList<OkeyTile> getPlayerTile(int n) {
		
		if(n < 0 || n > okey_set.size()) {
			throw new ArrayIndexOutOfBoundsException();
		} else {
			
			ArrayList<OkeyTile> returnedTiles = new ArrayList<>();
			
			for(int i = 0; i < n; i ++) {
				
				returnedTiles.add(okey_set.get(0));
				okey_set.remove(0);
			}
			
			return returnedTiles;
		}
	}
	
	public void clear() {
		okey_set.clear();
	}

}
