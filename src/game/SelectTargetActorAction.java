package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class SelectTargetActorAction extends Action {

	private Actor Target;
	private SniperRifle sniper;
	private Action actionSelected;
	
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();
	
	public SelectTargetActorAction(Actor target, SniperRifle gun, Action selected) {
		Target = target;
		sniper = gun;
		actionSelected = selected;		
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		if (actionSelected instanceof AimSniperAction) {
			if (Target != sniper.previousTarget) {
				actor.setConcentration(0);
			}
			sniper.chargeWeapon(actor, Target);
			return actor.toString() + " aims at " + Target.toString();
		}
		else if (actionSelected instanceof ShootSniperAction) {
			
			if (Target != sniper.previousTarget) {
				actor.setConcentration(0);
			}
//			Gun.chargeWeapon(actor, Target);
			
			double probability = WeaponHitProb(actor); // gets probability of weapon for specified actor
			double randomNumber = rand.nextInt(100);
			// Actor misses their target if the random number is greater than the probability to hit
			if (randomNumber > probability) {
				return actor + " misses " + Target + ".";
			}
			
			int damage = sniper.rangeDamage(actor);
			Target.takeDamage(damage, map);
			
			String result = actor + " " + sniper.rangeVerb() + " " + Target + " for " + damage + " damage.";
			
			
			if (!Target.isConscious()) {
				result += System.lineSeparator();
				Corpse corpse;
				if ((actor.hasCapability(ZombieCapability.UNDEAD)) && (Target.hasCapability(ZombieCapability.ALIVE))){
					corpse = new InfectedCorpse(Target);
				}
				else {
					corpse = new Corpse(Target);
				}

				map.locationOf(Target).addItem(corpse);
				
				Actions dropActions = new Actions();
				for (Item item : Target.getInventory())
					dropActions.add(item.getDropAction());
				for (Action drop : dropActions)		
					drop.execute(Target, map);
				map.removeActor(Target);	
				
				actor.setConcentration(0);
				
				result += Target + " is killed.";
			}
			
			return result;
		}
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {

		return actor.toString() + " selects " + Target.toString();
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
