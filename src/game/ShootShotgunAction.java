package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import java.util.ArrayList;

public class ShootShotgunAction extends Action {
	Shotgun shotgun;
	String direction; 
	//direction is string "N", "S", "E", "W", "NE", "SE", "NW", "SW"
	public ShootShotgunAction(Shotgun shotgun,String direction) {
		this.shotgun = shotgun;
		this.direction = direction;
	}
	@Override
	public String execute(Actor actor, GameMap map) {

		ArrayList<Actor> affectedActors = new ArrayList<Actor>();
		for (Location l : affectedLocation(direction, actor, map)) {
			if (l.containsAnActor()) {
				if(Math.random()<0.75) {
					l.getActor().takeDamage(shotgun.rangeDamage, map);
					affectedActors.add(l.getActor());
				}
			}
		}
		if (affectedActors.size() > 0) {
			String returnString = actor.toString()+" blasted their shotgun and hit ";
			
			if (affectedActors.size()==1) {
				returnString += affectedActors.get(0).toString()+".";
			}
			else {
				for (int i=0; i<= affectedActors.size()-2;i++) {
					returnString += affectedActors.get(i).toString()+ " and ";
				}
				returnString += affectedActors.get(affectedActors.size()-1).toString()+".";
			}
			
			for  (Actor affectedActor : affectedActors) {
				
				if (!affectedActor.isConscious()) {
					Corpse corpse = new Corpse(affectedActor);
					map.locationOf(affectedActor).addItem(corpse);
					Actions dropActions = new Actions();
					for (Item item : affectedActor.getInventory())
						dropActions.add(item.getDropAction());
					for (Action drop : dropActions)		
						drop.execute(affectedActor, map);
					map.removeActor(affectedActor);	
					returnString += "\n"+affectedActor.toString() + " is killed.";
				}	
				else {
				returnString += "\n"+affectedActor.toString() +" is now on " +affectedActor.getCurrentHitPoints()+" HP.";
				}
			}

			return returnString;
		}
		return(actor.toString()+" blasted their shotgun but hit nobody.");
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor.toString() + " blasts with " + shotgun.toString();
	}
	
	
	
	
	
