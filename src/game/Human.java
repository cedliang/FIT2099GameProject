package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;


/**
 * Class representing an ordinary human.
 * 
 * 
 * @author ram
 *
 */
public class Human extends ZombieActor {
	private Behaviour behaviour = new WanderBehaviour();

	/**
	 * The default constructor creates default Humans
	 * 
	 * @param name the human's display name
	 */
	public Human(String name) {
		super(name, 'H', 50, ZombieCapability.ALIVE);
	}
	
	/**
	 * The protected constructor can be used to create subtypes
	 * of Human, such as the Player
	 * 
	 * @param name the human's display name
	 * @param displayChar character that will represent the Human in the map 
	 * @param hitPoints amount of damage that the Human can take before it dies
	 */
	protected Human(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints, ZombieCapability.ALIVE);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		if (hitPoints < maxHitPoints) {
			for (Item item : getInventory()) {
				if (item instanceof Food) {
					Food food = (Food) item;
					return new EatFoodAction(food);
				}
			}
		}
		
		//picks up food on current square
		for (Item item : map.locationOf(this).getItems()) {
			if (item instanceof Food) {
				return new PickUpItemAction(item);
			}
		}
		
		return behaviour.getAction(this, map);
	}

	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		return new HumanPunch();
	}
	
	@Override
	public void takeDamage(int damage, GameMap map) {
		this.hurt(damage);
	}

}
