package pl.pgj2015.sound;

import java.io.IOException;

public interface SoundPlayer {
	void loadMusic(String fileName) throws IOException;

	void playMusic();

	void stopMusic();
}
