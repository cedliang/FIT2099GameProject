package game;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Location;

/**
 * Portal. Teleports an Actor to a Location. By default, it doesn't link to anything and is inactive, but
 * can be set to have its destination defined.
 * 
 * 
 * 
 */
public class Portal extends StationaryItem {
	


	private Location destination;
	public Portal() {
		super("portal", '*');

	}

	//NOTE: modify the displayChar setting here if it is desired that the active portal have a different displayChar to the dormant one.
	public void setPortalDestination(Location destination) {
		this.destination = destination;
		this.displayChar='*';
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


