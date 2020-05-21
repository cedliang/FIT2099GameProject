package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Action that changes the Ground at the target Location to Crop.
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class SowAction extends Action {
	
	protected Location sowTargetLocation;
	
	/**
	 * Constructor
	 *
	 * @param sowTargetLocation the Location that contains the Crop to be sowed
	 */
	public SowAction(Location sowTargetLocation) {
		
	this.sowTargetLocation = sowTargetLocation;
		
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		Crop crop = new Crop();
		sowTargetLocation.setGround(crop);	
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " has planted some crops.";
	}

}
