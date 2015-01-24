package pl.pgj2015.controller;

import java.util.List;

public interface Controller {
	void keyPressed(int key);
	void keyReleased(int key);
	List<ActionKey> getKeysPressed();
	PlayerNumber playerNumber(); 
	
}
