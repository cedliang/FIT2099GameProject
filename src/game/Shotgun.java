package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Item;

public class Shotgun extends RangeWeaponItem {
	public Shotgun() {
		super("Shotgun", 'i',10,"bludgeons",25,"blasts");

	}

	
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
