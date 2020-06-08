package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;


/**
 * Moves an Actor to a Location within the game world.
 */
public class TeleportAction extends Action {
	

	private Location destination;
	private String itemname;
	
	/**
	 * Constructor
	 *
	 * @param destination the destination of teleportation
	 */
	public TeleportAction(Location destination, String itemdesc) {
	this.destination= destination;
	this.itemname=itemdesc;
	}
	
	
	//precondition: destination is not the location of an impassable Ground type
	@Override
	public String execute(Actor actor, GameMap map) {
		map.removeActor(actor);
		destination.map().addActor(actor, destination);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " travels using the " + itemname;
	}

}
