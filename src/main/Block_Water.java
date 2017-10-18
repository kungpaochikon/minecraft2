package main;
/**
 * 
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
	 * The constructor.
	 * @param wid
	 * @param bid
	 * @param ll
	 */
	public Block_Water(final int wid, final int bid, final int ll) {
		super(wid, bid, ll);
		waterLevel = waterLevelMax;
	}
	
	public Block_Water(int wid, int bid, int ll, int wl){
		super(wid, bid, ll);
		waterLevel = wl;
	}
	
	public Block_Water(Block block) {
		super(4, block.getBID(), block.getLight());
		waterLevel = waterLevelMax;
	}
	
	public int getWaterLevel() {
		return waterLevel;
	}
	
	public void setWaterLevel(int wl) {
		waterLevel = wl;
	}
	
	public void incWaterLevel() {
		waterLevel++;
	}
	
	public void decWaterLevel() {
		waterLevel--;
	}
	
	public int getWaterLevelMax(){
		return waterLevelMax;
	}
}
