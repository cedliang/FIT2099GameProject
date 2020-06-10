package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

public class SelectShotgunDirectionAction extends Action {
	
	private Display display = new Display();
	private Menu menu = new Menu();
	private Shotgun shotgun;

	
	public SelectShotgunDirectionAction (Shotgun shotgun) {
		this.shotgun = shotgun;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		Actions actionsList  = new Actions();
		
		actionsList.add(new ShootShotgunAction(shotgun,"N"));
		actionsList.add(new ShootShotgunAction(shotgun,"S"));
		actionsList.add(new ShootShotgunAction(shotgun,"E"));
		actionsList.add(new ShootShotgunAction(shotgun,"W"));
		actionsList.add(new ShootShotgunAction(shotgun,"NE"));
		actionsList.add(new ShootShotgunAction(shotgun,"SE"));
		actionsList.add(new ShootShotgunAction(shotgun,"NW"));
		actionsList.add(new ShootShotgunAction(shotgun,"SW"));
		
		Action playerSelectedAction = menu.showMenu(actor, actionsList, display);
		return playerSelectedAction.execute(actor, map);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor.toString() + " blasts with shotgun.";
	}

}
