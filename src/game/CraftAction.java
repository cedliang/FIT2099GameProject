package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class CraftAction extends Action {

	private Item parentItem;
	private Item childItem;
	public CraftAction(Item parentItem, Item childItem) {
		this.parentItem = parentItem;
		this.childItem = childItem;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		
		if (map.locationOf(actor).getItems().contains(parentItem)) {
			map.locationOf(actor).removeItem(parentItem);
			map.locationOf(actor).addItem(childItem);
		}
		
		else if ((actor.getInventory().contains(parentItem))) {
			actor.removeItemFromInventory(parentItem);
			actor.addItemToInventory(childItem);
		}
		
		else {
			throw new IllegalArgumentException("Parent craft Item to be consumed is neither on the actor's tile or in actor's inventory.");
		}
		
		
		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return (actor+" crafts "+parentItem+" into "+childItem);
	}

}
