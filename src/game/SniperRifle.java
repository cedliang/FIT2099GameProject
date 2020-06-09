package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.NumberRange;

public class SniperRifle extends RangeWeaponItem {
	
	protected int chargeTime = 2;
	
	public SniperRifle() {
		super("Sniper Rifle", 'L', 10, "whacks", 30, "shoots");
	}
	
	public int rangeDamage(Actor actor) {
		System.out.println(actor.getConcentration());
		if (actor.getConcentration() >= chargeTime) {
			return 1000;
		}
		else if (actor.getConcentration() == 1) {
			return this.rangeDamage * 2;
		}
		return this.rangeDamage;
	}
	
	public void chargeWeapon(Actor actor, Actor target) {
		actor.setConcentration(actor.getConcentration()+1);
		this.previousTarget = target;
	}
	
	public void tick(Location location, Actor actor) {
		this.allowableActions = new Actions();
		if (actor.hasCapability(ZombieCapability.ALIVE) && checkIfEnemiesVisible(location)) {
			allowableActions.add(new AimSniperAction(this));
			allowableActions.add(new ShootSniperAction(this));
		}
	}
	
	private boolean checkIfEnemiesVisible(Location location) {
		GameMap map = location.map();
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		
		for (int x = 0; x < xRange.max(); x++) {
			for (int y = 0; y < yRange.max(); y++) {
				Actor actor = map.getActorAt(map.at(x, y));
				if (actor == null) {
					continue;
				}
				if (actor.hasCapability(ZombieCapability.UNDEAD)) {
					return true;
				}
			}
		}
		return false;
	}

}
