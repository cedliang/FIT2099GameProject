package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
/**
 * Class representing MamboMarie.
 * @author Cedric Liang, Nathan Vaughan
 *
 */
public class MamboMarie extends ZombieActor {

	private WanderBehaviour wanderBehaviour = new WanderBehaviour();
	private int turnCount = 0;	// Tracks the number of turns MamboMarie has played
	private int numberOfTimesChanted = 0;
	
	public MamboMarie(String name) {
		super(name, 'M', 100, ZombieCapability.UNDEAD);
	}

	public MamboMarie(String name, int hitPoints) {
		super(name, 'M', hitPoints, ZombieCapability.UNDEAD);
	}
	
	@Override
	public void takeDamage(int damage, GameMap map) {
		this.hurt(damage);
	}
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {		
		// Increment the turnCount each turn to track turns
		turnCount++;
		// If MamboMarie has played 30 turns, return VanishAction
		if (turnCount == 30) {
			//vanish
			return new VanishAction();
		}
		// Every 10 turns, return ChantAction
		if (turnCount % 10 == 0) {
			//chant
			numberOfTimesChanted++;
			return new ChantAction(numberOfTimesChanted);
		}
		//otherwise, wander or do nothing
		Action wanderAction = wanderBehaviour.getAction(this, map);
		if (wanderAction != null)
			return wanderAction;
		//do nothing
		return new DoNothingAction();
	}
	
	public int getNumberOfTimesChanted() {
		return numberOfTimesChanted;
	}
}
