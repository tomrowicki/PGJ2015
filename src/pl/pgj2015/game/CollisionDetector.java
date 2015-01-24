package pl.pgj2015.game;

import pl.pgj2015.entities.GameEntity;
import processing.core.PVector;

public class CollisionDetector {

	public static boolean doEntitiesCollide(GameEntity gameEntity,
			GameEntity gameEntity2) {
		boolean collide = false;
		PVector s1 = gameEntity.getSize().get();
		PVector s2 = gameEntity2.getSize().get();
		s1.div(2);
		s2.div(2);
		PVector p1 = gameEntity.getPosition();
		PVector p2 = gameEntity2.getPosition();

		if (p1.x < p2.x + s2.x &&
				   p1.x + s1.x > p2.x &&
				   p1.y < p2.y + s2.y &&
				   s1.y + p1.y > p2.y) {
			collide = true;
		}

		return collide;
	}
}
