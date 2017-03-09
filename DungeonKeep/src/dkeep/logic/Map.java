package dkeep.logic;

import java.util.Arrays;

public class Map {
	
	protected char[][] map;
	
	protected char[] valid_symbs = {'B', 'S', 'k'};
	
	private int[][] victory_pos;
	
	Map (char[][] map, int[][] pos_vic) {
		this.map = map;
		this.victory_pos = pos_vic;
	}
	
	Map() {};
		
	protected void setVictoryPos(int[][] positions) {
		victory_pos = positions;
	}
			
	public char[][] getMap() {
		return map;
	}
	
	public boolean isValid(int row, int col) {
		for (char c : valid_symbs)
			if (map[row][col] == c)
				return true;
		return false;
	}
	
	// Returns false when game is over (hero reached victory position)
	public boolean update(Hero hero) {
		for (int[] v_pos : victory_pos) {
			if (Arrays.equals(v_pos, hero.pos))
				return false;
		}
				
		return true;		
	}
	
	public void setOnMap(int[] pos, char symb) {
		this.map[pos[0]][pos[1]] = symb;
	}
}
