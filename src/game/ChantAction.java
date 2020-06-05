package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.NumberRange;
/**
 * ChantAction allows the actor that executes it to spawn in 5 zombies and random locations on the map.
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class ChantAction extends Action {
	
	protected Random random = new Random();
	protected int xValue;
	protected int yValue;
	
	@Override
	public String execute(Actor actor, GameMap map) {
		String[] zombies = {"Peckish", "Bitemark", "Nib", "Gobbles", "Bilk"};
		// Iterates through the names in the zombies list and creates a new Zombie at a random location
		for (String zombieName : zombies) {
			setXY(map);
			// Checks if there is an actor at the current location or if an actor cannot enter that location
			while (map.at(xValue, yValue).containsAnActor() || !(map.at(xValue, yValue).getGround().canActorEnter(actor))) {
				setXY(map);
			}
			map.at(xValue, yValue).addActor(new Zombie(zombieName));
		}	
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " chants to spawn 5 zombies.";
	}
	
	/**
	 * Sets x and y values for the new random location
	 * @param map The map to get a random location on
	 */
	private void setXY(GameMap map) {
		NumberRange width = map.getXRange();
		NumberRange height = map.getYRange();
		xValue = random.nextInt(width.max());
		yValue = random.nextInt(height.max());
	}
}
