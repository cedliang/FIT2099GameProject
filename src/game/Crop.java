package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * A class that represents Crop, a type of Ground that can be interacted with by Farmers and other Actors
 * to yield Food, a PortableItem that has beneficial effects to Human Actors.
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class Crop extends Ground {

	private int age;

	/**
	 * Constructor. By default, the Crop is created with the capability of being fertilised.
	 * 
	 */
	public Crop() {
		super('~');
		age=0;		
		addCapability(CropCapability.FERTILISABLE);
	}
	
	/**
	 * Fertilises the crop. Adds 10 to its age and removes its ability to be fertilised again.
	 *
	 */
	public void fertilise() {
		assert (hasCapability(CropCapability.FERTILISABLE)==true); 

		age += 10;
		//crop can only be fertilised once
		removeCapability(CropCapability.FERTILISABLE);
	}
	
	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age >= 20) {
			displayChar = '!';
			if (hasCapability(CropCapability.HARVESTABLE)==false) {
				addCapability(CropCapability.HARVESTABLE);
			if (hasCapability(CropCapability.FERTILISABLE)==true) {
				removeCapability(CropCapability.FERTILISABLE);
			}
			}
		}
	}
	@Override
	/**
	 * Permits the Actor to harvest
	 *
	 * @param actor the Actor acting
	 * @param location the current Location
	 * @param direction the direction of the Ground from the Actor
	 * @return a new, empty collection of Actions
	 */
	public Actions allowableActions(Actor actor, Location location, String direction){
		Actions actions = new Actions();
		if (hasCapability(CropCapability.HARVESTABLE)) {
			actions.add(new HarvestAction(location));
		}
		return actions;
	}

}
