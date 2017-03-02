package dkeep.logic;

public class Rookie implements Behaviour {

	private final static int[][] guard_mov = { 
			{ 0, -1}, { 1,  0}, { 1,  0}, { 1,  0}, { 1,  0},
			{ 0, -1}, { 0, -1}, { 0, -1}, { 0, -1}, { 0, -1},
			{ 0, -1}, { 1,  0}, { 0,  1}, { 0,  1}, { 0,  1},
			{ 0,  1}, { 0,  1}, { 0,  1}, { 0,  1}, {-1,  0},
			{-1,  0}, {-1,  0}, {-1,  0}, {-1,  0}, {-1,  0}
	};
	
	private int count;

	public Rookie() {
		count = 0;
	}

	@Override
	public final int[] getMovement() {
		
		++count;
		if (count >= guard_mov.length)
			count = 0;
		
		return guard_mov[count];
	}

}
