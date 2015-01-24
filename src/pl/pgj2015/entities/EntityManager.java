package pl.pgj2015.entities;

import java.util.HashSet;
import java.util.Set;

public class EntityManager {
	private Set<GameEntity> gameEntities;
	
	public EntityManager(){
		gameEntities = new HashSet<GameEntity>();
	}

	public Set<GameEntity> getGameEntities() {
		return gameEntities;
	}
}
