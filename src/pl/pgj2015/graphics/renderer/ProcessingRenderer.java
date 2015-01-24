package pl.pgj2015.graphics.renderer;

import pl.pgj2015.entities.EntityManager;
import pl.pgj2015.entities.GameEntity;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class ProcessingRenderer {
	private PApplet p;
	
	public ProcessingRenderer(PApplet parent){
		p = parent;
	}
	
	public void draw(){
		p.background(0);
		for(GameEntity gameEntity : EntityManager.INSTANCE.getGameEntities()){
			PVector size = gameEntity.getSize();
			PVector position = gameEntity.getPosition();
			p.imageMode(PApplet.CENTER);
			PImage image = gameEntity.getImage();
			p.image(image, position.x, position.y, size.x, size.y);
		}
	}
}
