package pl.pgj2015.game;

import java.util.ArrayList;
import java.util.List;

import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.entities.EntityManager;
import pl.pgj2015.entities.GameEntity;
import pl.pgj2015.entities.PlayerEntity;
import pl.pgj2015.event.clock.Clock;
import pl.pgj2015.event.clock.GodClock;
import pl.pgj2015.graphics.animation.Animation;
import pl.pgj2015.graphics.animation.ProcessingAnimation;
import pl.pgj2015.graphics.renderer.ProcessingRenderer;
import pl.pgj2015.main.ProcessingMain;
import processing.core.PImage;
import processing.core.PVector;

public class Game {
	private Clock clock = new GodClock(this);
	private ProcessingRenderer renderer;
	private PlayerEntity player;
	private PlayerEntity player2;

	public Game(ProcessingRenderer renderer) {
		this.renderer = renderer;
		addPlayers();
	}

	public void update(double delta) {
		clock.update(delta);
		for (GameEntity gameEntity : EntityManager.INSTANCE.getGameEntities()) {
			gameEntity.update(delta);
		}
	}

	public GameEntity drawNextStuff() {
		// TODO: Implement logic
		return null;
	}

	public String getMessageFromHigherPower() {
		return clock.getState();
	}
	
	public void addPlayers() {
		player = new PlayerEntity(PlayerNumber.PLAYER_ONE,
				new PVector(100, 100), new PVector(50, 50));
		player2 = new PlayerEntity(PlayerNumber.PLAYER_TWO, new PVector(150,
				100), new PVector(50, 50));

		PImage image = renderer.loadImage(ProcessingMain.MAIN_DIRECTORY + "shokunin.png");
		List<PImage> images = new ArrayList<PImage>();
		images.add(image);

		Animation animation = new ProcessingAnimation(images);
		player.setAnimation(animation);
		player2.setAnimation(animation);
		EntityManager.INSTANCE.addGameEntity(player);
		EntityManager.INSTANCE.addGameEntity(player2);
	}

}
