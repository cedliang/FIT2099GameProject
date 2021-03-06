package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.GameMap;

/**
 * This interface provides the ability to add methods to Actor, without modifying code in the engine,
 * or downcasting references in the game.   
 */

public interface ActorInterface {
	//Implement takeDamage(int damage, map map)
	public void takeDamage(int damage, GameMap map);
	public int getCurrentHitPoints();
	public void setConcentration(int concentration);
	public int getConcentration();
}