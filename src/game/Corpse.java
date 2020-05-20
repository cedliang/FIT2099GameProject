package game;
import edu.monash.fit2099.engine.Actor;
public class Corpse extends StationaryItem {
	
	protected String actorName;
	
	public Corpse(Actor actor) {
		super("dead "+actor.toString(), '%');
		actorName = actor.toString();		
	}

}
