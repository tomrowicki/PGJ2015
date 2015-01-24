package pl.pgj2015.graphics.animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PImage;

public class ProcessingAnimation implements Animation{
	private static final double ONE_FRAME_TIME = 0.25;
	private List<PImage> images;
	private double timeFromLastChange = 0;
	private int currentImageIndex;
	
	@Override
	public void setImages(List<PImage> images) {
		this.images = new ArrayList<PImage>(images.size());
		Collections.copy(this.images, images);
		currentImageIndex = 0;
	}

	@Override
	public PImage getCurrentImage() {
		if(timeFromLastChange >= ONE_FRAME_TIME){
			updateImageIndex();
		}
		return images.get(currentImageIndex);
	}

	@Override
	public void update(double delta) {
		timeFromLastChange += delta;
	}
	
	private void updateImageIndex(){
		currentImageIndex += 1;
		currentImageIndex = currentImageIndex % images.size();
	}
}
