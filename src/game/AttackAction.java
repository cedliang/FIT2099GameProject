package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		double probability = WeaponHitProb(weapon, actor); // gets probability of weapon for specified actor
		double randomNumber = rand.nextInt(100);
		// Actor misses their target if the random number is greater than the probability to hit
		if (randomNumber > probability) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		
		// When an actor is hurt, go through their specified taking damage logic
		target.takeDamage(damage, map);
		
		if (weapon instanceof ZombieBite) {	
			actor.heal(5);
			result += " "+actor +" is now on "+actor.getCurrentHitPoints()+" HP.";
		}	
		
		result += System.lineSeparator();
		
		if (!target.isConscious()) {
			Corpse corpse;
			if ((actor.hasCapability(ZombieCapability.UNDEAD)) && (target.hasCapability(ZombieCapability.ALIVE))){
				corpse = new InfectedCorpse(target);
			}
			else {
				corpse = new Corpse(target);
			}

			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);
			map.removeActor(target);	
			
			result += target + " is killed.";
		}
		
		else {
			result += target +" is now on " +target.getCurrentHitPoints()+" HP.";
		}
		
		// When the ZombieBite attack is used, heals the actor for 5 hp
	

		return result;
	}
	
	/**
	 * Returns the probability that a specific Weapon's hit probability.
	 * 
	 * @param weapon Weapon that is used for the attack
	 * @param actor the Actor that is attacking
	 * @return probability to hit the target with the Weapon
	 */
	private double WeaponHitProb(Weapon weapon, Actor actor) {
		double probability = 50;
		if (weapon instanceof ZombieArm) {
			probability = 40;
		}
		else if (weapon instanceof ZombieLeg) {
			probability = 40;
		}
		else if (weapon instanceof ZombieClub) {
			probability = 40;
		}
		else if (weapon instanceof ZombieMace) {
			probability = 40;
		}
		else if (weapon instanceof ZombieBite) {
			probability = 50;
		}
		else if (weapon instanceof ZombiePunch) {
			probability = 50;
		}
		else if (weapon instanceof HumanPunch) {
			probability = 50;
		}
		return probability;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}