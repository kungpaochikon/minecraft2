package main;
/**
 * A list of constants to be used throughout
 * Minecraft 2.
 * @author Logun DeLeon
 *
 */
public final class Constants {
	/**
	 * Private constructor to disallow instances
	 * of Constants.
	 */
	private Constants() {
		super();
	}
	/**
	 * The index of an air blocks in the block array.
	 */
	public static final int BLOCK_AIR = 0;
	
	/**
	 * The index of dirt blocks in the block array.
	 */
	public static final int BLOCK_DIRT = 1;
	
	/**
	 * The index of grass blocks in the block array.
	 */
	public static final int BLOCK_GRASS = 2;
	
	/**
	 * The index of leaf blocks in the block array.
	 */
	public static final int BLOCK_LEAVES = 3;
	
	/**
	 * The index of water blocks in the block array.
	 */
	public static final int BLOCK_WATER = 4;
	
	/**
	 * The index of diamond ore blocks in the block array.
	 */
	public static final int BLOCK_DIAMOND_ORE = 5;
	
	/**
	 * The index of stone blocks in the block array.
	 */
	public static final int BLOCK_STONE = 6;
	
	/**
	 * The index of cobblestone blocks in the block array.
	 */
	public static final int BLOCK_COBBLESTONE = 7;
	
	/**
	 * The index of the block array in the type array.
	 */
	public static final int TYPE_BLOCK = 0;
	
	/**
	 * The index of the background array in the type array.
	 */
	public static final int TYPE_BACK = 1;
	
	/**
	 * The index of the tool array in the type array.
	 */
	public static final int TYPE_TOOL = 2;
	
	/**
	 * The index of the item array in the type array.
	 */
	public static final int TYPE_ITEM = 3;
	
	/**
	 * The index of the food array in the type array.
	 */
	public static final int TYPE_FOOD = 4;
	
	/**
	 * The index of the entity array in the type array.
	 */
	public static final int TYPE_ENTITY = 5;
	
	/**
	 * The index of the player in the entity array.
	 */
	public static final int ENTITY_PLAYER = 0;
	
	/**
	 * The index of chickens in the entity array.
	 */
	public static final int ENTITY_CHICKEN = 1;
	
	/**
	 * The index of cows in the entity array.
	 */
	public static final int ENTITY_COW = 2;
	
	/**
	 * The index of zombies in the entity array.
	 */
	public static final int ENTITY_ZOMBIE = 3;
	
	/**
	 * The index of an air background in the
	 * background array.
	 */
	public static final int BACK_AIR = 0;
	
	/**
	 * The index of a wood background in the
	 * background array.
	 */
	public static final int BACK_WOOD = 1;
	
	/**
	 * The index of a dirt background in the
	 * background array.
	 */
	public static final int BACK_DIRT = 2;
	
	/**
	 * The index of a stone background in the
	 * background array.
	 */
	public static final int BACK_STONE = 3;
	
	/**
	 * The index of a pickaxe in the tools array.
	 */
	public static final int PICKAXE = 0;
	
	/**
	 * The index of an axe in the tools array.
	 */
	public static final int AXE = 1;
	
	/**
	 * The index of a sword in the tools array.
	 */
	public static final int SWORD = 2;
	
	/**
	 * The index of a diamond in the item array.
	 */
	public static final int ITEM_DIAMOND = 0;
	
	/**
	 * The index of an apple in the food array.
	 */
	public static final int APPLE = 0;
	
	/**
	 * The index of raw chicken in the food array.
	 */
	public static final int RAW_CHICKEN = 1;
	
	/**
	 * The index of raw beef in the food array.
	 */
	public static final int RAW_BEEF = 2;
	
	/**
	 * The array of block integrities. The integrities are in order
	 * of the indices of the blocks in the blocks array (i.e. the value
	 * at index 0 corresponds to BLOCK_AIR which equals 0).
	 */
	public static final int[] INTEGRITIES =
		{Integer.MAX_VALUE, 50, 50, 20, Integer.MAX_VALUE, 100, 80, 80};
	
	/**
	 * The frame rate of the game.
	 */
	public static final double GAME_HERTZ = 60.0;
	
	/**
	 * Calculate how many ns each frame should take for our target
	 * game hertz.
	 */
	public static final double TIME_BETWEEN_UPDATES =
			1000000000 / Constants.GAME_HERTZ;
	
	/**
	 * At the very most we will update the game this many times before
	 * a new render. If you're worried about visual hitches more than
	 * perfect timing, set this to 1.
	 */
    public static final int MAX_UPDATES_BEFORE_RENDER = 100;
	
    /**
     * The FPS the game prefers to run at.
     */
    public static final double TARGET_FPS = 60;
    
    /**
     * The ideal time between each render.
     */
    public static final double TARGET_TIME_BETWEEN_RENDERS =
    		1000000000 / TARGET_FPS;
    
    /**
     * The number of milliseconds before the player can swing again
     * after swinging.
     */
    public static final long SWING_COOL_DOWN = 250;
    
    public static final Item AIR_ITEM = new Item(TYPE_BLOCK, BLOCK_AIR, 1);
    
    public static final Item DIRT_ITEM = new Item(TYPE_BLOCK, BLOCK_DIRT, 1);
    
    public static final Item GRASS_ITEM = new Item(TYPE_BLOCK, BLOCK_GRASS, 1);
    
    public static final Item SWORD_ITEM = new Item(TYPE_TOOL, SWORD, 1);
    
    public static final Item DIAMOND_ITEM = new Item(TYPE_ITEM, ITEM_DIAMOND, 1);
    
    public static final Item WOOD_ITEM = new Item(TYPE_BACK, BACK_WOOD, 1);
}
