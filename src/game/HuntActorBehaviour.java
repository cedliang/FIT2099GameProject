
package game;

import edu.monash.fit2099.engine.Location;

public class HuntActorBehaviour extends HuntBehaviour {

	public HuntActorBehaviour(Class<?> cls, int range) {
		super(cls, range);
	}

	@Override
	protected boolean containsTarget(Location here) {
		return (here.getActor() != null &&
				targetClass.isInstance(here.getActor()));
	}
}
