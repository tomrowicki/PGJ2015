package pl.pgj2015.controller;

import java.util.List;
import java.util.Map;

public class KeyboardController implements Controller {

	private PlayerNumber player;
	
	public KeyboardController (PlayerNumber player){
		
		this.player = player;
	}

	@Override
	public void keyPressed(int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ActionKey> getKeysPressed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerNumber playerNumber() {

		return player;
	}
}
