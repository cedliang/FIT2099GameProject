package game;
import edu.monash.fit2099.engine.Actor;

/**
 * StationaryItem that represents a corpse. Takes an Actor as a input in the constructor rather than
 * a String so it can be easily expanded to also hold an Actor as a field (for example, to support
 * resurrection later in the game)
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class Corpse extends StationaryItem {
	
	protected String actorName;
	
	/**
	 * Constructor
	 *
	 * @param actor The actor that the corpse belongs to.
	 */
	public Corpse(Actor actor) {
		super("dead "+actor.toString(), '%');
		actorName = actor.toString();		
	}

}
