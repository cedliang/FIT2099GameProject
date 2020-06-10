package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Item;

/**
 * Shotgun ranged weapon. Consumes ammo and deals area of effect damage at short range.
 * 
 * @author Cedric Liang, Nathan Vaughan
 */
public class Shotgun extends RangeWeaponItem {
	/**
	 * Constructs a Shotgun
	 * 
	 */
	public Shotgun() {
		super("Shotgun", 'i',10,"bludgeons",25,"blasts");

	}
	
	//if Actor holding weapon has ammo, allows actor to shoot the weapon on the next turn
	@Override
	public void tick(Location location, Actor actor) {
		this.allowableActions = new Actions();
		boolean hasAmmo = false;
		for (Item i : actor.getInventory()) {
			if (i instanceof Ammo) {
				hasAmmo = true;
			}
		}
		if (hasAmmo) {
		allowableActions.add(new SelectShotgunDirectionAction(this));
		}
	}
	
	
	
	
}
