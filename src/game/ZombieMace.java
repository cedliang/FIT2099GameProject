package game;

import edu.monash.fit2099.engine.WeaponItem;
/**
 * A zombie mace weapon. Can be crafted from ZombieLeg.
 * 
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class ZombieMace extends WeaponItem {

	public ZombieMace() {
		super("Zombie Mace", '[', 25, "whacks");
	}
}
