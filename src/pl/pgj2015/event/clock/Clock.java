package pl.pgj2015.event.clock;

public interface Clock {
	void update(double delta);
	String getState();
}
