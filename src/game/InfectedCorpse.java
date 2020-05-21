package game;

import java.util.Random;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;

/**
 * Special type of Corpse that reanimates as a Zombie 5-10 turns after creation.
 * 
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class InfectedCorpse extends Corpse {
	private int age;
	private int reanimatesWhen;
	private Random rand = new Random();
	
	/**
	 * Constructor
	 *
	 * @param actor The actor that the corpse belongs to.
	 */
	public InfectedCorpse(Actor actor) {
		super(actor);
		displayChar = '‰';
		age = 0;		
		reanimatesWhen = rand.nextInt(6) + 5;
	}

	@Override
	public void tick(Location currentLocation) {
		age++;
		if (age > reanimatesWhen) {
			//corpse reanimates when the Location is vacated
			if (!(currentLocation.containsAnActor())) {
				Zombie newZombie = new Zombie(actorName + "aargh");
				currentLocation.addActor(newZombie);
				currentLocation.removeItem(this);
			}

		}
	}
	
	
}
