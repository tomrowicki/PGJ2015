package pl.pgj2015.sound;

import java.io.IOException;

import ddf.minim.AudioOutput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class MinimSoundPlayer implements SoundPlayer {
	private AudioPlayer player;
	private Minim minim;

	private AudioOutput out;

	public MinimSoundPlayer(Minim minim) {
		this.minim = minim;

		out = minim.getLineOut();
	}

	@Override
	public void loadMusic(String fileName) throws IOException {
		player = minim.loadFile(fileName);
	}

	@Override
	public void playMusic() {
		if (player != null) {
			player.play();
			player.loop();
		}
	}

	@Override
	public void stopMusic() {
		if (player != null) {
			player.pause();
			player.close();
		}
		out.close();
	}

}
