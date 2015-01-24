package pl.pgj2015.main;

import processing.core.PApplet;
import ddf.minim.Minim;

public class ProcesingMain extends PApplet {
	private static final long serialVersionUID = 619350183209013106L;
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGHT = 500;
	private static final double MILISECONDS_IN_TIME_UNIT = 1000;

	public void setup() {
		size(GAME_WIDTH, GAME_HEIGHT);

	}

	public void startGame() {
		Minim minim = new Minim(this);
	}

	public void draw() {

	}

	@Override
	public void keyPressed() {
		// handle keys
	}

	@Override
	public void keyReleased() {
		// handle keys
	}

	public static void main(String[] args) {
		PApplet.main("pl.pgj2015.main.ProcessingMain", args);
	}

}
