package game;
import java.util.ArrayList;

import edu.monash.fit2099.engine.*;
import java.util.Random;

//A class that figures out what farming actions a Farmer can perform on a given turn.

public class FarmingBehaviour implements Behaviour{
	
	private Random rand = new Random();
	
	public Action getAction(Actor actor, GameMap map) {
		
		//Harvest, then fertilise, then sow
		ArrayList<Action> actions = new ArrayList<Action>();
		
		//Harvest added to actions array first
		if (map.locationOf(actor).getGround().hasCapability(CropCapability.HARVESTABLE)){
			actions.add(new HarvestAction(map.locationOf(actor)));
		}
		for (Exit exit : map.locationOf(actor).getExits()) {
            Location adjacentSquare = exit.getDestination();         
            
            if (adjacentSquare.getGround().hasCapability(CropCapability.HARVESTABLE)) {
            	actions.add(new HarvestAction(adjacentSquare));
            }
        }
		
		//Fertilise
		if (map.locationOf(actor).getGround().hasCapability(CropCapability.FERTILISABLE)){
			actions.add(new FertiliseAction(map.locationOf(actor)));
		}

		
		//Plant Crop
		for (Exit exit : map.locationOf(actor).getExits()) {
            Location adjacentSquare = exit.getDestination();
            if (adjacentSquare.getGround() instanceof Dirt) {
            	actions.add(new SowAction(adjacentSquare));
            }
        }
		
		//33% chance of sowing adjacent square
		if (actions.size() > 0) {
			if(actions.get(0) instanceof SowAction) {
				if (rand.nextInt(100)< 34) {
					return actions.get(0);
				}
				else {
					return null;
				}
			}
				
			return actions.get(0);
		}
		return null;
	}
}
