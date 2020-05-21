package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Action that harvests the Crop at a given Location. Ie, it changes the Ground back into Dirt
 * and adds Food to either the map (when called by NPCs) or the inventory of the Player.
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class HarvestAction extends Action {
	
	protected Location harvestTargetLocation;
	
	/**
	 * Constructor
	 *
	 * @param harvestTargetLocation the Location that contains the Crop to be harvested
	 */
	public HarvestAction(Location harvestTargetLocation) {
		
	this.harvestTargetLocation = harvestTargetLocation;
		
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		Dirt dirt = new Dirt();
		harvestTargetLocation.setGround(dirt);
		
		if (actor instanceof Player){
			actor.addItemToInventory(new Food());
		}
		else {
		harvestTargetLocation.addItem(new Food());
		}
		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " harvests some crops.";
	}

}