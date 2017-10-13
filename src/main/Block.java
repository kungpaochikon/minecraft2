package main;

public class Block {
	
	private int wID;
	private int bID;
	private int light;
	private int integrity;
	
	public Block() {
		wID = 0;
		bID = 0;
		light = 0;
		integrity = Constants.INTEGRITIES[wID];
	}
	
	public Block(final int wid, final int bid, final int ll) {
		wID = wid;
		bID = bid;
		light = ll;
		integrity = Constants.INTEGRITIES[wID];
	}
	
	public Block(final int wid, final Block_Water block) {
		wID = wid;
		bID = block.getBID();
		light = block.getLight();
		integrity = Constants.INTEGRITIES[wID];
		
	}
	
	public Block(final int wid, final Block block){
		wID = wid;
		bID = block.getBID();
		light = block.getLight();
		integrity = Constants.INTEGRITIES[wID];
		
	}
	
	public void setWID(final int wid) {
		wID = wid;
		integrity = Constants.INTEGRITIES[wID];
	}
	
	public void setBID(final int bid) {
		bID = bid;
	}
	
	public void setLight(final int ll) {
		light = ll;
	}
	
	public int getWID() {
		return wID;
	}
	
	public int getBID() {
		return bID;
	}
	
	public int getLight() {
		return light;
	}
	
	public boolean isWater() {
		return this instanceof Block_Water;
	}
	
	public int getIntegrity() {
		return integrity;
	}
	
	public void hitBlock(final int damage) {
		if (damage < 0) {
			return;
		}
		integrity -= damage;
	}
	
	public void resetIntegrity() {
		integrity = Constants.INTEGRITIES[wID];
	}
}

