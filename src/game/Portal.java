package game;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Location;

/**
 * Portal. Teleports an Actor to a Location.
 * 
 * 
 * This is a generic portal and can be used to allow entry to buildings, dungeons that are maps of their own, like in Pokemon, etc.
 */
public class Portal extends StationaryItem {
	


	private Location destination;
	public Portal(Location destination) {
		super("portal", '*');
		this.destination = destination;

	}

	//determines whether on the NEXT turn TeleportAction is allowable. This is because the world class manages actor interactions
	//and movements before item ticks per turn, so this method checks the current state of the map after all the actors have moved
	//and determines whether TeleportAction can be called.
	public void tick(Location location) {
		this.allowableActions = new Actions();
		if ((destination != null) && (destination.containsAnActor() == false) && (destination.getGround().canActorEnter(new Human("")))) {
			
			allowableActions.add(new TeleportAction(destination,toString()));
			}
	}
	


}


