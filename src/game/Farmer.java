package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.DoNothingAction;

public class Farmer extends Human {
	
	//farmer specific behaviour
	private Behaviour[] behaviours = {
			new FarmingBehaviour()
	};
	
	public Farmer(String name) {
		super(name, 'F', 50);
	}
	
	
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action != null)
				return action;
		}
		return super.playTurn(actions, lastAction, map, display);
	}

}
