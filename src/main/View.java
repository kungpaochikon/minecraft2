package main;

import java.util.Random;
/**
 * The game view. This controls the view shaking when
 * the player character falls from high up.
 * @author Gary Fleming, Logun DeLeon, Alex Duncanson
 *
 */
public class View {
	/**
	 * The normal x coordinate of the view.
	 */
	private double viewX;
	
	/**
	 * The normal y coordinate of the view.
	 */
	private double viewY;
	
	/**
	 * The actual x coordinate of the view.
	 */
	private double viewXFinal;
	
	/**
	 * The actual y coordinate of the view.
	 */
	private double viewYFinal;
	
	/**
	 * The width of the view.
	 */
	private int viewW;
	
	/**
	 * The height of the view.
	 */
	private int viewH;
	
	/**
	 * The x offset of the view caused by
	 * the screen shaking.
	 */
	private int viewShakeX;
	
	/**
	 * The y offset of the view caused by
	 * the screen shaking.
	 */
	private int viewShakeY;
	
	/**
	 * The amount of time that the
	 * screen shakes.
	 */
	private int viewShakeTime;
	
	/**
	 * The magnitude at which the screen
	 * shakes.
	 */
   	private int viewShakeMag;
   	
   	/**
   	 * If the view is shaking.
   	 */
   	private boolean viewShaking;
   	
   	/**
   	 * A random number generator.
   	 */
   	private Random random;
   	
   	/**
   	 * A constructor that sets everything to default values.
   	 * All numerical values are set to 0. viewShaking is false.
   	 */
   	public View() {
   		setViewX(0);
   		setViewY(0);
   		setViewXFinal(0);
   		setViewYFinal(0);
   		setViewW(0);
   		setViewH(0);
   		setViewShakeX(0);
   		setViewShakeY(0);
   		setViewShakeTime(0);
   		setViewShakeMag(0);
   		setViewShaking(false);
   		random = new Random();
   	}
   	
   	/**
   	 * A constructor that allows you to set viewX, viewY, width, and height.
   	 * @param x the value of viewX.
   	 * @param y the value of viewY.
  	 * @param w the width of the view.
   	 * @param h the height of the view.
   	 */
   	public View(final double x, final double y, final int w, final int h) {
   		this();
   		setViewX(x);
   		setViewY(y);
   		setViewW(w);
   		setViewH(h);
   	}
   	
   	/**
   	 * The actions taken by the view during each iteration of
   	 * the game loop. Shakes the screen if necessary.
   	 */
   	public void step() {
 	   //View Shake
 	   if (viewShaking) {
 		   viewShakeTime--;
 		   if (viewShakeTime < 1) {
 			   viewShaking = false;
 		   }
 		   viewShakeX = random.nextInt(viewShakeMag * 2 + 1) - viewShakeMag;
 		   viewShakeY = random.nextInt(viewShakeMag * 2 + 1) - viewShakeMag;
 	   } else {
 		   viewShakeX = 0;
 		   viewShakeY = 0;
 	   }
       viewXFinal = viewX + viewShakeX;
       viewYFinal = viewY + viewShakeY;
       viewXFinal = Math.round(viewXFinal);
       viewYFinal = Math.round(viewYFinal);
   		
   	}
   	
    /*******************************************************************
     * 
     * View Shake
     * -----------
     * Init the camera shake.
     * Will change to a list later to allow for multiple shakes
     * @param time the amount of time the screen shakes.
     * @param mag the magnitude that the screen shakes.
     * 
     *******************************************************************/
    public void viewShake(final int time, final int mag) {
 	   viewShakeTime = time;
 	   viewShakeMag = mag;
 	   viewShaking = true;
    }
    
    /**
     * Gets the viewX.
     * @return the viewX.
     */
	public double getViewX() {
		return viewX;
	}
	
	/**
	 * Sets the viewX.
	 * @param viewX the value to set viewX to.
	 */
	public void setViewX(final double viewX) {
		this.viewX = viewX;
	}
	
