package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.NumberRange;
import edu.monash.fit2099.engine.WeaponItem;

public class AimSniperAction extends Action {
	
	private Menu menu = new Menu();
	private RangeWeaponItem Gun;
	private Display display = new Display();
	
	public AimSniperAction(RangeWeaponItem gun) {
		Gun = gun;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		Actions list = new Actions();
		
		NumberRange Xrange = map.getXRange();
		NumberRange Yrange = map.getYRange();
		
		for (int x = Xrange.min(); x < Xrange.max(); x++) {
			for (int y = Yrange.min(); y < Yrange.max(); y++) {
				Location location = map.at(x, y);
				Actor target = map.getActorAt(location);
				if (target == null) {
					continue;
				}
				if (target.hasCapability(ZombieCapability.UNDEAD)) {
					list.add(new SelectAction(target, Gun, this));
				}
			}
		}
		if (list.size() != 0) {
			Action playerSelectedAction = menu.showMenu(actor, list, display);
			return playerSelectedAction.execute(actor, map);
		}
		
		return (new DoNothingAction()).execute(actor, map);	
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor.toString() + " aims with " + Gun.toString();
	}

}
