package pl.pgj2015.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import pl.pgj2015.controller.Controller;
import pl.pgj2015.controller.KeyboardController;
import pl.pgj2015.controller.PlayerNumber;

public enum EntityManager {
	INSTANCE;
	
	private Set<GameEntity> gameEntities;
	private Map<PlayerNumber, Controller> playerNumberToController;
	
	private EntityManager(){
		gameEntities = new HashSet<GameEntity>();
		playerNumberToController = new HashMap<PlayerNumber, Controller>();
		for(PlayerNumber playerNumber : PlayerNumber.values()){
			Controller controller = new KeyboardController(playerNumber);
			playerNumberToController.put(playerNumber, controller);
		}
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
	
	public PlayerEntity getPlayer(PlayerNumber playerNumber){
		PlayerEntity player = null;
		for(GameEntity gameEntity : gameEntities){
			if(gameEntity instanceof PlayerEntity){
				if(playerNumber == ((PlayerEntity) gameEntity).getPlayerNumber()){
					player = (PlayerEntity) gameEntity;
					break;
				}
			}
		}
		return player;
	}
}
