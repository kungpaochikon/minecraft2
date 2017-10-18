
package main;

/**
 * @author gary, logun, alex
 *
 */
public class Block {
	
	/**
	 * The world id. The kind of
	 * block it is.
	 */
	private int wID;
	/**
	 * The background id. The kind of background
	 * is behind the block.
	 */
	private int bID;
	/**
	 * The lighting.
	 */
	private int light;
	/**
	 * The integrity of the block.
	 */
	private int integrity;
	
	/**
	 * This constructor sets all values to a default of 0.
	 * This makes it an air block.
	 */
	public Block() {
		wID = 0;
		bID = 0;
		light = 0;
		integrity = Constants.INTEGRITIES[wID];
	}
	
	/**
	 * This constructor allows you to set the id and background id.
	 * It also allows you to set lighting.
	 * @param wid world ID
	 * @param bid block ID
	 * @param ll light
	 */
	public Block(final int wid, final int bid, final int ll) {
		wID = wid;
		bID = bid;
		light = ll;
		integrity = Constants.INTEGRITIES[wID];
	}
	
	/**
	 * This constructor creates a block of a certain wid with the
	 * background and lighting of a water block.
	 * @param wid world id.
	 * @param block the base water block.
	 */
	public Block(final int wid, final Block_Water block) {
		wID = wid;
		bID = block.getBID();
		light = block.getLight();
		integrity = Constants.INTEGRITIES[wID];
		
	}
	
	/**
	 * This constructor creates a block of a certain wid with
	 * the background and lighting of another block.
	 * @param wid world id
	 * @param block the base block.
	 */
	public Block(final int wid, final Block block) {
		wID = wid;
		bID = block.getBID();
		light = block.getLight();
		integrity = Constants.INTEGRITIES[wID];
		
	}
	
	/**
	 * Set the world id.
	 * @param wid world id
	 */
	public void setWID(final int wid) {
		wID = wid;
		integrity = Constants.INTEGRITIES[wID];
	}
	
	/**
	 * Set the background id.
	 * @param bid background id
	 */
	public void setBID(final int bid) {
		bID = bid;
	}
	
	/**
	 * @param ll light
	 */
	public void setLight(final int ll) {
		light = ll;
	}
	
	/**
	 * @return world id
	 */
	public int getWID() {
		return wID;
	}
	
	/**
	 * @return block id
	 */
	public int getBID() {
		return bID;
	}
	
	/**
	 * @return light
	 */
	public int getLight() {
		return light;
	}
	
	/**
	 * @return if the block is water or not
	 */
	public boolean isWater() {
		return this instanceof Block_Water;
	}
	
	/**
	 * @return the integrity of the block
	 */
	public int getIntegrity() {
		return integrity;
	}
	
	/**
	 * Decrement integrity by damage.
	 * @param damage how much integrity is lost on
	 * a hit.
	 */
	public void hitBlock(final int damage) {
		if (damage < 0) {
			return;
		}
		integrity -= damage;
	}
	
	/**
	 * Reset the integrity to its starting value.
	 */
	public void resetIntegrity() {
		integrity = Constants.INTEGRITIES[wID];
	}
}
