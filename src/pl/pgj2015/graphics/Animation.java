package pl.pgj2015.graphics;

import java.util.List;

import processing.core.PImage;

public interface Animation {
	
	public void setImages(List<PImage> images);
	public PImage getCurrentImage();
	public void update(double delta);
}
