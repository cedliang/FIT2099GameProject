package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;

public class MamboMarie extends ZombieActor {

	private WanderBehaviour wanderBehaviour = new WanderBehaviour();
	private int turnCount = 0;
	
	public MamboMarie(String name) {
		super(name, 'M', 100, ZombieCapability.ALIVE);
	}

	@Override
	public void takeDamage(int damage, GameMap map) {
		this.hurt(damage);
	}
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// If she is not on the map, she has a 5% chance per turn of appearing
		// She starts at the edge of the map and wanders randomly.
		// Every 10 turns, she will stop and spend a turn chanting
		// This will cause five new zombies to appear in random locations on the map.
		// If she is not killed, she will vanish after 30 turns.
		// Mambo Marie will keep coming back until she is killed.
		
		turnCount++;
		if (turnCount == 30) {
			//vanish
			return new VanishAction();
		}
		if (turnCount % 10 == 0) {
			//chant
			return new ChantAction();
		}
		//wander
		Action wanderAction = wanderBehaviour.getAction(this, map);
		if (wanderAction != null)
			return wanderAction;
		//do nothing
		return new DoNothingAction();
	}
}
