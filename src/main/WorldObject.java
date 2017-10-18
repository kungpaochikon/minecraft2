package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * A world object is anything that is in the world.
 * This is the parent class of most game objects.
 * @author gary, logun, alex
 *
 */
public class WorldObject implements Comparable<WorldObject> {
	/**
	 * The x coordinate of the object.
	 */
	protected double x;
	/**
	 * The y coordinate of the object.
	 */
	protected double y;
	/**
	 * The change in the x coordinate per step.
	 */
	protected double xsp;
	/**
	 * The change in the y coordinate per step.
	 */
	protected double ysp;
	/**
	 * The value of gravity.
	 */
	protected double grav;
	/**
	 * The width of the object.
	 */
	protected double width;
	/**
	 * The height of the object.
	 */
	protected double height;
	/**
	 * If the object is solid.
	 */
	protected boolean solid;
	/**
	 * The angle of the object.
	 */
	protected double angle;
	/**
	 * If the object is destroyed.
	 */
	protected boolean destroy;
	/**
	 * The depth of the object.
	 */
	protected int depth;
	
	/**
	 * The constructor sets the default values to many fields
	 * and sets x and y to xx and yy.
	 * @param xx x coordinate
	 * @param yy y coordinate
	 */
	public WorldObject(final double xx, final double yy) {
		x = xx;
		y = yy;
		xsp = 0;
		ysp = 0;
		grav = 0;
		width = 32;
		height = 32;
		solid = false;
		angle = 0;
		destroy = false;
		depth = 0;
	}
	
	/**
	 * Gets the depth of the object.
	 * @return depth.
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * Sets the depth of the object.
	 * @param d value to set depth to.
	 */
	public void setDepth(final int d) {
		depth = d;
	}
	
	/**
	 * @return If the object is destroyed or not.
	 */
	public boolean destroyed() {
		return destroy;
	}
	
	/**
	 * Sets the object to destroyed.
	 */
	public void destroy() {
		destroy = true;
	}
	
	/**
	 * Sets the x coordinate.
	 * @param xx x coordinate
	 */
	public void setX(final double xx) {
		x = xx;
	}
	
	/**
	 * Sets the y coordinate.
	 * @param yy y coordinate
	 */
	public void setY(final double yy) {
		y = yy;
	}
	
	/**
	 * Sets the change in x.
	 * @param xx xsp
	 */
	public void setXsp(final double xx) {
		xsp = xx;
	}
	
	/**
	 * Sets the change in y.
	 * @param yy ysp
	 */
	public void setYsp(final double yy) {
		ysp = yy;
	}
	
	/**
	 * Increases x by some amount.
	 * @param xx move this amount.
	 */
	public void moveX(final double xx) {
		x += xx;
	}
	
	/**
	 * Increases y by some amount.
	 * @param yy move this amount.
	 */
	public void moveY(final double yy) {
		y += yy;
	}
	
	/**
	 * Adds gravity to change in y.
	 */
	public void doGrav() {
		ysp += grav;
	}
	
	/**
	 * What the object does during each iteration
	 * of the loop.
	 * @param g the game.
	 */
	public void step(final Game g) {
		//doGrav();
		x += xsp;
		y += ysp;
	}
	
	/**
	 * Sets the gravity.
	 * @param g gravity
	 */
	public void setGrav(final double g) {
		grav = g;
	}
	
	/**
	 * Sets the width.
	 * @param ww width
	 */
	public void setWidth(final double ww) {
		width = ww;
	}
	
	/**
	 * Sets the height.
	 * @param hh height
	 */
	public void setHeight(final double hh) {
		height = hh;
	}
	
	/**
	 * Sets if the object is solid.
	 * @param ss if the object is solid.
	 */
	public void setSolid(final boolean ss) {
		solid = ss;
	}
	
	/**
	 * Sets the angle.
	 * @param ang angle
	 */
	public void setAngle(final double ang) {
		angle = ang;
	}
	
	/**
	 * Gets the x coordinate.
	 * @return x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Gets the y coordinate.
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Gets change in x.
	 * @return xsp
	 */
	public double getXsp() {
		return xsp;
	}
	
	/**
	 * Gets change in y.
	 * @return ysp
	 */
	public double getYsp() {
		return ysp;
	}
	
	/**
	 * Gets gravity.
	 * @return gravity
	 */
	public double getGrav() {
		return grav;
	}
	
	/**
	 * Gets width.
	 * @return width
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Gets height.
	 * @return height
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Gets angle.
	 * @return angle
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * @return if the object is solid or not.
	 */
	public boolean isSolid() {
		return solid;
	}

	@Override
	public int compareTo(final WorldObject arg0) {
		return depth - arg0.depth;
	}
	
	
}
