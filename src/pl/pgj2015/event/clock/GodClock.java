package pl.pgj2015.event.clock;

import pl.pgj2015.entities.GameEntity;
import pl.pgj2015.game.Game;

public class GodClock implements Clock{
	
	private static final double COUNTDOWN_TIME = 10;
	
	private enum ClockState{
		COUNTDOWN, WAITING;
	}
	
	private double timer = COUNTDOWN_TIME;

	private ClockState clockState = ClockState.WAITING;

	private Game game;
	private GameEntity objectToGet;
	
	public GodClock(Game game){
		this.game = game;
	}
	
	@Override
	public void update(double delta) {
		if(clockState == ClockState.COUNTDOWN){
			timer -= delta;
			if(timer < 0){
				clockState = ClockState.WAITING;
			}
			objectToGet = game.drawNextStuff();
		}else{
			timer = COUNTDOWN_TIME;
		}
	}
	
	public void itemDroppedToAltar(){
		clockState = ClockState.COUNTDOWN;
	}

	@Override
	public String getState() {
		String state = null;
		switch(clockState){
		case COUNTDOWN:
			state = getCountdownState();
			break;
		case WAITING:
			state= getWaitingState();
			break;
		}
		return state;
	}
	
	public String getWaitingState(){
		return "Bring me: " + objectToGet.toString();
	}
	
	public String getCountdownState(){
		return "Time left: " + timer;
	}

}
