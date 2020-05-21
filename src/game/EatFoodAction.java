package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * An Action that heals the Actor that calls this method and removes the consumed Food from the map
 * or Actor inventory.
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class EatFoodAction extends Action {

	private Food food;
	
	/**
	 * Constructor
	 *
	 * @param food The food object to be consumed
	 */
	public EatFoodAction(Food food) {
		this.food = food;
	}
	

	@Override
	public String execute(Actor actor, GameMap map) {
		actor.heal(food.getHealFactor());
		
		
		//logic to make sure that the food that is consumed corresponds to the food that is removed from the inventory or map.
		
		if (map.locationOf(actor).getItems().contains(food)) {
			map.locationOf(actor).removeItem(food);
		}
		
		else if ((actor.getInventory().contains(food))) {
			actor.removeItemFromInventory(food);
		}
		
		else {
			throw new IllegalArgumentException("Food object to be consumed is neither on the actor's tile or in actor's inventory.");
		}


		return (actor + " ate some food and restored some HP. "+ actor+" is now on "+Integer.toString(actor.getCurrentHitPoints())+" HP.");
	}

	@Override
	public String menuDescription(Actor actor) {
		if((actor.getInventory().contains(food))) {
			return (actor + " eats some food (from inventory)");
		}
		return (actor + " eats some food (from the ground)");
	}

}
