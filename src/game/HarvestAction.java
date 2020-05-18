package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class HarvestAction extends Action {
	
	protected Location harvestTargetLocation;
	
	public HarvestAction(Location harvestTargetLocation) {
		
	this.harvestTargetLocation = harvestTargetLocation;
		
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		Dirt dirt = new Dirt();
		harvestTargetLocation.setGround(dirt);	
		harvestTargetLocation.addItem(new Food());
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " harvested some crops.";
	}

}