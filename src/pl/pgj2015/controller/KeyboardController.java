package pl.pgj2015.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardController implements Controller {

	private PlayerNumber player;
	private Map<Integer, ActionKey> keysMapping;
	private Map<ActionKey, Boolean> keysPressed = new HashMap<ActionKey, Boolean>();
	
	public KeyboardController (PlayerNumber player){
		
		this.player = player;
		ControllerConfig config = ControllerConfig.INSTANCE;
		config.setConfigurationForPlayer(player);
		keysMapping = config.getKeysMappingForPlayer(player);
		for(ActionKey key : ActionKey.values()){
			keysPressed.put(key, false);
		}
	}

	@Override
	public void keyPressed(int key) {
		if(keysMapping.containsKey(key)){
			keysPressed.put(keysMapping.get(key), true);
		}
	}

	@Override
	public void keyReleased(int key) {

		if(keysMapping.containsKey(key)){
			keysPressed.put(keysMapping.get(key), false);
		}
	}

	@Override
	public List<ActionKey> getKeysPressed() {

		List<ActionKey> keysPressedList = new ArrayList<>();
		for(ActionKey actionKey : ActionKey.values()){
			if(keysPressed.get(actionKey)){
				keysPressedList.add(actionKey);
			}
		}
		return keysPressedList;
	}

	@Override
	public PlayerNumber getPlayerNumber() {
		return player;
	}
}
