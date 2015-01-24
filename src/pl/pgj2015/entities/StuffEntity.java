package pl.pgj2015.entities;

import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.graphics.animation.Animation;
import processing.core.PImage;
import processing.core.PVector;

public class StuffEntity implements GameEntity{
	private PlayerNumber holdingPlayerNumber;

	private long id = IdManager.INSTANCE.getNextId();

	private GameEntityState gameEntityState;

	private PVector acceleration = new PVector();
	private PVector position;
	private PVector size;
	private Animation animation;
	private PVector speed = new PVector(0 , 0);
	private String name;

	public StuffEntity(PVector position,
			PVector size, String name, Animation animation) {
		this.position = position;
		this.size = size;
		this.name = name;
	}

	@Override
	public void update(double delta) {
		animation.update(delta);
		if(holdingPlayerNumber != null){
			position = getHoldingPlayerPosition(holdingPlayerNumber);
		}
	}
	
	private PVector getHoldingPlayerPosition(PlayerNumber playerNumber){
		PlayerEntity player = EntityManager.INSTANCE.getPlayer(holdingPlayerNumber);
		return player.getPosition();
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
		return gameEntityState;
	}

	@Override
	public void applyForce(PVector force) {
		acceleration.add(force);
	}

	@Override
	public PVector getSpeed() {
		return speed;
	}

	@Override
	public PVector getAcceleration() {
		return acceleration;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void grabbedByPlayer(PlayerNumber playerNumber){
		holdingPlayerNumber = playerNumber;
	}
	
	public void droppedByPlayer(){
		position = getHoldingPlayerPosition(holdingPlayerNumber).get();
		holdingPlayerNumber = null;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return getId().intValue();
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object o){
		boolean isEqual = false;
		if(o instanceof GameEntity){
			isEqual = (getId() == ((GameEntity)o).getId());
		}
		return isEqual;
	}
	
}
