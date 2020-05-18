package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class SowAction extends Action {
	
	protected Location sowTargetLocation;
	
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
