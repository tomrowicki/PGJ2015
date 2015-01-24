package pl.pgj2015.entities;

import pl.pgj2015.graphics.Animation;
import processing.core.PImage;
import processing.core.PVector;

public interface GameEntity {
	void update(double delta);

	PVector getPosition();

	PVector getSize();

	PImage getImage();

	void setAnimation(Animation animation);

	GameEntityState getState();

	void applyForce(PVector force);

	PVector getSpeed();

	PVector getAcceleration();
	
	Long getId();
}
