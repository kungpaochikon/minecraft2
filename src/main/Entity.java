package main;
/**
 * A moving and living object. It has hitpoints and can
 * be killed. 
 * @author Gary Fleming, Logun DeLeon, Alex Duncanson
 *
 */
public class Entity extends WorldObject {
	/**
	 * The maximum number of hit points.
	 * Max health.
	 */
	protected int hp_max;
	
	/**
	 * The current number of hit points.
	 */
	protected int hp;
	
	/**
	 * If the entity is alive or not.
	 */
	protected boolean alive;
	
	/**
	 * The constructor initializes all fields to their
	 * initial values.
	 * @param xx the x coordinate of the entity.
	 * @param yy the y coordinate of the entity.
	 */
	public Entity(final double xx, final double yy) {
		super(xx, yy);
		width = 32;
		height = 32;
		xsp = 0;
		ysp = 0;
		grav = 1;
		solid = false;
		hp_max = 3;
		hp = hp_max;
		alive = true;
	}
	
	/**
	 * Makes the entity dead.
	 */
	public void kill() {
		hp = 0;
		alive = false;
	}
	
	/**
	 * Returns whether the entity is alive or not.
	 * @return if the entity is alive or not.
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * The actions taken by the entity during
	 * each iteration through the game loop.
	 * @param g the game the entity is a part of.
	 */
	public void step(final Game g) {
		if (alive) {
			super.step(g);
		}
		if (hp < 1) {
			alive = false;
		}
	}
	
	/**
	 * Gets the current hit points of the
	 * entity.
	 * @return the current hp.
	 */
	public int getHP() {
		return hp;
	}
	
	/**
	 * Sets the hit points of the entity.
	 * @param h the value to set hp to.
	 */
	public void setHP(final int h) {
		hp = h;
	}
}
