package main;

import java.util.Random;

/**
 * A cow entity.
 * @author Gary Fleming, Logun DeLeon, Alexander Duncanson
 *
 */
public class Cow extends Animal {
	/**
	 * The current state of the cow.
	 */
	private int state;
	
	/**
	 * A random number generator.
	 */
	private Random random;
	
	/**
	 * This constructor creates a cow with a set x and y
	 * location.
	 * @param xx the x coordinate of the cow.
	 * @param yy the y coordinate of the cow.
	 */
	public Cow(final double xx, final double yy) {
		super(xx, yy);
		width = 64;
		height = 64;
		state = 0;
		random = new Random();
	}
	
	/**
	 * The actions a cow takes during each iteration
	 * of the game loop.
	 * @param g the game the cow exists in.
	 */
	public void step(final Game g) {
		super.step(g);
	}

}
