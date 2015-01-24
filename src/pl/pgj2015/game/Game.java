package pl.pgj2015.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.entities.EntityManager;
import pl.pgj2015.entities.GameEntity;
import pl.pgj2015.entities.PlayerEntity;
import pl.pgj2015.entities.ProjectileEntity;
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
		Iterator<GameEntity> iterator = EntityManager.INSTANCE.getGameEntities().iterator();
		while (iterator.hasNext()) {
			GameEntity gameEntity = iterator.next();
			gameEntity.update(delta);
		}
		EntityManager.INSTANCE.flush();
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
				new PVector(100, 100), new PVector(50, 80), this);
		player2 = new PlayerEntity(PlayerNumber.PLAYER_TWO, new PVector(150,
				100), new PVector(50, 80), this);
		
		String baseDirPlayer1 = ProcessingMain.IMAGES_DIRECTORY + "player1" + File.separator;
		List<PImage> images = new ArrayList<PImage>();
		
		for(int i = 0; i <= 4; i++){
			PImage image = renderer.loadImage(baseDirPlayer1 + "deranged_"+i+".png");
			images.add(image);
		}
		
		Animation animation = new ProcessingAnimation(images);
		player.setAnimation(animation);
		player2.setAnimation(animation);
		EntityManager.INSTANCE.addGameEntity(player);
		EntityManager.INSTANCE.addGameEntity(player2);
		EntityManager.INSTANCE.flush();
	}
	
	public  void addProjectile(PVector position, PVector acc, PlayerNumber pn, PVector size){
		
		PImage image = renderer.loadImage(ProcessingMain.MAIN_DIRECTORY + "melon.png");
		List<PImage> images = new ArrayList<PImage>();
		images.add(image);
		Animation animation = new ProcessingAnimation(images);
		ProjectileEntity projectile = new ProjectileEntity(position, acc, pn, size, animation);
		EntityManager.INSTANCE.addGameEntity(projectile);
		
	}

}
