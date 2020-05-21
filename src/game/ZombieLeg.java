package game;

import edu.monash.fit2099.engine.WeaponItem;
/**
 * A zombie leg weapon. Can be crafted into a zombie mace.
 * 
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class ZombieLeg extends WeaponItem {

	public ZombieLeg() {
		super("Zombie Leg", '{', 15, "whacks");
		this.allowableActions.add(new CraftAction(this, new ZombieMace()));
	}
}
