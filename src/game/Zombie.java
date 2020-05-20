package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * A Zombie.
 * 
 * Zombies have two intrinsic weapons they can choose from and now have a new behaviour that
 * lets them pick up a WeaponItem at their location if they currently do not have a WeaponItem.
 * They can also lose their limbs upon being attacked which will affect the way the Zombie behaves.
 * 
 * @author ram, Cedric Liang, Nathan Vaughan
 *
 */
public class Zombie extends ZombieActor {
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntActorBehaviour(Human.class, 10),
			new WanderBehaviour()
	};
	
	private int numberOfArms = 2;	// Number of arms the Zombie has
	private int numberOfLegs = 2;	// Number of legs the Zombie has
	
	private Random rand = new Random();

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
	}
	
	/**
	 * The Zombie has two intrinsic weapons, ZombieBite and ZombiePunch. To determine which one
	 * should be used is dependent on the number of arms the Zombie has. 
	 * 
	 * @return a newly created intrinsic weapon
	 */
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		// If the Zombie has 2 arms, the probability of choosing ZombiePunch is 50%
		if (this.numberOfArms == 2) {
			int probability = rand.nextInt(100);
			if (probability < 50) {
				return new ZombiePunch();
			}
		}
		// If the Zombie has 1 arm, the probability of choosing ZombiePunch is 25%
		else if (this.numberOfArms == 1) {
			int probability = rand.nextInt(100);
			if (probability < 25) {
				return new ZombiePunch();
			}
		}
		// If the Zombie has 2 arms, the probability of choosing ZombieBite is 50%
		// If the Zombie has 1 arm, the probability of choosing ZombieBite is 75%
		// If the Zombie has 0 arms, the probability of choosing ZombieBite is 100%
		return new ZombieBite();
	}

	/**
	 * If there is a WeaponItem at the location of the Zombie, if the Zombie does not currently have
	 * a weapon in their inventory and has at least 1 arm, then they must pick up the WeaponItem.
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {		
		// Zombies have a 10% chance to say "Braaaaaains" during their turn and
		// this does not use up their turn.
		int probability = rand.nextInt(100);
		if (probability < 10) {
			display.println("Braaaaaaaaaaaaaaiinsssss");
			return new DoNothingAction();
		}
		
		// Solves the problem of a Zombie picking up all of the weapons.
		// Sets a flag (weaponInInventory) if the Zombie has a WeaponItem in its inventory.
		boolean weaponInInventory = false;
		for (Item item : this.getInventory()) {
			if (item instanceof WeaponItem) {
				weaponInInventory = true;
			}
		}
		
		// If the Zombie has atleast 1 arm then it should pick up a weapon
		// at it's location if a weapon is present.
		// Zombie only picks up 1 WeaponItem if they do not currently have a weapon.
		if (this.numberOfArms > 0 && !weaponInInventory) {
			Location locationOfZombie = map.locationOf(this);
			for (Item item : locationOfZombie.getItems()) {
				if (item instanceof WeaponItem) {
					return new PickUpItemAction(item);
				}
			}
		}
		
		// If the Zombie has 1 leg, then they can only move every 2 turns.
		// This reduces the speed of the zombie's movement in half disallowing them from
		// moving in their current turn if they moved in the previous turn.
		// The Zombie can only choose an action from the AttackBehaviour.
		if (this.numberOfLegs == 1) {
			if (lastAction instanceof MoveActorAction) {
				Action action = behaviours[0].getAction(this, map);
				if (action != null) {
					return action;
				}
				return new DoNothingAction();
			}
		}
		
		// If the Zombie has atleast 1 leg, then they can choose from all of their behaviours.
		if (this.numberOfLegs > 0) {
			for (Behaviour behaviour : behaviours) {
				Action action = behaviour.getAction(this, map);
				if (action != null)
					return action;
			}
		}
		
		// If the Zombie has no legs, then they can only get actions from the AttackBehaviour.
		if (this.numberOfLegs == 0) {
			Action action = behaviours[0].getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		
		// If the Zombie has no actions to do, then it does nothing.
		return new DoNothingAction();	
	}

	/**
	 * If a Zombie takes damage, they lose an amount of hit points (damage taken) and 
	 * have the possibility of losing at least 1 of its limbs. 
	 * 
	 * @param damage amount of hit points the Zombie takes
	 * @param map the map where the current Zombie is
	 */
	@Override
	public void takeDamage(int damage, GameMap map) {
		// Zombie loses hp (damage taken)
		this.hurt(damage);

		double prob = rand.nextDouble(); 	// A value between 0.0 (inc) and 1.0 (exc).
		double probToTakeLimbOff = 0.3;		// Probability for a limb to fall off.
		int numberOfLimbs = (this.numberOfArms + this.numberOfLegs);
		
		if (numberOfLimbs > 0) {
			if (prob < Math.pow(probToTakeLimbOff, 10)) {
				// All limbs fall off with a probToTakeLimbOff^4 probability.
				for (int i = 0; i < 4; i++) {
					this.limbFallOff(map);
				}
			}
			else if (prob < Math.pow(probToTakeLimbOff, 9)) {
				// 3 limbs fall off with a probToTakeLimbOff^3 probability.
				for (int i = 0; i < 3; i++) {
					this.limbFallOff(map);
				}
			}
			else if (prob < Math.pow(probToTakeLimbOff, 8)) {
				// 2 limbs fall off with a probToTakeLimbOff^2 probability.
				for (int i = 0; i < 2; i++) {
					this.limbFallOff(map);
				}
			}
			else if (prob < probToTakeLimbOff){
				// 1 limb falls off with a probToTakeLimbOff probability.
				this.limbFallOff(map);
			}
		}
	}
	
	/**
	 * Knocks a limb off a Zombie.
	 * 
	 * @param map the map where the current Zombie is
	 */
	private void limbFallOff(GameMap map) {
		// If a limb is taken off the Zombie, reduce the number of arms/legs it has
		// and create the specified limb weapon item at the location of the Zombie.
		
		// Location of the Zombie on the map.
		Location location = map.locationOf(this); 
		
		// If the Zombie has atleast a leg and arm, then randomly choose which to take off.
		if (this.numberOfArms > 0 && this.numberOfLegs > 0) {
			int probLimbs = rand.nextInt(2);
			if (probLimbs == 0) {
				this.numberOfArms--;
				map.at(location.x(), location.y()).addItem(new ZombieArm());
				dropWeapon(map);
			}
			else {
				this.numberOfLegs--;
				map.at(location.x(), location.y()).addItem(new ZombieLeg());
			}
		// If the Zombie only has atleast an arm, then take off the arm.
		}
		else if (this.numberOfArms > 0 && this.numberOfLegs == 0) {
			this.numberOfArms--;
			map.at(location.x(), location.y()).addItem(new ZombieArm());
			dropWeapon(map);
		}
		// If the Zombie only has atleast a leg, then take off the leg.
		else if (this.numberOfArms == 0 && this.numberOfLegs > 0) {
			this.numberOfLegs--;
			map.at(location.x(), location.y()).addItem(new ZombieLeg());
		}
	}

	
	/**
	 * If the Zombie is holding a WeaponItem and at least 1 of its arms are knocked
	 * off, then the Zombie has a possibility of dropping the WeaponItem it is holding.
	 * 
	 * @param map the map where the current Zombie is
	 */
	private void dropWeapon(GameMap map) {
		// Logic for zombies to drop their weapons if their arms are knocked off.
		// Assuming zombies can only have 1 weapon at a time.
		if (this.numberOfArms == 1) {
			// Drop weapon with 50% probability
			int randomNumber = rand.nextInt(2);
			if (randomNumber == 0) {
				for (Item item : this.getInventory()) {
					if (item instanceof WeaponItem) {
						DropItemAction dropItem = new DropItemAction(item);
						dropItem.execute(this, map);
						return;
					}
				}
			}
		}
		else if (this.numberOfArms == 0) {
			// Drop weapon with 100% probability
			for (Item item : this.getInventory()) {
				if (item instanceof WeaponItem) {
					DropItemAction dropItem = new DropItemAction(item);
					dropItem.execute(this, map);
					return;
				}
			}
		}
	}
}
