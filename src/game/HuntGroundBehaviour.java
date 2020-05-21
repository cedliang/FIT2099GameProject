package game;


import edu.monash.fit2099.engine.Location;


/**
 * Returns a MoveAction that will take the Actor closer to the nearest instance of a Ground class.
 * 
 * This uses a breadth-first search algorithm and is based on code written for the
 * FIT2099 assignment in S2 2019 by Spike.
 * 
 * @author ram
 *
 */
public class HuntGroundBehaviour extends HuntBehaviour {

	public HuntGroundBehaviour(Class<?> cls, int range) {
		super(cls, range);
	}


	protected boolean containsTarget(Location here) {
		Boolean returnBool = false;
		

		if (targetClass.isInstance(here.getGround())) {
			returnBool = true;
		}

		
		return returnBool;
	}





}