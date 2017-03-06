package dkeep.logic;
import java.util.Random;

public class Guard extends Character {
	
	private Behaviour strategy;
		
	public Guard(int[] initial_pos) {
		super(initial_pos, 'G', 'g');
		armed = true;
		
		Random rand = new Random();
		switch (rand.nextInt(3)) {
		case 0:
			strategy = new Drunken();
			break;
		case 1:
			strategy = new Suspicious();
			break;
		case 2:
			strategy = new Rookie();
			break;
		default:
			System.err.println("Error in Guard Constructor!!");
		}
	}

	@Override
	public void update(Map map) {
		int[] tmp = strategy.getMovement();
		
		if (tmp == null) {
			this.setInactive();
		} else {
			this.setActive();
			
			if (map.isValid(tmp[0] + pos[0], tmp[1] + pos[1])) {
				pos[0] += tmp[0];
				pos[1] += tmp[1];
			}
		}
	}
	
	@Override
	public void update(int row, int col) {
		pos[0] += row;
		pos[1] += col;
	}

}
