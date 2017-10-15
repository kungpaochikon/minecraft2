package main;

public final class Constants {
	public static final int BLOCK_AIR = 0;
	public static final int BLOCK_DIRT = 1;
	public static final int BLOCK_GRASS = 2;
	public static final int BLOCK_LEAVES = 3;
	public static final int BLOCK_WATER = 4;
	public static final int BLOCK_DIAMOND_ORE = 5;
	public static final int BLOCK_STONE = 6;
	public static final int BLOCK_COBBLESTONE = 7;
	
	public static final int TYPE_BLOCK = 0;
	public static final int TYPE_BACK = 1;
	public static final int TYPE_TOOL = 2;
	public static final int TYPE_ITEM = 3;
	public static final int TYPE_FOOD = 4;
	public static final int TYPE_ENTITY = 5;
	
	public static final int ENTITY_PLAYER = 0;
	public static final int ENTITY_CHICKEN = 1;
	public static final int ENTITY_COW = 2;
	public static final int ENTITY_ZOMBIE = 3;
	
	public static final int BACK_AIR = 0;
	public static final int BACK_WOOD = 1;
	public static final int BACK_DIRT = 2;
	public static final int BACK_STONE = 3;
	
	public static final int PICKAXE = 0;
	public static final int AXE = 1;
	public static final int SWORD = 2;
	
	public static final int ITEM_DIAMOND = 0;
	
	public static final int APPLE = 0;
	public static final int RAW_CHICKEN = 1;
	public static final int RAW_BEEF = 2;
	
	public static final int[] INTEGRITIES = {Integer.MAX_VALUE, 50, 50, 20, Integer.MAX_VALUE, 100, 80, 80};
	
	public static final double GAME_HERTZ = 60.0;
	
	//Calculate how many ns each frame should take for our target game hertz.
	public static final double TIME_BETWEEN_UPDATES = 1000000000 / Constants.GAME_HERTZ;
	
	//At the very most we will update the game this many times before a new render.
    //If you're worried about visual hitches more than perfect timing, set this to 1.
    public static final int MAX_UPDATES_BEFORE_RENDER = 100;
	
  //If we are able to get as high as this FPS, don't render again.
    public static final double TARGET_FPS = 60;
    public static final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
    
    public static final long SWING_COOL_DOWN = 250;
}
