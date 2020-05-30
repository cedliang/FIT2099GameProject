package game;

import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;

public class MamboMarieTracker extends StationaryItem {
	protected String actorName;
	protected Random rand = new Random();
	protected int HP;
	
	/**
	 * Constructor
	 *
	 * @param actor The actor that the corpse belongs to.
	 */
	public MamboMarieTracker(Actor actor, Location location) {
		super(actor.toString() + " left overs", location.getGround().getDisplayChar());
		actorName = actor.toString();
		HP = actor.getCurrentHitPoints();
	}
	
	@Override
	public void tick(Location currentLocation) {
		int randomInt = rand.nextInt(20);
		if (!(currentLocation.containsAnActor())) {
			if (randomInt == 0) {
				MamboMarie newMamboMarie = new MamboMarie(actorName);
				newMamboMarie.hurt(newMamboMarie.getCurrentHitPoints()-HP);
				currentLocation.addActor(newMamboMarie);
				currentLocation.removeItem(this);
			}
		}
	}
}
