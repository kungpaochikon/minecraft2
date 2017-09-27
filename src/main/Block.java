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
