package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.NumberRange;

/**
 * Class representing the Player.
 */
public class Player extends Human {

	private Menu menu = new Menu();
	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// If the Player uses an action that is not AimSniperAction, then concentration is reset
		if (!(lastAction instanceof AimSniperAction)) {
			this.concentration = 0;
		}
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		actions.add(new QuitAction());	// Added a QuitAction to the actions list
		return menu.showMenu(this, actions, display);
	}	
}
