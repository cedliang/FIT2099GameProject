package game;

import edu.monash.fit2099.engine.Location;

/*
 * Special subclass of portal that is called 'vehicle' and has a V as a character
 */

public class Vehicle extends Portal {
	public Vehicle(Location location) {
		super(location);
		this.name="vehicle";
		this.displayChar='V';
	}
	
}
