package pl.pgj2015.event;

import pl.pgj2015.entities.GameEntity;

public interface EventReceiver {
	void receiveEvent(EventType eventType, GameEntity from);
}
