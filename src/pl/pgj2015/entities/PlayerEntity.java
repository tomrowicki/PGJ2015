package pl.pgj2015.entities;

import java.util.List;

import pl.pgj2015.controller.ActionKey;
import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.game.Game;
import pl.pgj2015.graphics.animation.Animation;
import pl.pgj2015.main.ProcessingMain;
import processing.core.PImage;
import processing.core.PVector;

public class PlayerEntity implements GameEntity {
	private PlayerNumber playerNumber;
	private static final float MAX_SPEED_X = 10;
	private static final float MAX_SPEED_Y = 10;
	private static final float MAX_ACCELERATION = 7;
	private static final float DAMPING_FACTOR = 0.01f;
	
	private long id = IdManager.INSTANCE.getNextId();

	private GameEntityState gameEntityState;

	private PVector acceleration = new PVector();
	private PVector position;
	private PVector size;
	private Animation animation;
	private PVector speed = new PVector(MAX_SPEED_X, MAX_SPEED_Y);
	private boolean isFacingLeft = true;
	Game game;

	public PlayerEntity(PlayerNumber playerNumber, PVector position,
			PVector size, Game game) {
		this.playerNumber = playerNumber;
		this.position = position;
		this.size = size;
		this.game = game;
	}
	
	public PlayerEntity(PlayerNumber playerNumber, PVector position,
			PVector size, Animation animation, Game game) {
		this(playerNumber, position, size, game);
		this.animation = animation;
	}

	@Override
	public void update(double delta) {
		animation.update(delta);
		handleKeysPressed();
		acceleration.mult((float) (ProcessingMain.MILISECONDS_IN_TIME_UNIT/delta));
		acceleration.limit(MAX_ACCELERATION);
		position.add(acceleration);
		acceleration.mult(DAMPING_FACTOR);
	}
	
	private void handleKeysPressed(){
		List<ActionKey> keysPressed = EntityManager.INSTANCE.getControllerForPlayer(playerNumber).getKeysPressed();
		for(ActionKey key : keysPressed){
			PVector force = new PVector(0, 0);
			switch(key){
				case DOWN:
					force = new PVector(0, MAX_SPEED_Y);
					break;
				case LEFT:
					force = new PVector(-MAX_SPEED_X, 0);
					break;
				case RIGHT:
					force = new PVector(MAX_SPEED_X, 0);
					break;
				case SHOOT:
					game.addProjectile(position, acceleration, playerNumber, size);
					break;
				case UP:
					force = new PVector(0, -MAX_SPEED_Y);
					break;
				default:
					break;
			}
			applyForce(force);
		}
	}

	@Override
	public PVector getPosition() {
		
		if (position.x < 0){
			position.x = ProcessingMain.GAME_WIDTH;
		}
		else if (position.x > ProcessingMain.GAME_WIDTH){
			position.x = 0;
		}
		if (position.y < 0){
			position.y = ProcessingMain.GAME_HEIGHT;
		}
		else if (position.y > ProcessingMain.GAME_HEIGHT){
			position.y = 0;
		}
		 
		return position;
	}

	@Override
	public PVector getSize() {
		return size;
	}
	
	public PlayerNumber getPlayerNumber(){
		return playerNumber;
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

	public boolean isFacingLeft() {
		return isFacingLeft;
	}

	public void setFacingLeft(boolean isFacingLeft) {
		this.isFacingLeft = isFacingLeft;
	}

}
