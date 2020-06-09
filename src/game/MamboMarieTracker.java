package game;

import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
/**
 * MamboMarieTracker is a StationaryItem that keeps track of MamboMarie when she is no longer
 * on the map. This allows her to spawn back on the map.
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class MamboMarieTracker extends StationaryItem {
	private String actorName;
	private MamboMarie oldMamboMarie;
	private Random rand = new Random();
	private int hp;
	private int tickCount = 0;
	
	/**
	 * Constructor, creates a new Mambo Marie with the same HP as the previous one.
	 *
	 * @param actor The actor that the tracker belongs to.
	 */
	public MamboMarieTracker(Actor actor, Location location) {
		super(actor.toString() + " left overs", location.getGround().getDisplayChar());
		actorName = actor.toString();
		hp = actor.getCurrentHitPoints();
		oldMamboMarie = new MamboMarie(actorName, hp);
	}
	
	@Override
	/**
	 * Checks to see if MamboMarie is ready to appear back onto the map.
	 * If she is, then she is spawned back onto the map with her previous HP.
	 */
	public void tick(Location currentLocation) {
		int randomInt = rand.nextInt(20);
		if (!(currentLocation.containsAnActor()) && tickCount != 0) {
			if (randomInt == 0) {
				currentLocation.addActor(oldMamboMarie);
				currentLocation.removeItem(this);
			}
		}
		tickCount++;
	}
}
