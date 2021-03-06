package pl.pgj2015.controller;

import java.util.HashMap;
import java.util.Map;

public enum ControllerConfig {
	
	INSTANCE;
	
	private Map<PlayerNumber, Map<Integer, ActionKey>> config = new HashMap<PlayerNumber, Map<Integer, ActionKey>>();

	public Map<Integer, ActionKey> getConfigForPlayer(PlayerNumber player) {

		return config.get(player);
	}

	public void setConfigurationForPlayer(PlayerNumber player) {

		if (player == PlayerNumber.PLAYER_ONE) {
			Map<Integer, ActionKey> keysMapping = new HashMap<Integer, ActionKey>();
			keysMapping.put((int) 'w', ActionKey.UP);
			keysMapping.put((int) 's', ActionKey.DOWN);
			keysMapping.put((int) 'a', ActionKey.LEFT);
			keysMapping.put((int) 'd', ActionKey.RIGHT);
			keysMapping.put((int) 'v', ActionKey.SHOOT);
			keysMapping.put((int) 'c', ActionKey.DROP);
			config.put(player, keysMapping);
		} else if (player == PlayerNumber.PLAYER_TWO) {

			/*
			 * left arrow: 37 up arrow: 38 right arrow: 39 down arrow: 40
			 */
			Map<Integer, ActionKey> keysMapping = new HashMap<Integer, ActionKey>();
			keysMapping.put((int) 'i', ActionKey.UP);
			keysMapping.put((int) 'k', ActionKey.DOWN);
			keysMapping.put((int) 'j', ActionKey.LEFT);
			keysMapping.put((int) 'l', ActionKey.RIGHT);
			keysMapping.put((int) 'p', ActionKey.SHOOT);
			keysMapping.put((int) 'o', ActionKey.DROP);
			config.put(player, keysMapping);
		}

	}

	public Map<Integer, ActionKey> getKeysMappingForPlayer(PlayerNumber player) {
		return config.get(player);
	}
}
