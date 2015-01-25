package pl.pgj2015.game;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
	private List<String> stuffNames = new ArrayList<String>();
	private static final float SCALE = 2.f;
	private Map<PlayerNumber, Integer> points;

	public Game(ProcessingRenderer renderer) {
		this.renderer = renderer;
		addPlayers();
		addAltar();
		addStuff();
		points = new HashMap<PlayerNumber, Integer>();
		points.put(PlayerNumber.PLAYER_ONE, 0);
		points.put(PlayerNumber.PLAYER_TWO, 0);
		renderer.setPoints(points);
	}

	private void addStuff() {
		stuffNames.add("beer");
		stuffNames.add("chili");
		stuffNames.add("coke");
		stuffNames.add("cheese");
		stuffNames.add("corn");
		stuffNames.add("lays");
		for (String stuffName : stuffNames) {
			addStuffByName(stuffName);
		}
	}

	private void addAltar() {
		List<PImage> images = new ArrayList<PImage>();
		images.add(renderer.loadImage(ProcessingMain.IMAGES_DIRECTORY
				+ "altar.png"));
		Animation altarAnimation = new ProcessingAnimation(images);
		Random random = new Random();
		PVector position = new PVector(
				random.nextInt(ProcessingMain.GAME_WIDTH - 50),
				random.nextInt(ProcessingMain.GAME_HEIGHT - 50));
		altar = new Altar(position, new PVector(75 * SCALE, 75 * SCALE),
				altarAnimation);
		EntityManager.INSTANCE.addGameEntity(altar);
		EntityManager.INSTANCE.flush();
	}

	private void addStuffByName(String stuffName) {
		List<PImage> images = new ArrayList<PImage>();
		images.add(renderer.loadImage(ProcessingMain.IMAGES_DIRECTORY
				+ File.separator + "stuff" + File.separator + stuffName
				+ ".png"));
		Animation animation = new ProcessingAnimation(images);
		Random random = new Random();
		PVector position = new PVector(
				random.nextInt(ProcessingMain.GAME_WIDTH),
				random.nextInt(ProcessingMain.GAME_HEIGHT));
		StuffEntity stuff = new StuffEntity(position, new PVector(50 * SCALE,
				50 * SCALE), stuffName, animation);
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
		return EntityManager.INSTANCE.getStuffByName(stuffNames.get(random
				.nextInt(stuffNames.size())));
	}

	public String getMessageFromHigherPower() {
		return clock.getState();
	}

	public void addPlayers() {
		PVector playerSize = new PVector(60 * SCALE * 1.5f, 80 * SCALE * 1.5f);
		player = new PlayerEntity(PlayerNumber.PLAYER_ONE,
				new PVector(100, 100), playerSize, this);
		player2 = new PlayerEntity(PlayerNumber.PLAYER_TWO, new PVector(ProcessingMain.GAME_WIDTH - 150,
				100), playerSize, this);

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
		ProjectileEntity projectile = new ProjectileEntity(position.get(),
				force.get(), pn, size.get(), animation, facingLeft);
		EntityManager.INSTANCE.addGameEntity(projectile);

	}

	public void itemDeliveredByPlayer(GameEntity player, StuffEntity stuff) {
		addPointToPlayer(((PlayerEntity)player).getPlayerNumber());
		EntityManager.INSTANCE.removeStuff(stuff);
		stuffNames.remove(stuff.getName());
		clock.setStateToCountdown();
	}

	public void itemDroppedOnAltar(GameEntity player, StuffEntity stuff) {
		altar.itemDelivered(stuff, player, this);
	}
	
	public boolean canPlayersGrabbStuff(){
		return clock.isInCountdown();
	}
	
	public void addPointToPlayer(PlayerNumber playerNumber){
		Integer pointsForPlayer = points.get(playerNumber);
		pointsForPlayer += 1;
		points.put(playerNumber, pointsForPlayer);
	}
}
