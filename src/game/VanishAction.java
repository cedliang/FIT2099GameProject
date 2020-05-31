package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.NumberRange;

public class VanishAction extends Action {
	
	protected Random rand = new Random();
	protected int xValue;
	protected int yValue;
	
	@Override
	public String execute(Actor actor, GameMap map) {
		
		setXY(map);
		
		Location location = map.at(xValue, yValue);

		while (map.isAnActorAt(location)) {
			setXY(map);
		}
		
		location.addItem(new MamboMarieTracker(actor, location));
		map.removeActor(actor);
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return actor + " vanishes from the map.";
	}
	
	private void setXY(GameMap map) {
		NumberRange width = map.getXRange();
		NumberRange height = map.getYRange();
		
		int randomNumber1 = rand.nextInt(4);
		
		if (randomNumber1 == 0) {
			this.xValue = width.min();
			this.yValue = rand.nextInt(height.max());
		}
		else if (randomNumber1 == 1) {
			this.xValue = width.max();
			this.yValue = rand.nextInt(height.max());
		}
		else if (randomNumber1 == 2) {
			this.xValue = rand.nextInt(width.max());
			this.yValue = height.min();
		}
		else {
			this.xValue = rand.nextInt(width.max());
			this.yValue = height.max();
		}
	}
	
}
