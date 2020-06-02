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
	private GameMap starterMap;
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
	
	public Player(String name, char displayChar, int hitPoints, GameMap startingMap) {
		super(name, displayChar, hitPoints);
		this.starterMap = startingMap;
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		if (checkIfPlayerWins(starterMap)) {
			return new PlayerWinsLoses(checkIfPlayerWins(starterMap));
		}
		if (checkIfPlayerLoses(starterMap)) {
			return new PlayerWinsLoses(!checkIfPlayerLoses(starterMap));
		}
		actions.add(new QuitAction());
		return menu.showMenu(this, actions, display);
	}
	
	private boolean checkIfPlayerLoses(GameMap map) {
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		for (int x = 0; x <= xRange.max(); x++) {
			for (int y = 0; y <= yRange.max(); y++) {
				if (map.at(x, y).containsAnActor()) {
					Actor actor = map.getActorAt(map.at(x, y));
					if ((actor.hasCapability(ZombieCapability.ALIVE) && (actor != this && !(actor instanceof MamboMarie)))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean checkIfPlayerWins(GameMap map) {
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		for (int x = 0; x <= xRange.max(); x++) {
			for (int y = 0; y <= yRange.max(); y++) {
				for (Item item : map.at(x, y).getItems()) {
					if (item instanceof MamboMarieTracker) {
						return false;
					}
				}
				if (map.at(x, y).containsAnActor()) {
					Actor actor = map.getActorAt(map.at(x, y));
					if (actor.hasCapability(ZombieCapability.UNDEAD) || (actor instanceof MamboMarie)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
}
