package main;

/**
 * A chicken entity.
 * @author Gary Fleming, Logun DeLeon, Alex Duncanson
 *
 */
public class Chicken extends Animal {
	/**
	 * The state of the chicken.
	 */
	//private int state;
	
	/**
	 * A random number generator.
	 */
	//private Random random;
	
	/**
	 * Creates a chicken entity with an x and y location.
	 * @param xx the x location of the chicken.
	 * @param yy the y location of the chicken.
	 */
	public Chicken(final double xx, final double yy) {
		super(xx, yy);
		width = 32;
		height = 32;
	}
	
	/**
	 * The actions a chicken takes during each iteration
	 * of the game loop.
	 * @param g the game the chicken belongs to.
	 */
	public void step(final Game g) {
		super.step(g);
	}

}
