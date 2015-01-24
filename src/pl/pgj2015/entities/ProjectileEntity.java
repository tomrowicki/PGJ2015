package pl.pgj2015.entities;

import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.graphics.animation.Animation;
import pl.pgj2015.main.ProcessingMain;
import processing.core.PImage;
import processing.core.PVector;

public class ProjectileEntity implements GameEntity {

	private static final float MAX_SPEED_X = 20;
	private static final float MAX_SPEED_Y = 20;
	private static final float MAX_ACCELERATION = 7;

	private GameEntityState gameEntityState;
	
	private PVector position;
	private long id; 
	private PVector speed = new PVector(MAX_SPEED_X, MAX_SPEED_Y);
	private PVector size;
	private PVector acceleration = new PVector();
	private PlayerNumber playerNumber;
	private Animation animation;

	public ProjectileEntity(PVector position, PVector acceleration,
			PlayerNumber playerNumber, PVector size, Animation animation){
		
		this.playerNumber = playerNumber;
		this.position = position;
		this.acceleration = acceleration;
		this.size = size;
		this.animation = animation;
		id = IdManager.INSTANCE.getNextId();
	}
	
	@Override
	public void update(double delta) {

		acceleration.mult((float) (ProcessingMain.MILISECONDS_IN_TIME_UNIT/delta));
		position.add(acceleration);
		acceleration.limit(MAX_ACCELERATION);
		//TODO: mo¿na dorzuciæ animacjê pocisku
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
}
