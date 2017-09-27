package main;

public class Block_Water extends Block{
	private int waterLevel;
	private int waterLevelMax = 4;
	
	public Block_Water(int wid, int bid, int ll){
		super(wid,bid,ll);
		waterLevel = waterLevelMax;
	}
	
	public Block_Water(int wid, int bid, int ll, int wl){
		super(wid,bid,ll);
		waterLevel = wl;
	}
	
	public Block_Water(Block block){
		super(4,block.getBID(),block.getLight());
		waterLevel = waterLevelMax;
	}
	
	public int getWaterLevel(){ return waterLevel;}
	
	public void setWaterLevel(int wl){waterLevel = wl;}
	
	public void incWaterLevel(){waterLevel++;}
	
	public void decWaterLevel(){waterLevel--;}
	
	public int getWaterLevelMax(){ return waterLevelMax;}
}
