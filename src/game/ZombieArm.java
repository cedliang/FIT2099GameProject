package game;

import edu.monash.fit2099.engine.WeaponItem;
/**
 * ??
 * 
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class ZombieArm extends WeaponItem {

	public ZombieArm() {
		super("Zombie Arm", '}', 15, "whacks");
		this.allowableActions.add(new CraftAction(this, new ZombieClub()));
	}
}
