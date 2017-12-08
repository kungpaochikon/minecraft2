package main;

//import static org.junit.Assert.*;

import org.junit.Test;

/**
 * A unit test of WorldGrid.
 * @author Logun DeLeon
 *
 */
public class WorldGridTest {

	/**
	 * 
	 */
	@Test
	public void test() {
		WorldGrid world = new WorldGrid(64, 64, 64);
		world.setWID(-1, -1, 1);
		world.setWID(1, 1, -1);
		assert (world.getWID(-1, -1) == 1);
		world.setWID(100, 100, 1);
		world.getWID(100, 100);
		world.generate();
	}

}
