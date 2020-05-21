package game;


import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Item;


/**
 * Returns a MoveAction that will take the Actor closer to the nearest instance of an Item class.
 * 
 * This uses a breadth-first search algorithm and is based on code written for the
 * FIT2099 assignment in S2 2019 by Spike.
 * 
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class HuntItemBehaviour extends HuntBehaviour {

	public HuntItemBehaviour(Class<?> cls, int range) {
		super(cls, range);
	}



	//rewrote containsTarget support hunt for items
	protected boolean containsTarget(Location here) {
		Boolean returnBool = false;
		
		for (Item item : here.getItems()) {
			if (targetClass.isInstance(item)) {
				returnBool = true;
			}
		}
		
		return returnBool;
	}




}
