package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponItem;

public class RangeWeaponItem extends WeaponItem {
	
	protected String rangeVerb;
	protected int rangeDamage;
	protected Actor previousTarget;
	
	public RangeWeaponItem(String name, char displayChar, int damage, String verb, int shootingDamage, String shootingVerb) {
		super(name, displayChar, damage, "whacks");
		this.rangeDamage = shootingDamage;
		this.rangeVerb = shootingVerb;
	}
	
	public int rangeDamage(Actor actor) {
		return this.rangeDamage;
	}
	
	public String rangeVerb() {
		return this.rangeVerb;
	}
	
	public void chargeWeapon(Actor actor, Actor target) {
		actor.setConcentration(actor.getConcentration()+1);
		this.previousTarget = target;
	}

}
