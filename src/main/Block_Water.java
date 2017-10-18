package main;
/**
 * A block of water. A water block is like other blocks,
 * except that they are not solid and they contain another
 * variable: water level. This determines how much water is
 * in the block. Also, water flows instead of staying in place.
 * @author Gary Fleming
 *
 */
public class Block_Water extends Block {
	/**
	 * The current level of water.
	 * This holds how much water there is
	 * in the block.
	 */
	private int waterLevel;
	
	/**
	 * The maximum amount of water in the
	 * block.
	 */
	private int waterLevelMax = 4;
	
	/**
	 * The constructor sets the world id, the background id, and
	 * the lighting.
	 * @param wid the world id of the water block.
	 * @param bid the background id of the water block.
	 * @param ll the lighting.
	 */
	public Block_Water(final int wid, final int bid, final int ll) {
		super(wid, bid, ll);
		waterLevel = waterLevelMax;
	}
	
	/**
	 * The constructor sets world id, background id,
	 * lighting, and water level.
	 * @param wid the world id of the water block.
	 * @param bid the background id of the water block.
	 * @param ll the lighting.
	 * @param wl the water level.
	 */
	public Block_Water(final int wid, final int bid,
			final int ll, final int wl) {
		super(wid, bid, ll);
		waterLevel = wl;
	}
	
	/**
	 * This constructor creates a water block from a block. It uses its
	 * BID and lighting and sets water level to max.
	 * @param block the base block.
	 */
	public Block_Water(final Block block) {
		super(4, block.getBID(), block.getLight());
		waterLevel = waterLevelMax;
	}
	
	/**
	 * Gets the water level.
	 * @return the water level.
	 */
	public int getWaterLevel() {
		return waterLevel;
	}
	
	/**
	 * Sets the water level.
	 * @param wl the value to set water level to.
	 */
	public void setWaterLevel(final int wl) {
		waterLevel = wl;
	}
	
	/**
	 * Increment water level by one.
	 */
	public void incWaterLevel() {
		waterLevel++;
	}
	
	/**
	 * Decrement water level by one.
	 */
	public void decWaterLevel() {
		waterLevel--;
	}
	
	/**
	 * Gets the max water level.
	 * @return the max water level.
	 */
	public int getWaterLevelMax() {
		return waterLevelMax;
	}
}
