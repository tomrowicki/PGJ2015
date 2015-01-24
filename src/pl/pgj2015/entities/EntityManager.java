package pl.pgj2015.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.pgj2015.controller.Controller;
import pl.pgj2015.controller.PlayerNumber;

public enum EntityManager {
	INSTANCE;
	
	private Set<GameEntity> gameEntities;
	private Map<PlayerNumber, Controller> playerNumberToController;
	
	private EntityManager(){
		gameEntities = new HashSet<GameEntity>();
		playerNumberToController = new HashMap<PlayerNumber, Controller>();
	}

	public Set<GameEntity> getGameEntities() {
		return gameEntities;
	}
	
	public Controller getControllerForPlayer(PlayerNumber playerNumber){
		return playerNumberToController.get(playerNumber);
	}
	
	public Collection<Controller> getAllControllers(){
		return playerNumberToController.values();
	}
	
	public void addGameEntity(GameEntity gameEntity){
		gameEntities.add(gameEntity);
	}
}
