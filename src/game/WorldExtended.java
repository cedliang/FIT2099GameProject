package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.NumberRange;
import edu.monash.fit2099.engine.World;
/**
 * Extension of the World class. Improves the end game functionality.
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class WorldExtended extends World {
	/**
	 * Constructor for the extended World Class
	 * @param display
	 */
	public WorldExtended(Display display) {
		super(display);
	}
	
	@Override
	/**
	 * Returns true if the game is still running.
	 *
	 * Checks if the player has won or lost the game.
	 * If the player is still on the map and has not currently won or lost the game, then
	 * the game will continue to run until the Player has won or lost.
	 *
	 * @return true if the player is still on the map.
	 * @return false if the player has won or lost the game.
	 */
	protected boolean stillRunning() {
		if (checkIfPlayerLoses(this.gameMaps.get(0))) {
			return false;
		}
		if (checkIfPlayerWins(this.gameMaps.get(0))) {
			return false;
		}
		return actorLocations.contains(player);
	}
	
	@Override
	/**
	 * Return a string that can be displayed when the game ends.
	 *
	 * @return the string "Player Loses" if the player loses the game.
	 * @return the string "Player Wins" if the player wins the game.
	 * @return the string "Game Over" otherwise.
	 */
	protected String endGameMessage() {
		if (checkIfPlayerLoses(this.gameMaps.get(0)) || !actorLocations.contains(player)) {
			return "Player Loses";
		}
		if (checkIfPlayerWins(this.gameMaps.get(0))) {
			return "Player Wins";
		}
		return "Game Over";
	}
	
	/**
	 * checkIfPlayerLoses checks if there are no longer any humans alive.
	 * @param map the map to check loss condition on
	 * @return true if there are no humans alive
	 * @return false if there is at least 1 other human alive besides the player
	 */
	private boolean checkIfPlayerLoses(GameMap map) {
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		
		// Iterates through the entire map and checks each actor to see if they are a Human
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
	
	/**
	 * 
	 * @param map the map to check win condition on
	 * @return true if there are no hostile enemies alive
	 * @return false if there is at least 1 hostile enemy
	 */
	private boolean checkIfPlayerWins(GameMap map) {
		NumberRange xRange = map.getXRange();
		NumberRange yRange = map.getYRange();
		
		// Iterates through the entire map and checks each actor to see if they are a Zombie or Mambo Marie 
		for (int x = 0; x <= xRange.max(); x++) {
			for (int y = 0; y <= yRange.max(); y++) {
				for (Item item : map.at(x, y).getItems()) {
					// Checks if Mambo Marie is still alive but not on the map
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
