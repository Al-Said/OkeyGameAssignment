/**
 * @author Said Alýr - 20.08.18
 * 
 * OkeyTile (Turkish: Okey Taþý) is a minimal piece of this game. 
 * It can be comparable with other OkeyTiles.
 * It has color, number and it could be a Okey or not.
 * 
 */
package com.okeyGame;

public class OkeyTile implements Comparable<OkeyTile>{
	
	private int number;
	private Color color;
	private String name;
	private boolean isOkey = false;
	
	public OkeyTile(int number, Color color) {
		
		if(color.equals(Color.FAKE_OKEY)) {
			
			this.number = 0;
			this.color = color;
		} else {
			
			setNumber(number);
			setColor(color);	
		}
		
		setName(number, color);
	}
	
	public int getNumber() {
		
		if(color.equals(Color.FAKE_OKEY)) {
			return GameManager.fakeOkey.number;
		}
		return number;
	}
	
	public void setNumber(int number) {
		
		if(number < 0 || number > 13) {
			
			
			throw new IllegalArgumentException();
		}
		else {
			this.number = number;
		}
	}
	
	public Color getColor() {
		
		if(color.equals(Color.FAKE_OKEY) && GameManager.fakeOkey != null) {
			return GameManager.fakeOkey.color;
		}
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	private void setName(int number, Color color) {
		
		name = color.getName() + "-" + number;
		if(color.equals(Color.FAKE_OKEY)) {
			name = color.getName();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean isOkey() {
		return isOkey;
	}
	
	public void setTileOkey() {
		isOkey = true;
	}

	@Override
	public int compareTo(OkeyTile o) {
		
		int answer;
		if(number == 0) {
			return GameManager.fakeOkey.compareTo(o);
		} else if (o.number == 0) {
			return compareTo(GameManager.fakeOkey);
		} else if(number > o.number) {
			answer = 1;
		} else if (number < o.number) {
			answer = -1;
		} else {
			answer = 0;
		}
		return answer;
	}
	
}
