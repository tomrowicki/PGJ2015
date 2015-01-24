package pl.pgj2015.game;

import pl.pgj2015.entities.EntityManager;
import pl.pgj2015.entities.GameEntity;
import pl.pgj2015.event.clock.Clock;
import pl.pgj2015.event.clock.GodClock;

public class Game {
	private Clock clock = new GodClock(this);
	
	private long lastFrameTime = System.currentTimeMillis();
	
	public void update(double delta){
		clock.update(delta);
		for (GameEntity gameEntity : EntityManager.INSTANCE.getGameEntities()) {
			gameEntity.update(System.currentTimeMillis() - lastFrameTime);
		}
		lastFrameTime = System.currentTimeMillis();
	}
	
	public GameEntity drawNextStuff(){
		//TODO: Implement logic
		return null;
	}
}
