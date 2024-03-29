package dkeep.logic;

import java.util.Arrays;

/**
 * Class responsible for handling Game Map's of type DungeonMap.
 */
public class DungeonMap extends GameMap implements java.io.Serializable {
	
	/**
	 * long SerialVersionUID. Class's ID for serialization.
	 */
	private static final long serialVersionUID = 11L;
	
	/**
	 * Boolean corresponding to whether doors are open.
	 */
	private boolean doors_open = false;
	
	/**
	 * Position of the lever in the Dungeon Map
	 */
	private static int[] lever_pos = {8, 7};
	
	/**
	 * Initial hero position in the Dungeon Map
	 */
	public static int[] hero_pos = {1, 1};	// initial hero position
	
	/**
	 * Initial guard position in the Dungeon Map
	 */
	public static int[] guard_pos = {1, 8};	// initial guard position
	
	public DungeonMap() {
		super.setVictoryPos(new int[][] {{5, 0}, {6, 0}});
		
		this.map = new char[][] {
				{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
				{'X', 'B', 'B', 'B', 'I', 'B', 'X', 'B', 'B', 'X'},
				{'X', 'X', 'X', 'B', 'X', 'X', 'X', 'B', 'B', 'X'},
				{'X', 'B', 'I', 'B', 'I', 'B', 'X', 'B', 'B', 'X'},
				{'X', 'X', 'X', 'B', 'X', 'X', 'X', 'B', 'B', 'X'},
				{'I', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'X'},
				{'I', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'X'},
				{'X', 'X', 'X', 'B', 'X', 'X', 'X', 'X', 'B', 'X'},
				{'X', 'B', 'I', 'B', 'I', 'B', 'X', 'k', 'B', 'X'},
				{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'}
			};
	}
	
	/**
	 * Constructor with given map and victory_pos arrays.
	 * @param board	2D array representing map
	 * @param victory_pos Array of arrays representing victory positions
	 */
	public DungeonMap(char[][] board, int[][] victory_pos) {
		this.map = board;
		
		for (int i = 0 ; i < board.length; ++i) {
			for (int j = 0; j < board[0].length; ++j) {
				if (board[i][j] == 'k')
					lever_pos = new int[] {i, j};
			}
		}	
		
		this.setVictoryPos(victory_pos);
	}

	/**
	 * Opens all doors in map
	 */
	private void openDoors() {		
		if (doors_open)
			return;
		
		for (int r = 0; r < map.length; r++)
			for (int c = 0; c < map[r].length; c++)
				if (map[r][c] == 'I')
					map[r][c] = 'S';
		
		doors_open = true;
	}
	
	/* (non-Javadoc)
	 * @see dkeep.logic.GameMap#update(dkeep.logic.Hero)
	 */
	@Override
	public boolean update(Hero hero) {
		if (Arrays.equals(lever_pos, hero.pos))
			this.openDoors();
		
		return super.update(hero);
	}

}
