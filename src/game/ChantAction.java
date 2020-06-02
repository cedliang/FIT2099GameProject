package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.NumberRange;

public class ChantAction extends Action {
	
	protected Random random = new Random();
	protected int xValue;
	protected int yValue;
	
	@Override
	public String execute(Actor actor, GameMap map) {
		String[] zombies = {"Peckish", "Bitemark", "Nib", "Gobbles", "Bilk"};
		for (String zombieName : zombies) {
			setXY(map);
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
	
	private void setXY(GameMap map) {
		NumberRange width = map.getXRange();
		NumberRange height = map.getYRange();
		xValue = random.nextInt(width.max());
		yValue = random.nextInt(height.max());
	}
}
