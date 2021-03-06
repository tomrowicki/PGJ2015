package pl.pgj2015.graphics.renderer;

import java.io.IOException;
import java.util.Map;

import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.entities.EntityManager;
import pl.pgj2015.entities.GameEntity;
import pl.pgj2015.main.ProcessingMain;
import pl.pgj2015.sound.SoundPlayer;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class ProcessingRenderer {
	private PApplet p;
	private PImage background;
	private Map<PlayerNumber, Integer> points;
	boolean gameHasEnded = false;
	private PImage endingImage;
	private String messageFromHigherPower = null;
	private SoundPlayer soundPlayer;
	
	public ProcessingRenderer(PApplet parent, SoundPlayer soundPlayer){
		p = parent;
		background = p.loadImage(ProcessingMain.IMAGES_DIRECTORY + "bgd.jpg" );
		background.resize(ProcessingMain.GAME_WIDTH, ProcessingMain.GAME_HEIGHT);
		endingImage = loadImage(ProcessingMain.IMAGES_DIRECTORY + "outro.jpg");
		endingImage.resize(ProcessingMain.GAME_WIDTH, ProcessingMain.GAME_HEIGHT);
		this.soundPlayer = soundPlayer;
	}
	
	public PImage loadImage(String fileName){
		return p.loadImage(fileName);
	}
	
	public void draw(){
		if(gameHasEnded){
			p.background(endingImage);
		}else{
			p.background(background);
			for(GameEntity gameEntity : EntityManager.INSTANCE.getGameEntities()){
				PVector size = gameEntity.getSize();
				PVector position = gameEntity.getPosition();
				p.imageMode(PApplet.CENTER);
				PImage image = gameEntity.getImage();
				p.image(image, position.x, position.y, size.x, size.y);
			}
			drawPoints();
			p.text(messageFromHigherPower, ProcessingMain.GAME_WIDTH / 2 - 100, 50);
		}
	}
	
	private void drawPoints(){
		p.textMode(PApplet.CENTER);
		//p.textSize(32);
		p.text( points.get(PlayerNumber.PLAYER_ONE), 50, ProcessingMain.GAME_HEIGHT - 50);
		p.text( points.get(PlayerNumber.PLAYER_TWO), ProcessingMain.GAME_WIDTH - 50, ProcessingMain.GAME_HEIGHT - 50);
	}

	public void setPoints(Map<PlayerNumber, Integer> points) {
		this.points = points;
	}
	
	public void gameEnded(){
		if(gameHasEnded == false){
			soundPlayer.stopMusic();
			try {
				soundPlayer.loadMusic(ProcessingMain.MAIN_DIRECTORY + "outro.mp3");
				soundPlayer.playMusic();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		gameHasEnded = true;
	}
	public void setMessage(String message){
		messageFromHigherPower = message;
	}
}
