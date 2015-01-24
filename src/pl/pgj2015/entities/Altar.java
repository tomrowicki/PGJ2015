package pl.pgj2015.entities;

import pl.pgj2015.game.Game;
import pl.pgj2015.graphics.animation.Animation;
import processing.core.PImage;
import processing.core.PVector;

public class Altar implements GameEntity{
	private PVector acceleration = new PVector(0.f , 0.f);
	private PVector position;
	private PVector size;
	private Animation animation;
	private long id = IdManager.INSTANCE.getNextId();
	private GameEntity itemToBring;
	
	public Altar(PVector position, PVector size, Animation animation){
		this.position = position;
		this.size = size;
		this.animation = animation;
	}
	
	@Override
	public void update(double delta) {
		animation.update(delta);
	}

	@Override
	public PVector getPosition() {
		return position;
	}

	@Override
	public PVector getSize() {
		return size;
	}

	@Override
	public PImage getImage() {
		return animation.getCurrentImage();
	}

	@Override
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	@Override
	public GameEntityState getState() {
		return null;
	}

	@Override
	public void applyForce(PVector force) {
		
	}

	@Override
	public PVector getSpeed() {
		return acceleration;
	}

	@Override
	public PVector getAcceleration() {
		return acceleration;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	public void setItemToBring(GameEntity itemToBring){
		this.itemToBring = itemToBring;
	}
	
	public void itemDelivered(GameEntity item, GameEntity player, Game game){
		if(item.equals(itemToBring)){
			game.itemDeliveredByPlayer(player);
		}
	}

}
