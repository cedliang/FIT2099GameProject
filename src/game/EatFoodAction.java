package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;


public class EatFoodAction extends Action {

	private Food food;
	public EatFoodAction(Food food) {
		this.food = food;
	}
	

	@Override
	public String execute(Actor actor, GameMap map) {
		actor.removeItemFromInventory(food);
		actor.heal(food.getHealFactor());
		return (actor + " ate some food and restored some HP. "+ actor+" is now on "+Integer.toString(actor.getCurrentHitPoints())+" HP.");
	}

	@Override
	public String menuDescription(Actor actor) {
		return (actor + " eats some food");
	}

}
