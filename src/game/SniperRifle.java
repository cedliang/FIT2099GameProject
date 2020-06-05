package game;

import edu.monash.fit2099.engine.Actor;

public class SniperRifle extends RangeWeaponItem {
	
	protected int chargeTime = 2;
	
	public SniperRifle() {
		super("Sniper Rifle", 'L', 10, "whacks", 30, "shoots");
		this.allowableActions.add(new AimSniperAction(this));
		this.allowableActions.add(new ShootSniperAction(this));
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
	
	public void chargeWeapon(Actor actor) {
		actor.setConcentration(actor.getConcentration()+1);
	}

}
