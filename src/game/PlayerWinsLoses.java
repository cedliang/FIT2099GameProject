package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class PlayerWinsLoses extends Action {

	private boolean WinLoss;
	
	public PlayerWinsLoses(boolean Win) {
		this.WinLoss = Win;
	}
	
	@Override
	public String execute(Actor actor, GameMap map) {
		map.removeActor(actor);
		if (this.WinLoss) {
			return actor.toString() + " wins!";
		}
		return actor.toString() + " loses!";
	}

	@Override
	public String menuDescription(Actor actor) {
		return null;
	}
}
