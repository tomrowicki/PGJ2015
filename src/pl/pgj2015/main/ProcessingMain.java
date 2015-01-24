package pl.pgj2015.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.pgj2015.controller.Controller;
import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.entities.EntityManager;
import pl.pgj2015.entities.PlayerEntity;
import pl.pgj2015.graphics.animation.Animation;
import pl.pgj2015.graphics.animation.ProcessingAnimation;
import pl.pgj2015.graphics.renderer.ProcessingRenderer;
import pl.pgj2015.sound.MinimSoundPlayer;
import pl.pgj2015.sound.SoundPlayer;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import ddf.minim.Minim;

public class ProcessingMain extends PApplet {
	private static final long serialVersionUID = 619350183209013106L;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 500;
	public static final double MILISECONDS_IN_TIME_UNIT = 1000;

	private SoundPlayer soundPlayer;
	private ProcessingRenderer renderer;
	private PlayerEntity player;

	private static final String SONG_FILE = "mainTheme.mp3";

	public void setup() {
		size(GAME_WIDTH, GAME_HEIGHT);
		startGame();
		try {
			soundPlayer.loadMusic(System.getProperty("user.dir")
					+ File.separator + SONG_FILE);
			soundPlayer.playMusic();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startGame() {
		Minim minim = new Minim(this);
		soundPlayer = new MinimSoundPlayer(minim);
		renderer = new ProcessingRenderer(this);
		player = new PlayerEntity(PlayerNumber.PLAYER_ONE, new PVector(100, 100), new PVector(50, 50));
		
		PImage image = loadImage("D:\\Dropbox\\Programming\\Java\\Eclipse Workspace\\PGJ2015\\shokunin.png");
		List<PImage> images = new ArrayList<PImage>();
		images.add(image);
		
		Animation animation = new ProcessingAnimation(images);
		player.setAnimation(animation);
		EntityManager.INSTANCE.addGameEntity(player);
	}

	public void draw() {
		renderer.draw();
		
	}

	@Override
	public void keyPressed() {
		for (Controller controller : EntityManager.INSTANCE.getAllControllers()) {
			controller.keyPressed(key);
		}
	}

	@Override
	public void keyReleased() {
		for (Controller controller : EntityManager.INSTANCE.getAllControllers()) {
			controller.keyReleased(key);
		}
	}

	public static void main(String[] args) {
		PApplet.main("pl.pgj2015.main.ProcessingMain", args);
	}

}
