package pl.pgj2015.entities;

import java.util.HashSet;
import java.util.Set;

public enum EntityManager {
	INSTANCE;
	
	private Set<GameEntity> gameEntities;
	
	private EntityManager(){
		gameEntities = new HashSet<GameEntity>();
	}

	public Set<GameEntity> getGameEntities() {
		return gameEntities;
	}
}
