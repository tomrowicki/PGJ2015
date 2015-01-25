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
	private PVector force;

	public ProjectileEntity(PVector position, PVector force,
			PlayerNumber playerNumber, PVector size, Animation 

animation, boolean facingLeft){
		
		// TODO: spawnowaæ pocisk przed postaci¹, bior¹c pod uwagê jej zwrot
		// TODO: wykorzystaæ force do ustalenia sposobu poruszania siê pocisku
		// FIXME: zrobiæ coœ z losowym znikaniem obiektów
		this.playerNumber = playerNumber;
		float newX = (position.x);
		if (facingLeft){
			newX = position.x - 100;
		}
		else if (!facingLeft){
			newX = position.x + 100;
		}
		PVector projectileSpawningPosition = new PVector (newX, position.y);
		this.position = projectileSpawningPosition;
		this.force = force;
		this.size = size;
		this.animation = animation;
		id = IdManager.INSTANCE.getNextId();
	}
	
	@Override
	public void update(double delta) {

		acceleration.mult((float) (ProcessingMain.MILISECONDS_IN_TIME_UNIT/delta));
		acceleration.limit(MAX_ACCELERATION);
		applyForce(force);
		position.add(acceleration);
		
		//TODO: mo¿na dorzuciæ animacjê pocisku
	}

	@Override
	public PVector getPosition() {
		if (position.x < 0){
			EntityManager.INSTANCE.removeGameEntity(id);
		}
		else if (position.x > ProcessingMain.GAME_WIDTH){
			EntityManager.INSTANCE.removeGameEntity(id);
		}
		if (position.y < 0){
			EntityManager.INSTANCE.removeGameEntity(id);
		}
		else if (position.y > ProcessingMain.GAME_HEIGHT){
			EntityManager.INSTANCE.removeGameEntity(id);
		}
		
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