	/**
	 * Gets the viewY.
	 * @return the viewY
	 */
	public double getViewY() {
		return viewY;
	}
	
	/**
	 * Sets the viewY.
	 * @param viewY the value to set viewY to.
	 */
	public void setViewY(final double viewY) {
		this.viewY = viewY;
	}
	
	/**
	 * Gets the actual viewX.
	 * @return the actual viewX.
	 */
	public double getViewXFinal() {
		return viewXFinal;
	}
	
	/**
	 * Set the actual viewX.
	 * @param viewXFinal the value to set this.viewXFinal to.
	 */
	public void setViewXFinal(final double viewXFinal) {
		this.viewXFinal = viewXFinal;
	}
	
	/**
	 * Get the actual viewY.
	 * @return the actual viewY.
	 */
	public double getViewYFinal() {
		return viewYFinal;
	}
	
	/**
	 * Set the actual viewY.
	 * @param viewYFinal the value to set this.viewYFinal to.
	 */
	public void setViewYFinal(final double viewYFinal) {
		this.viewYFinal = viewYFinal;
	}
	
	/**
	 * Get the width of the view.
	 * @return the width of the view.
	 */
	public int getViewW() {
		return viewW;
	}
	
	/**
	 * Set the width of the view.
	 * @param viewW the value to set the width
	 * of the view to.
	 */
	public void setViewW(final int viewW) {
		this.viewW = viewW;
	}
	
	/**
	 * Get the height of the view.
	 * @return the height of the view.
	 */
	public int getViewH() {
		return viewH;
	}
	
	/**
	 * Set the height of the view.
	 * @param viewH the value to set the height to.
	 */
	public void setViewH(final int viewH) {
		this.viewH = viewH;
	}
	
	/**
	 * Get the x offset due to shaking.
	 * @return the x offset due to shaking.
	 */
	public int getViewShakeX() {
		return viewShakeX;
	}
	
	/**
	 * Set the x offset due to shaking.
	 * @param viewShakeX the value to set this.viewShakeX to.
	 */
	public void setViewShakeX(final int viewShakeX) {
		this.viewShakeX = viewShakeX;
	}
	
	/**
	 * Get the y offset due to shaking.
	 * @return the y offset due to shaking.
	 */
	public int getViewShakeY() {
		return viewShakeY;
	}
	
	/**
	 * Set the y offset due to shaking.
	 * @param viewShakeY the value to set this.viewShakeY to.
	 */
	public void setViewShakeY(final int viewShakeY) {
		this.viewShakeY = viewShakeY;
	}
	
	/**
	 * Gets the time the view shakes for.
	 * @return the time the view shakes for.
	 */
	public int getViewShakeTime() {
		return viewShakeTime;
	}
	
	/**
	 * Set the time the view shakes for.
	 * @param viewShakeTime the value to set this.viewShakeTime to.
	 */
	public void setViewShakeTime(final int viewShakeTime) {
		this.viewShakeTime = viewShakeTime;
	}
	
	/**
	 * Gets the magnitude that the view shakes at.
	 * @return the magnitude of the view shake.
	 */
	public int getViewShakeMag() {
		return viewShakeMag;
	}
	
	/**
	 * Set the magnitude of the view shake.
	 * @param viewShakeMag the value to set this.viewShakeMag to.
	 */
	public void setViewShakeMag(final int viewShakeMag) {
		this.viewShakeMag = viewShakeMag;
	}
	
	/**
	 * Returns if the view is shaking or not.
	 * @return if the view is shaking or not.
	 */
	public boolean isViewShaking() {
		return viewShaking;
	}
	
	/**
	 * Sets whether the view is shaking or not.
	 * @param viewShaking the value to set viewShaking to.
	 */
	public void setViewShaking(final boolean viewShaking) {
		this.viewShaking = viewShaking;
	}
	
}
