package game;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;


/**
 * The main class for the zombie apocalypse game.
 *
 */
public class Application {

	public static void main(String[] args) {
		WorldExtended world = new WorldExtended(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree());
		
		List<String> compoundmap = Arrays.asList(
		"................................................................................",
		"................................................................................",
		"....................................##########..................................",
		"..........................###########........#####..............................",
		"............++...........##......................########.......................",
		"..............++++.......#..............................##......................",
		".............+++...+++...#...............................#......................",
		".........................##..............................##.....................",
		"..........................#...............................#.....................",
		".........................##...............................##....................",
		".........................#...............................##.....................",
		".........................###..............................##....................",
		"...........................####....................########.....................",
		"..............................#########.........####............................",
		"............+++.......................#.........#...............................",
		".............+++++....................#.........#...............................",
		"...............++.....................######.####........+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap compound = new GameMap(groundFactory, compoundmap );
		world.addGameMap(compound);
		
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, compound.at(42, 15));
		
	    // Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (compound.at(x, y).containsAnActor());
			//20% chance that a human is a farmer
			if (Math.random()>0.8) {
				compound.at(x,  y).addActor(new Farmer(name));	
			}
			else {
			compound.at(x,  y).addActor(new Human(name));
			}
		}
		
		
		// place a simple weapon
		compound.at(74, 20).addItem(new Plank());
		// FIXME: Add more zombies!
		compound.at(30, 20).addActor(new Zombie("Groan"));
		compound.at(30,  18).addActor(new Zombie("Boo"));
		compound.at(10,  4).addActor(new Zombie("Uuuurgh"));
		compound.at(50, 18).addActor(new Zombie("Mortalis"));
		compound.at(1, 10).addActor(new Zombie("Gaaaah"));
		compound.at(62, 12).addActor(new Zombie("Aaargh"));	
		
		compound.at(0, 0).addActor(new MamboMarie("Mambo Marie"));
		
		compound.at(43,15).addItem(new Shotgun());
		
		//create town
		List<String> townmap = Arrays.asList(
				"................................................................................",
				"................................................................................",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............######.######.......######.######.......######.######...............",
				"...........+.............+.....+.............+.....+.............+..............",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"...........+.............+.....+.............+.....+.............+..............",
				"............######.######.......######.######.......######.######...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"................................................................................",
				".............................................................+++................",
				".............................++++............................+++++++............",
				"................................................................++..+...........",
				".............++++......................++++.....................................",
				"..............++................................................................",
				".............+...............++.................................................",
				"................................................................................"
				);
		GameMap town = new GameMap(groundFactory, townmap );
		world.addGameMap(town);
		
		//portal to town
		compound.at(44, 17).addItem(new Vehicle(town.at(1, 12)));
		//portal from town
		town.at(1, 12).addItem(new Vehicle(compound.at(44, 17)));
		
		//sniper located far from vehicle, can go for it to have a guaranteed chance or can search buildings for chance of a sniper rifle
		town.at(77, 9).addItem(new SniperRifle());

		//houses
		//doors are house0(18,6), house1(18,12), house2(38,6), house3(38,12), house4(58,6), house5(58,12)
		List<String> houseup= Arrays.asList(
				"######.######",
				"#...........#",
				"#...........#",
				"#...........#",
				"#############"
				);
		List<String> housedown= Arrays.asList(
				"#############",
				"#...........#",
				"#...........#",
				"#...........#",
				"######.######"
				);
		
		ArrayList<GameMap> houses = new ArrayList<GameMap>();
		GameMap house0 = new GameMap(groundFactory, housedown );
		houses.add(house0);
		GameMap house1 = new GameMap(groundFactory, houseup );
		houses.add(house1);
		GameMap house2 = new GameMap(groundFactory, housedown );
		houses.add(house2);
		GameMap house3 = new GameMap(groundFactory, houseup );
		houses.add(house3);
		GameMap house4 = new GameMap(groundFactory, housedown );
		houses.add(house4);
		GameMap house5 = new GameMap(groundFactory, houseup );
		houses.add(house5);
		
		//50% chance of human being in a house, 50% chance of 3 pieces of food
		for (GameMap house : houses) {
			world.addGameMap(house);
			
			if (Math.random()>0.5) {
					house.at(6, 2).addActor(new Human("Bob the Townsman"));	
				}
			else {
					for (int i=0; i<3; i++) {
						house.at(6, 2).addItem(new Food());
				}
					
			//33% chance of a house also containing a sniper rifle
			if (Math.random()<0.33) {
				house.at(1,1).addItem(new SniperRifle());
			}
					
			}
		}
		
		//doors to houses
		town.at(18, 6).addItem(new Door(house0.at(6, 4)));
		house0.at(6, 4).addItem(new Door(town.at(18, 6)));
		
		town.at(18, 12).addItem(new Door(house1.at(6, 0)));
		house1.at(6, 0).addItem(new Door(town.at(18, 12)));

		town.at(38, 6).addItem(new Door(house2.at(6, 4)));
		house2.at(6, 4).addItem(new Door(town.at(38, 6)));
		
		town.at(38, 12).addItem(new Door(house3.at(6, 0)));
		house3.at(6, 0).addItem(new Door(town.at(38, 12)));
		
		town.at(58, 6).addItem(new Door(house4.at(6, 4)));
		house4.at(6, 4).addItem(new Door(town.at(58, 6)));
		
		town.at(58, 12).addItem(new Door(house5.at(6, 0)));
		house5.at(6, 0).addItem(new Door(town.at(58, 12)));
		
		
		
		
		
		
		
		
		
		world.run();
	}
}
