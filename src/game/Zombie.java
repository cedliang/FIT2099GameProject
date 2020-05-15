package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};
	
	private int numberOfArms = 2;	// Number of arms the zombie has
	private int numberOfLegs = 2;	// Number of legs the zombie has
	
	protected Random rand = new Random();

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
	}
	
	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		// If the zombie has 2 arms, the probability of choosing ZombiePunch is 50%
		if (this.numberOfArms == 2) {
			int probability = rand.nextInt(100);
			if (probability < 50) {
				return new ZombiePunch();
			}
		}
		// If the zombie has 1 arm, the probability of choosing ZombiePunch is 25%
		else if (this.numberOfArms == 1) {
			int probability = rand.nextInt(100);
			if (probability < 25) {
				return new ZombiePunch();
			}
		}
		// If the zombie has 2 arms, the probability of choosing ZombieBite is 50%
		// If the zombie has 1 arm, the probability of choosing ZombieBite is 75%
		return new ZombieBite();
	}

	/**
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
		//TODO: May need to add a PickUpBehaviour - Currently not checking if a zombie has a weapon in their inventory, so
		// we need to check if the zombie has a weapon in their inventory, and if they do then we do not allow them to pick up another
		// weapon, otherwise it will be very cluttered with all of the limbs falling off at their location and they continuously pick up
		// their own limbs as they fall off.
		
		//We can hardcode in the behaviours instead of running it over a loop. This lets us handle the checks for movement behviours separately to handling the 
		//attack behaviours. Then, we can add the possibility for huntbehaviour or wanderbehaviour to return null (skipping it)
		// if we query lastAction
		
		// Zombies have a 10% chance to say "Braaaaaains" during their turn
		// This does not use up their turn
		int probability = rand.nextInt(100);
		if (probability < 10) {
			display.println("Braaaaaaaaaaaaaaiinsssss");
		}
		
		// If the zombie has atleast 1 arm then it should pick up a weapon
		// at it's location if a weapon is present.
		// Still have to identify how many items the zombie can pick up, right now
		// the zombie HAS to pick up all of the items at their location before they
		// can use a different action, limiting the zombies ability to attack or move
		if (this.numberOfArms > 0) {
			for (Action action : actions) { // return first action that results in a pickupitemaction
				if (action instanceof PickUpItemAction) {
					return action;
				}
			}
		}
		
		// If the zombie has 1 leg, then they can only move every 2 turns
		// This reduces the speed of the zombie's movement in half disallowing them from
		// moving in their current turn if they moved in the previous turn
		if (this.numberOfLegs == 1) {
			if (lastAction instanceof MoveActorAction) {
				Action action = behaviours[0].getAction(this, map);
				if (action != null) {
					return action;
				}
				return new DoNothingAction();
			}
		}
		
		// If the zombie has atleast 1 leg, then they can choose from all of their behaviours
		if (this.numberOfLegs > 0) {
			for (Behaviour behaviour : behaviours) {
				Action action = behaviour.getAction(this, map);
				if (action != null)
					return action;
			}
		}
		
		// If the zombie has no legs, then they can only get actions from the AttackBehaviour
		if (this.numberOfLegs == 0) {
			Action action = behaviours[0].getAction(this, map);
			if (action != null) {
				return action;
			}
		}
		
		// If the zombie has no actions to do, then it does nothing
		return new DoNothingAction();	
	}


	@Override
	public void takeDamage(int damage, GameMap map) {
		//TODO:Still have to implement dropping of weapons when limbs fall off
		// When a zombie takes damage, there is a probability that the zombie will
		// lose at least 1 limb.
		this.hurt(damage);
		double prob = rand.nextDouble(); // A value between 0.0 (inc) and 1.0 (exc)
		double probToTakeLimbOff = 0.25; // probability for a limb to fall off
		int numberOfLimbs = (this.numberOfArms + this.numberOfLegs);
		if (numberOfLimbs > 0) {
			if (prob < Math.pow(probToTakeLimbOff, 4)) {
				// All limbs fall off with a probToTakeLimbOff^4 probability
				for (int i = 0; i < 4; i++) {
					this.limbFallOff(map);
				}
			}
			else if (prob < Math.pow(probToTakeLimbOff, 3)) {
				// 3 limbs fall off with a probToTakeLimbOff^3 probability
				for (int i = 0; i < 3; i++) {
					this.limbFallOff(map);
				}
			}
			else if (prob < Math.pow(probToTakeLimbOff, 2)) {
				// 2 limbs fall off with a probToTakeLimbOff^2 probability
				for (int i = 0; i < 2; i++) {
					this.limbFallOff(map);
				}
			}
			else if (prob < probToTakeLimbOff){
				// 1 limb falls off with a probToTakeLimbOff probability
				this.limbFallOff(map);
			}
		}
	}

	private void limbFallOff(GameMap map) {
		// TODO: Add logic to dropping weapons when arms fall off
		// If a limb is taken off the zombie, reduce the number of arms/legs it has
		// and create the specified limb weapon item at the location of the zombie
		int prevNumberOfLimbs = (this.numberOfArms + this.numberOfLegs); // This counter represents the previous count of limbs
		Location location = map.locationOf(this); // Location of the zombie on the map
		// When a zombie loses a limb, then this loop ends, ensuring that a limb was lost
		while (prevNumberOfLimbs == (this.numberOfArms + this.numberOfLegs)) {
			// If the zombie has atleast a leg and arm, then randomly choose which to take off
			if (this.numberOfArms > 0 && this.numberOfLegs > 0) {
				int probLimbs = rand.nextInt(2);
				if (probLimbs == 0) {
					this.numberOfArms--;
					map.at(location.x(), location.y()).addItem(new ZombieArm());
				}
				else {
					this.numberOfLegs--;
					map.at(location.x(), location.y()).addItem(new ZombieLeg());
				}
			// If the zombie only has atleast an arm, then take off the arm
			}
			else if (this.numberOfArms > 0 && this.numberOfLegs == 0) {
				this.numberOfArms--;
				map.at(location.x(), location.y()).addItem(new ZombieArm());
			}
			// If the zombie only has atleast a leg, then take off the leg
			else if (this.numberOfArms == 0 && this.numberOfLegs > 0) {
				this.numberOfLegs--;
				map.at(location.x(), location.y()).addItem(new ZombieLeg());
			}
		}
	}
}