package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class SelectTargetActorAction extends Action {

	private Actor target;
	private SniperRifle sniper;
	private Action actionSelected;
	
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();
	
	public SelectTargetActorAction(Actor targetSelected, SniperRifle gun, Action selected) {
		target = targetSelected;
		sniper = gun;
		actionSelected = selected;		
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		if (actionSelected instanceof AimSniperAction) {
			if (target != sniper.previousTarget) {
				actor.setConcentration(0);
			}
			sniper.chargeWeapon(actor, target);
			return actor.toString() + " aims at " + target.toString();
		}
		else if (actionSelected instanceof ShootSniperAction) {
			
			if (target != sniper.previousTarget) {
				actor.setConcentration(0);
			}
//			Gun.chargeWeapon(actor, Target);
			
			double probability = WeaponHitProb(actor); // gets probability of weapon for specified actor
			double randomNumber = rand.nextInt(100);
			// Actor misses their target if the random number is greater than the probability to hit
			if (randomNumber > probability) {
				return actor + " misses " + target + ".";
			}
			
			int damage = sniper.rangeDamage(actor);
			target.takeDamage(damage, map);
			
			String result = actor + " " + sniper.rangeVerb() + " " + target + " for " + damage + " damage.";
			
			
			if (!target.isConscious()) {
				result += System.lineSeparator();
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
				
				actor.setConcentration(0);
				
				result += target + " is killed.";
			}
			
			return result;
		}
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {

		return actor.toString() + " selects " + target.toString();
	}
	
	private double WeaponHitProb(Actor actor) {

		if (actor.getConcentration() == 0) {
			return 75;
		}
		else if (actor.getConcentration() == 1) {
			return 90;
		}

		return 100;



	}
}
