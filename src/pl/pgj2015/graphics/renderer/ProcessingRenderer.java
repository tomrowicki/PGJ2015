package pl.pgj2015.graphics.renderer;

import pl.pgj2015.entities.EntityManager;
import pl.pgj2015.entities.GameEntity;
import pl.pgj2015.main.ProcessingMain;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class ProcessingRenderer {
	private PApplet p;
	private PImage background;
	
	
	public ProcessingRenderer(PApplet parent){
		p = parent;
		background = p.loadImage(ProcessingMain.IMAGES_DIRECTORY + "bgd.jpg" );
		background.resize(ProcessingMain.GAME_WIDTH, ProcessingMain.GAME_HEIGHT);
	}
	
	public PImage loadImage(String fileName){
		return p.loadImage(fileName);
	}
	
	public void draw(){
		p.background(background);
		for(GameEntity gameEntity : EntityManager.INSTANCE.getGameEntities()){
			PVector size = gameEntity.getSize();
			PVector position = gameEntity.getPosition();
			p.imageMode(PApplet.CENTER);
			PImage image = gameEntity.getImage();
			p.image(image, position.x, position.y, size.x, size.y);
		}
	}
}
