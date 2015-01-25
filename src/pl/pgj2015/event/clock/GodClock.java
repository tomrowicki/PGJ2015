package pl.pgj2015.event.clock;

import pl.pgj2015.entities.GameEntity;
import pl.pgj2015.game.Game;
import pl.pgj2015.main.ProcessingMain;

public class GodClock implements Clock {

	private static final double COUNTDOWN_TIME = 10 * ProcessingMain.MILISECONDS_IN_TIME_UNIT;

	private enum ClockState {
		COUNTDOWN, WAITING;
	}

	private double timer = COUNTDOWN_TIME;

	private ClockState clockState = ClockState.COUNTDOWN;

	private Game game;
	private GameEntity objectToGet;

	public GodClock(Game game) {
		this.game = game;
	}

	@Override
	public void update(double delta) {
		if (clockState == ClockState.COUNTDOWN) {
			timer -= delta;
			if (timer < 0) {
				clockState = ClockState.WAITING;
			}
			game.drawNextStuff();
		} else {
			timer = COUNTDOWN_TIME;
		}
	}

	public void itemDroppedToAltar() {
		clockState = ClockState.COUNTDOWN;
	}

	@Override
	public String getState() {
		String state = null;
		switch (clockState) {
		case COUNTDOWN:
			state = getCountdownState();
			break;
		case WAITING:
			state = getWaitingState();
			break;
		}
		return state;
	}

	public String getWaitingState() {
		return "Bring me: " + objectToGet;
	}

	public String getCountdownState() {
		return "Time left: " + timer;
	}

	@Override
	public void setItemToBring(GameEntity item) {
		objectToGet = item;
	}

	@Override
	public void setStateToCountdown() {
		clockState = ClockState.COUNTDOWN;
	}

}
