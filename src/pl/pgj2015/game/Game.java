package pl.pgj2015.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.entities.Altar;
import pl.pgj2015.entities.EntityManager;
import pl.pgj2015.entities.GameEntity;
import pl.pgj2015.entities.PlayerEntity;
import pl.pgj2015.entities.ProjectileEntity;
import pl.pgj2015.entities.StuffEntity;
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
	private Altar altar;
	private ProcessingRenderer renderer;
	private PlayerEntity player;
	private PlayerEntity player2;
	private String[] stuffNames = {
		"beer",
		"chili",
		"coke"
	};

	public Game(ProcessingRenderer renderer) {
		this.renderer = renderer;
		addPlayers();
		addAltar();
		addStuff();
	}

	private void addStuff() {
		for(int i = 0 ; i < stuffNames.length; i++){
			addStuffByName(stuffNames[i]);
		}
	}

	private void addAltar() {
		List<PImage> images = new ArrayList<PImage>();
		images.add(renderer.loadImage(ProcessingMain.IMAGES_DIRECTORY
				+ "altar.png"));
		Animation altarAnimation = new ProcessingAnimation(images);
		altar = new Altar(new PVector(250, 250), new PVector(75, 75),
				altarAnimation);
		EntityManager.INSTANCE.addGameEntity(altar);
		EntityManager.INSTANCE.flush();
	}

	private void addStuffByName(String stuffName) {
		List<PImage> images = new ArrayList<PImage>();
		images.add(renderer.loadImage(ProcessingMain.IMAGES_DIRECTORY + File.separator +"stuff" + File.separator
				+ stuffName + ".png"));
		Animation animation = new ProcessingAnimation(images);
		Random random = new Random();
		PVector position = new PVector(random.nextInt(ProcessingMain.GAME_WIDTH), random.nextInt(ProcessingMain.GAME_HEIGHT));
		StuffEntity stuff = new StuffEntity(position, new PVector(50, 50), stuffName, animation);
		EntityManager.INSTANCE.addStuff(stuff);
		EntityManager.INSTANCE.flush();
	}

	public void update(double delta) {
		clock.update(delta);
		Iterator<GameEntity> iterator = EntityManager.INSTANCE
				.getGameEntities().iterator();
		while (iterator.hasNext()) {
			GameEntity gameEntity = iterator.next();
			gameEntity.update(delta);
		}
		EntityManager.INSTANCE.flush();
	}

	public void drawNextStuff() {
		GameEntity itemToBring = getRandomStuff();
		clock.setItemToBring(itemToBring);
		altar.setItemToBring(itemToBring);
	}

	public GameEntity getRandomStuff() {
		Random random = new Random();
		return EntityManager.INSTANCE.getStuffByName(stuffNames[random.nextInt(stuffNames.length)]);
	}

	public String getMessageFromHigherPower() {
		return clock.getState();
	}

	public void addPlayers() {
		player = new PlayerEntity(PlayerNumber.PLAYER_ONE,
				new PVector(100, 100), new PVector(50, 80), this);
		player2 = new PlayerEntity(PlayerNumber.PLAYER_TWO, new PVector(150,
				100), new PVector(50, 80), this);

		String baseDirPlayer1 = ProcessingMain.IMAGES_DIRECTORY + "player1"
				+ File.separator;
		List<PImage> images = new ArrayList<PImage>();

		for (int i = 0; i <= 4; i++) {
			PImage image = renderer.loadImage(baseDirPlayer1 + "deranged_" + i
					+ ".png");
			images.add(image);
		}

		Animation animation = new ProcessingAnimation(images);
		player.setAnimation(animation);
		player2.setAnimation(animation);
		EntityManager.INSTANCE.addGameEntity(player);
		EntityManager.INSTANCE.addGameEntity(player2);
		EntityManager.INSTANCE.flush();
	}

	public void addProjectile(PVector position, PVector force, PlayerNumber pn,
			PVector size, boolean facingLeft) {

		PImage image = renderer.loadImage(ProcessingMain.MAIN_DIRECTORY
				+ "melon.png");
		List<PImage> images = new ArrayList<PImage>();
		images.add(image);
		Animation animation = new ProcessingAnimation(images);
		ProjectileEntity projectile = new ProjectileEntity(position.get(), force.get(), pn,
				size.get(), animation, facingLeft);
		EntityManager.INSTANCE.addGameEntity(projectile);

	}

	public void itemDeliveredByPlayer(GameEntity player) {
		drawNextStuff();
	}

}
