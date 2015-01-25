package pl.pgj2015.event.clock;

import pl.pgj2015.entities.GameEntity;

public interface Clock {
	void update(double delta);
	String getState();
	void setStateToCountdown();
	void setItemToBring(GameEntity item);
	boolean isInCountdown();
}
