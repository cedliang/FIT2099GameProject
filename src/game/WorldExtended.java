package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.NumberRange;
import edu.monash.fit2099.engine.World;

public class WorldExtended extends World {

	public WorldExtended(Display display) {
		super(display);
	}
	
	protected boolean stillRunning() {
		if (checkIfPlayerLoses(this.gameMaps.get(0))) {
			return false;
		}
		if (checkIfPlayerWins(this.gameMaps.get(0))) {
			return false;
		}
		return actorLocations.contains(player);
	}
	
	protected String endGameMessage() {
		if (checkIfPlayerLoses(this.gameMaps.get(0)) || !actorLocations.contains(player)) {
			return "Player Loses";
		}
		if (checkIfPlayerWins(this.gameMaps.get(0))) {
			return "Player Wins";
		}
		return "Game Over";
	}
	
	private boolean checkIfPlayerLoses(GameMap map) {
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		
		for (int x = 0; x <= xRange.max(); x++) {
			for (int y = 0; y <= yRange.max(); y++) {
				if (map.at(x, y).containsAnActor()) {
					Actor actor = map.getActorAt(map.at(x, y));
					if (actor.hasCapability(ZombieCapability.ALIVE) && (!(actor instanceof Player) && !(actor instanceof MamboMarie))) {
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
