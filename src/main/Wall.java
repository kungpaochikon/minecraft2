package main;
/**
 * Not currently used in the project.
 * @author Logun DeLeon
 *
 */
public class Wall extends WorldObject {
	/**
	 * Default constructor sets x, y, width, and height.
	 * @param xx the x coordinate.
	 * @param yy the y coordinate.
	 * @param ww the width.
	 * @param hh the height.
	 */
	public Wall(final float xx, final float yy,
			final float ww, final float hh) {
		super(xx, yy);
		solid = true;
	}
}
