/*
 * */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.*;


public class GRectAnimated extends GRect implements Runnable {

	public GRectAnimated(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.setFilled(true);
	}
	
	public void run () {
		
		while (isAnimated) {
			int pause = rgen.nextInt(1, 500);
			
			this.setFillColor(Color.BLACK);
			pause(pause);
			
			this.setFillColor(Color.WHITE);
			pause(pause);
			
		}
		
		
		
	}
	
	public void setOffAnimation () {
		
		isAnimated = false ;
	}
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private boolean isAnimated = true;
	

}
