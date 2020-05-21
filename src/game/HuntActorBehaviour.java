
package game;

import edu.monash.fit2099.engine.Location;

/**
 * Returns a MoveAction that will take the Actor closer to the nearest instance of an Actor class.
 * 
 * This uses a breadth-first search algorithm and is based on code written for the
 * FIT2099 assignment in S2 2019 by Spike.
 * 
 * @author Cedric Liang, Nathan
 *
 */
public class HuntActorBehaviour extends HuntBehaviour {


	public HuntActorBehaviour(Class<?> cls, int range) {
		super(cls, range);
	}

	protected boolean containsTarget(Location here) {
		return (here.getActor() != null &&
				targetClass.isInstance(here.getActor()));
	}
}