	//works out which Locations are hit by the spread
	private ArrayList<Location> affectedLocation(String direction, Actor actor, GameMap map){
		ArrayList<Location> affectedLocations = new ArrayList<Location>();
		
		//declare variable for clarity
		Location locationOfActor = map.locationOf(actor);
		
		if (direction == "N") {
			for (int ydiff = 1; ydiff <= 3; ydiff++) {
				for (int x = locationOfActor.x()-ydiff; x <= locationOfActor.x()+ydiff; x++ ) {
					int y = locationOfActor.y()-ydiff;
					if(onMap(x,y,map)) {
						affectedLocations.add(map.at(x, y));
					}
				}
			}		
		}
		
		if (direction == "S") {
			for (int ydiff = 1; ydiff <= 3; ydiff++) {
				for (int x = locationOfActor.x()-ydiff; x <= locationOfActor.x()+ydiff; x++ ) {
					int y = locationOfActor.y()+ydiff;
					if(onMap(x,y,map)) {
						affectedLocations.add(map.at(x, y));
					}
				}
			}		
		}
		
		if (direction == "E") {
			for (int xdiff = 1; xdiff <= 3; xdiff++) {
				for (int y = locationOfActor.y()-xdiff; y <= locationOfActor.y()+xdiff; y++ ) {
					int x = locationOfActor.x()+xdiff;
					if(onMap(x,y,map)) {
						affectedLocations.add(map.at(x, y));
					}
				}
			}		
		}
		
		if (direction == "W") {
			for (int xdiff = 1; xdiff <= 3; xdiff++) {
				for (int y = locationOfActor.y()-xdiff; y <= locationOfActor.y()+xdiff; y++ ) {
					int x = locationOfActor.x()-xdiff;
					if(onMap(x,y,map)) {
						affectedLocations.add(map.at(x, y));
					}
				}
			}		
		}
		
	
		
		if (direction == "NE") {
			//3x3 square up-right
			for (int x=locationOfActor.x()+1;x<=locationOfActor.x()+3; x++) {
				for (int y=locationOfActor.y()-3;y<=locationOfActor.y()-1;y++) {
					if(onMap(x,y,map)) {
						affectedLocations.add(map.at(x, y));
					}
				}
			}	
			//line up (negative y direction)
			for (int y=locationOfActor.y()-3;y<=locationOfActor.y()-1;y++) {
				if(onMap(locationOfActor.x(),y,map)) {
					affectedLocations.add(map.at(locationOfActor.x(), y));
				}
			}
			//line right (positive x direction)
			for (int x=locationOfActor.x()+1;x<=locationOfActor.x()+3;x++) {
				if(onMap(x,locationOfActor.y(),map)) {
					affectedLocations.add(map.at(x, locationOfActor.y()));
				}
			}
		}
		
		if (direction == "SE") {
			//3x3 square down-right
			for (int x=locationOfActor.x()+1;x<=locationOfActor.x()+3; x++) {
				for (int y=locationOfActor.y()+1;y<=locationOfActor.y()+3;y++) {
					if(onMap(x,y,map)) {
						affectedLocations.add(map.at(x, y));
					}
				}
			}	
			//line down (positive y direction)
			for (int y=locationOfActor.y()+1;y<=locationOfActor.y()+3;y++) {
				if(onMap(locationOfActor.x(),y,map)) {
					affectedLocations.add(map.at(locationOfActor.x(), y));
				}
			}
			//line right (positive x direction)
			for (int x=locationOfActor.x()+1;x<=locationOfActor.x()+3;x++) {
				if(onMap(x,locationOfActor.y(),map)) {
					affectedLocations.add(map.at(x, locationOfActor.y()));
				}
			}
		}


		if (direction == "NW") {
			//3x3 square up-left
			for (int x=locationOfActor.x()-3;x<=locationOfActor.x()-1; x++) {
				for (int y=locationOfActor.y()-3;y<=locationOfActor.y()-1;y++) {
					if(onMap(x,y,map)) {
						affectedLocations.add(map.at(x, y));
					}
				}
			}	
			//line up (negative y direction)
			for (int y=locationOfActor.y()-3;y<=locationOfActor.y()-1;y++) {
				if(onMap(locationOfActor.x(),y,map)) {
					affectedLocations.add(map.at(locationOfActor.x(), y));
				}
			}
			//line left (negative x direction)
			for (int x=locationOfActor.x()-3;x<=locationOfActor.x()-1;x++) {
				if(onMap(x,locationOfActor.y(),map)) {
					affectedLocations.add(map.at(x, locationOfActor.y()));
				}
			}
		}
			
		if (direction == "SW") {
			//3x3 square down-left
			for (int x=locationOfActor.x()-3;x<=locationOfActor.x()-1; x++) {
				for (int y=locationOfActor.y()+1;y<=locationOfActor.y()+3;y++) {
					if(onMap(x,y,map)) {
						affectedLocations.add(map.at(x, y));
					}
				}
			}	
			//line down (positive y direction)
			for (int y=locationOfActor.y()+1;y<=locationOfActor.y()+3;y++) {
				if(onMap(locationOfActor.x(),y,map)) {
					affectedLocations.add(map.at(locationOfActor.x(), y));
				}
			}
			//line left (negative x direction)
			for (int x=locationOfActor.x()-3;x<=locationOfActor.x()-1;x++) {
				if(onMap(x,locationOfActor.y(),map)) {
					affectedLocations.add(map.at(x, locationOfActor.y()));
				}
			}
		}
			
		return affectedLocations;		
		}
		
		
	
	//determines for an x and y value whether the x and y values correspond to a valid location on the map
	private boolean onMap(int x, int y, GameMap map) {
		if (map.getXRange().contains(x) && map.getYRange().contains(y)) {
			return true;
		}
		return false;
	}
	
	
	
}
