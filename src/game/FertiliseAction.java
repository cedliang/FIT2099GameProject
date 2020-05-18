package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Ground;

public class FertiliseAction extends Action {
	
	protected Location fertiliseTargetLocation;
	
	public FertiliseAction(Location fertiliseTargetLocation) {
		
	this.fertiliseTargetLocation = fertiliseTargetLocation;
		
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		Ground ground = fertiliseTargetLocation.getGround();
		if (ground instanceof Crop) {
			Crop crop = (Crop) ground;
			crop.fertilise();
		}
		else {
			throw new IllegalArgumentException("Attempt was made to fertilise a non-Crop Ground");
		}
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " fertilised some crops.";
	}

}