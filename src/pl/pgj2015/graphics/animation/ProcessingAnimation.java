package pl.pgj2015.graphics.animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.pgj2015.main.ProcessingMain;
import processing.core.PImage;

public class ProcessingAnimation implements Animation {
	private static final double UPDATES_BY_SECOND = 4;
	private static final double ONE_FRAME_TIME = ProcessingMain.MILISECONDS_IN_TIME_UNIT
			/ UPDATES_BY_SECOND;
	private List<PImage> images;
	private double timeFromLastChange = 0;
	private int currentImageIndex;
	
	public ProcessingAnimation(List<PImage> images){
		setImages(images);
	}
	
	@Override
	public void setImages(List<PImage> images) {
		this.images = new ArrayList<PImage>(images.size());
		for(PImage image : images){
			this.images.add(image);
		}
		currentImageIndex = 0;
	}

	@Override
	public PImage getCurrentImage() {
		if (timeFromLastChange >= ONE_FRAME_TIME) {
			updateImageIndex();
		}
		return images.get(currentImageIndex);
	}

	@Override
	public void update(double delta) {
		timeFromLastChange += delta;
	}

	private void updateImageIndex() {
		currentImageIndex += 1;
		currentImageIndex = currentImageIndex % images.size();
	}
}
