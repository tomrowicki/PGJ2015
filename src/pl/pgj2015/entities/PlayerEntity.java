package pl.pgj2015.entities;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import pl.pgj2015.controller.ActionKey;
import pl.pgj2015.controller.PlayerNumber;
import pl.pgj2015.game.CollisionDetector;
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
	private StuffEntity holdingStuff = null;
	private long shotTime = System.nanoTime();

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
		
		PVector inverseAcceleration = acceleration.get();
		inverseAcceleration.mult(-1.f);
		
		boolean collide = checkForCollisions();
		if(collide){
			position.add(inverseAcceleration);
		}
		if(acceleration.x > 0){
			isFacingLeft = false;
		}else{
			isFacingLeft = true;
		}
		acceleration.mult(DAMPING_FACTOR);
	}
	
	private boolean checkForCollisions() {
		boolean collide = false;
		for(GameEntity entity : EntityManager.INSTANCE.getGameEntities()){
			if(!entity.equals(this)){
				boolean collideLocal = CollisionDetector.doEntitiesCollide(this, entity);
				if(collideLocal && entity instanceof StuffEntity){
					StuffEntity stuff = (StuffEntity) entity;
					if(holdingStuff == null){
						stuff.grabbedByPlayer(playerNumber);
						holdingStuff = stuff;
					}
					if(playerNumber.equals(stuff.getHoldingPlayerNumber())){
						collideLocal = false;
					}
				}else if (collideLocal && entity instanceof Altar){
					if(holdingStuff != null){
						game.itemDroppedOnAltar(this, holdingStuff);
						dropStuff();
					}
					collideLocal = false; //Altar spawns in random place, player doesnt collide with it
				}
				collide = collide || collideLocal;
			}
		}
		return collide;
	}

	private void handleKeysPressed(){
		List<ActionKey> keysPressed = EntityManager.INSTANCE.getControllerForPlayer(playerNumber).getKeysPressed();
		for(ActionKey key : keysPressed){
			PVector force = new PVector(0, 0);
			boolean hasShot = false;
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
					hasShot = true;
					break;
				case UP:
					force = new PVector(0, -MAX_SPEED_Y);
					break;
				case DROP:
					if(holdingStuff != null){
						holdingStuff.setPosition(new PVector(position.x + 100, position.y));
						holdingStuff.droppedByPlayer();
						holdingStuff = null;
					}
					break;
				default:
					break;
			}
			PVector projectileForce = new PVector();
			if (isFacingLeft){
				projectileForce.x = -10;
			}
			else 
			{
				projectileForce.x = 10;
			}
			if (hasShot){
				if (System.nanoTime() - shotTime > 4000){
					game.addProjectile(position.get(), projectileForce, playerNumber, new PVector(20, 20), this.isFacingLeft);
					shotTime = System.nanoTime();
					hasShot = false;
				}
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
		PImage currentImage = animation.getCurrentImage();
		currentImage.resize((int)size.x, (int)size.y);
		
		if(isFacingLeft){
			Image image = currentImage.getImage();
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter((BufferedImage) image, null);
			return new PImage(image);
		}
		
		return currentImage;
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
	private void dropStuff(){
		holdingStuff.droppedByPlayer();
		holdingStuff = null;
	}
}
