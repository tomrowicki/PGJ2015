package pl.pgj2015.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import pl.pgj2015.controller.Controller;
import pl.pgj2015.controller.KeyboardController;
import pl.pgj2015.controller.PlayerNumber;

public enum EntityManager {
	INSTANCE;
	
	private Set<GameEntity> gameEntities;
	private Set<GameEntity> gameEntitiesToAdd;
	private Map<PlayerNumber, Controller> playerNumberToController;
	private Set<StuffEntity> stuffs;
	
	private EntityManager(){
		gameEntities = new HashSet<GameEntity>();
		playerNumberToController = new HashMap<PlayerNumber, Controller>();
		for(PlayerNumber playerNumber : PlayerNumber.values()){
			Controller controller = new KeyboardController(playerNumber);
			playerNumberToController.put(playerNumber, controller);
		}
		stuffs = new HashSet<StuffEntity>();
		gameEntitiesToAdd = new HashSet<GameEntity>();
	}

	public void addStuff(StuffEntity stuff) {
		stuffs.add(stuff);
		gameEntities.add(stuff);
	}
	
	public void removeStuff(StuffEntity stuff){
		stuffs.remove(stuff);
		gameEntities.remove(stuff);
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
		gameEntitiesToAdd.add(gameEntity);
	}
	
	public void flush(){
		for(GameEntity gameEntity : gameEntitiesToAdd){
			gameEntities.add(gameEntity);
		}
		gameEntitiesToAdd = new HashSet<GameEntity>();
	}
	
	public void removeGameEntity(long id){
		Iterator<GameEntity> iterator = gameEntities.iterator();
		while(iterator.hasNext()){
			GameEntity gameEntity = iterator.next();
			if(gameEntity.getId() == id){
				iterator.remove();
				break;
			}
		}
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
