package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * A special type of WeaponItem that can be used to deal damage at range when the Actor
 * has Ammo in the inventory (but can also be used as a blunt instrument in close quarters)
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class RangeWeaponItem extends WeaponItem {
	
	protected String rangeVerb;
	protected int rangeDamage;
	protected Actor previousTarget;
	
	/**
	 * Constructs a RangeWeaponItem
	 * 
	 * @param shootingDamage the amount of damage the weapon deals for a ranged attack
	 * @param shootingVerb the verb that is displayed when the weapon is shot
	 *
	 */
	public RangeWeaponItem(String name, char displayChar, int damage, String verb, int shootingDamage, String shootingVerb) {
		super(name, displayChar, damage, "whacks");
		this.rangeDamage = shootingDamage;
		this.rangeVerb = shootingVerb;
	}
	
	/**
	 * Returns the amount of damage the weapon deals upon shooting
	 * 
	 * @param actor the Actor who is dealing the range damage
	 * @return the amount of shooting damage the weapon deals
	 */
	public int rangeDamage(Actor actor) {
		return this.rangeDamage;
	}
	
	/**
	 * Returns the shooting verb of the weapon
	 * 
	 * @return the range verb of the weapon
	 */
	public String rangeVerb() {
		return this.rangeVerb;
	}
	

}
