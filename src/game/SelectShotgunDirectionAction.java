package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * Allows the player to select a direction for shooting the shotgun. Calls an instance of ShootShotgunAction with the selected direction.
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class SelectShotgunDirectionAction extends Action {
	
	private Display display = new Display();
	private Menu menu = new Menu();
	private Shotgun shotgun;

	/**
	 * Constructs a SelectShotgunDirectionAction
	 * 
	 * @param shotgun the shotgun that is shooting
	 *
	 */
	public SelectShotgunDirectionAction (Shotgun shotgun) {
		this.shotgun = shotgun;
	}
	
	//precondition: shotgun is in the inventory, and the same actor also has ammo
	@Override
	public String execute(Actor actor, GameMap map) {
		Actions actionsList  = new Actions();
		
		String[] directionArray = {"N","S","E","W","NE","SE","NW","SW"};
		
		for (String i : directionArray) {
			actionsList.add(new ShootShotgunAction(shotgun,i));
		}

		Action playerSelectedAction = menu.showMenu(actor, actionsList, display);
		return playerSelectedAction.execute(actor, map);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor.toString() + " blasts with shotgun.";
	}

}
