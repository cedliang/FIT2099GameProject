package game;

import edu.monash.fit2099.engine.Item;

/**
 * Base class for any item that can't be picked up and dropped.
 */
public class StationaryItem extends Item {

	public StationaryItem(String name, char displayChar) {
		super(name, displayChar, false);
	}
}
