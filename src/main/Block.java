package main;

/**
 * @author gary, logun, alex
 *
 */
public class Block {
	
	/**
	 * 
	 */
	private int wID;
	/**
	 * 
	 */
	private int bID;
	/**
	 * 
	 */
	private int light;
	/**
	 * 
	 */
	private int integrity;
	
	/**
	 * 
	 */
	public Block() {
		wID = 0;
		bID = 0;
		light = 0;
		integrity = Constants.INTEGRITIES[wID];
	}
	
	/**
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
	 * @param wid width id
	 * @param block block id
	 */
	public Block(final int wid, final Block_Water block) {
		wID = wid;
		bID = block.getBID();
		light = block.getLight();
		integrity = Constants.INTEGRITIES[wID];
		
	}
	
	/**
	 * @param wid width id
	 * @param block block id
	 */
	public Block(final int wid, final Block block) {
		wID = wid;
		bID = block.getBID();
		light = block.getLight();
		integrity = Constants.INTEGRITIES[wID];
		
	}
	
	/**
	 * @param wid width id
	 */
	public void setWID(final int wid) {
		wID = wid;
		integrity = Constants.INTEGRITIES[wID];
	}
	
	/**
	 * @param bid block id
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
	 * @param damage block damage
	 */
	public void hitBlock(final int damage) {
		if (damage < 0) {
			return;
		}
		integrity -= damage;
	}
	
	/**
	 * 
	 */
	public void resetIntegrity() {
		integrity = Constants.INTEGRITIES[wID];
	}
}

