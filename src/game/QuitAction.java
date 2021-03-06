package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
/**
 * QuitAction allows the player to quit the game.
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class QuitAction extends Action {

	@Override
	public String execute(Actor actor, GameMap map) {
		map.removeActor(actor);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor.toString() + " quits the game.";
	}
	
	@Override
	public String hotkey() {
		return "`";
	}
}
