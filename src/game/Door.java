package game;

import edu.monash.fit2099.engine.Location;

/*
 * Special subclass of portal that is called 'vehicle' and has a V as a character
 */

public class Door extends Portal {
	public Door(Location location) {
		super(location);
		this.name="door";
		this.displayChar='_';
		this.verb = "enters";
	}
	
}
