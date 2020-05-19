package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Crop extends Ground {

	private int age;

	public Crop() {
		super('~');
		age=0;		
		addCapability(CropCapability.FERTILISABLE);
	}
	
	
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
			}
		}
	}

}
