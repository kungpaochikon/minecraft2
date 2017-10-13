<<<<<<< HEAD
package main;

public class Block {
	private int wID;
	private int bID;
	private int light;
	
	public Block(){
		wID = 0;
		bID = 0;
		light = 0;
	}
	
	public Block(int wid, int bid, int ll){
		wID = wid;
		bID = bid;
		light = ll;
	}
	
	public Block(int wid, Block_Water block){
		wID = wid;
		bID = block.getBID();
		light = block.getLight();
		
	}
	
	public Block(int wid, Block block){
		wID = wid;
		bID = block.getBID();
		light = block.getLight();
		
	}
	
	public void setWID(int wid){
		wID = wid;
	}
	
	public void setBID(int bid){
		bID = bid;
	}
	
	public void setLight(int ll){
		light = ll;
	}
	
	public int getWID(){
		return wID;
	}
	
	public int getBID(){
		return bID;
	}
	
	public int getLight(){
		return light;
	}
	
	public boolean isWater(){
		return this instanceof Block_Water;
	}
}
=======
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

<<<<<<< HEAD
>>>>>>> 4f7bfb7e869ece17d898350ba3b3462c23fd44e3
=======
>>>>>>> 86f9c217b2b4ecb4f2d9904efdee5a841559b624
