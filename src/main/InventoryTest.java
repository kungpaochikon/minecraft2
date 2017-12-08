package main;

import org.junit.Test;
/**
 * Testing inventory class.
 * @author Logun DeLeon
 *
 */
public class InventoryTest {
	/**
	 * A sample inventory class to test on.
	 */
	private Inventory inv = new Inventory();

	/**
	 * Test for the has() method.
	 */
	@Test
	public void hasTest() {
		inv = new Inventory();
		inv.add(Constants.SWORD_ITEM);
		assert (inv.has(Constants.SWORD_ITEM));
		
		assert (!inv.has(Constants.APPLE_ITEM));
	}
	/**
	 * Test for correct size.
	 */
	@Test
	public void sizeTest() {
		inv = new Inventory();
		inv.add(Constants.APPLE_PIE_ITEM);
		inv.add(Constants.AXE_ITEM);
		inv.add(Constants.DIAMOND_ITEM, 6);
		inv.remove(6);
		assert (inv.size() == 2);
	}
	
	/**
	 * Add with only item parameter test.
	 */
	@Test
	public void addTestOneParam() {
		inv = new Inventory();
		inv.add(Constants.APPLE_PIE_ITEM);
		assert (inv.get(0).equals(Constants.APPLE_PIE_ITEM));
		assert (inv.get(1) == null);
	}
}
