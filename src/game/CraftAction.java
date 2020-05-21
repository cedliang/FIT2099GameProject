package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * An Action that takes an instance of a parent Item and a child Item and performs the action of
 * removing the parent Item and adding the child Item to the game, depending on whether it was 
 * called on an Item on the Map or in an Actor's inventory.
 * 
 * Does not contain associations between parent and child, that needs to be fed in.
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class CraftAction extends Action {

	private Item parentItem;
	private Item childItem;
	
	/**
	 * Constructor
	 *
	 * @param parentItem the parent item that is to be removed from the game
	 * @param childItem the child item that is to be added to the game
	 */
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
