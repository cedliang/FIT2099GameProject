package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * A special Human that has access to FarmingBehaviour that allows the Farmer
 * to interact with Crop tiles.
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class Farmer extends Human {
	
	//farmer specific behaviour
	private FarmingBehaviour farmingBehaviour = new FarmingBehaviour();
	private HuntItemBehaviour findFood = new HuntItemBehaviour(Food.class,5);
	private HuntGroundBehaviour findCrop = new HuntGroundBehaviour(Crop.class,10);
	private WanderBehaviour wanderBehaviour = new WanderBehaviour();
	
	/**
	 * Constructor
	 * 
	 * @param name Name of the farmer
	 */
	public Farmer(String name) {
		super(name, 'F', 50);
	}
	
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

		if (hitPoints < maxHitPoints) {			
			//highest priority: eating food if low HP	
			for (Item item : getInventory()) {
				if (item instanceof Food) {
					Food food = (Food) item;
					return new EatFoodAction(food);
				}
			}
			
			//next priority: picks up food on current square if low HP. Farmer is generous (or getting paid a lot),
			//doesn't pick up the food he harvests himself
			for (Item item : map.locationOf(this).getItems()) {
				if (item instanceof Food) {
					return new PickUpItemAction(item);
				}
			}
			
			//if farmer is not full HP, third priority is finding food, but only if there is food nearby
			Action action = findFood.getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		
		//farmer performs farming action
		Action action1 = farmingBehaviour.getAction(this, map);
		if (action1 != null) {
			return action1;
		}

		//if the farmer decides not to perform farming action (for example, no dirt around or decides not to sow),
		//they move towards the nearest crop (to fertilise, harvest, etc)
		
		//This way, Farmer preferentially performs actions near where existing crops are, causing the formation of 
		//patches of farmland. Humans are drawn to these patches since food is produced here, so we see them form
		//small groups
		Action action2 = findCrop.getAction(this, map);
		if (action2 != null) {
			return action2;
		}		
		
		//If all this fails, wander
		Action action3 = wanderBehaviour.getAction(this, map);
		if (action3 != null) {
			return action3;
		}
		
		return new DoNothingAction();
	
	}

}
