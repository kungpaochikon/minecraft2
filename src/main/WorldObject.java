package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author gary, logun, alex
 *
 */
public class WorldObject implements Comparable<WorldObject> {
	/**
	 * 
	 */
	protected double x;
	/**
	 * 
	 */
	protected double y;
	/**
	 * 
	 */
	protected double xsp;
	/**
	 * 
	 */
	protected double ysp;
	/**
	 * 
	 */
	protected double grav;
	/**
	 * 
	 */
	protected double width;
	/**
	 * 
	 */
	protected double height;
	/**
	 * 
	 */
	protected boolean solid;
	/**
	 * 
	 */
	protected double angle;
	/**
	 * 
	 */
	protected boolean destroy;
	/**
	 * 
	 */
	protected int depth;
	
	/**
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
	 * @return depth
	 */
	public int getDepth() {
		return depth;
	}
	
	/**
	 * @param d depth
	 */
	public void setDepth(final int d) {
		depth = d;
	}
	
	/**
	 * @return destroy
	 */
	public boolean destroyed() {
		return destroy;
	}
	
	/**
	 * 
	 */
	public void destroy() {
		destroy = true;
	}
	
	/**
	 * @param xx x coordinate
	 */
	public void setX(final double xx) {
		x = xx;
	}
	
	/**
	 * @param yy y coordinate
	 */
	public void setY(final double yy) {
		y = yy;
	}
	
	/**
	 * @param xx xsp
	 */
	public void setXsp(final double xx) {
		xsp = xx;
	}
	
	/**
	 * @param yy ysp
	 */
	public void setYsp(final double yy) {
		ysp = yy;
	}
	
	/**
	 * @param xx move x
	 */
	public void moveX(final double xx) {
		x += xx;
	}
	
	/**
	 * @param yy move y
	 */
	public void moveY(final double yy) {
		y += yy;
	}
	
	/**
	 * 
	 */
	public void doGrav() {
		ysp += grav;
	}
	
	/**
	 * @param g game step
	 */
	public void step(final Game g) {
		//doGrav();
		x += xsp;
		y += ysp;
	}
	
	/**
	 * @param g gravity
	 */
	public void setGrav(final double g) {
		grav = g;
	}
	
	/**
	 * @param ww width
	 */
	public void setWidth(final double ww) {
		width = ww;
	}
	
	/**
	 * @param hh height
	 */
	public void setHeight(final double hh) {
		height = hh;
	}
	
	/**
	 * @param ss solid
	 */
	public void setSolid(final boolean ss) {
		solid = ss;
	}
	
	/**
	 * @param ang angle
	 */
	public void setAngle(final double ang) {
		angle = ang;
	}
	
	/**
	 * @return x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * @return xsp
	 */
	public double getXsp() {
		return xsp;
	}
	
	/**
	 * @return ysp
	 */
	public double getYsp() {
		return ysp;
	}
	
	/**
	 * @return gravity
	 */
	public double getGrav() {
		return grav;
	}
	
	/**
	 * @return width
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * @return height
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * @return angle
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * @return solid
	 */
	public boolean isSolid() {
		return solid;
	}

	@Override
	public int compareTo(final WorldObject arg0) {
		return depth - arg0.depth;
	}
	

	
}
