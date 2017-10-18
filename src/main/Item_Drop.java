package main;

/**
 * The icons the player picks up after they destroy a block,
 * kill an entity, etc.
 * @author Gary Fleming
 *
 */
public class Item_Drop extends WorldObject {
	/**
	 * The type of item.
	 */
	private int type;
	
	/**
	 * The id of the item.
	 */
	private int id;
	
	/**
	 * The amount of the item.
	 */
	private int count;
	
	/**
	 * The constructor sets the x and y location of the drop. It
	 * also sets the type, id, and count of the item.
	 * @param xx the x location of the item.
	 * @param yy the y location of the item.
	 * @param t the type of the item.
	 * @param i the id of the item.
	 * @param c the amount of the item.
	 */
	public Item_Drop(final double xx, final double yy, final int t,
			final int i, final int c) {
		super(xx, yy);
		width = 32;
		height = 32;
		type = t;
		id = i;
		count = c;
	}
	
	/**
	 * Gets the type of the item drop.
	 * @return the type of the item drop.
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Gets the id of the item drop.
	 * @return the id of the item drop.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the amount of the item.
	 * @return the number of that item.
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Sets the type of the item drop.
	 * @param t the value to set type to.
	 */
	public void setType(final int t) {
		type = t;
	}
	
	/**
	 * Sets the id of the item drop.
	 * @param i the value to set id to.
	 */
	public void setId(final int i) {
		id = i;
	}
	
	/**
	 * Sets the amount of an item drop.
	 * @param c the value to set count to.
	 */
	public void setCount(final int c) {
		count = c;
	}
	
	/**
	 * Increment the count by a value.
	 * @param c the value to increment count by.
	 */
	public void changeCount(final int c) {
		count += c;
	}
	
	/**
	 * The actions taken by the ItemDrop during
	 * each iteration of the game. It stops if
	 * it starts to go too slowly.
	 * @param g the game the item drop is in.
	 */
	public void step(final Game g) {
		if (xsp < 0.1) {
			xsp = 0;
		}
		if (ysp < 0.1) {
			ysp = 0;
		}
		super.step(g);
	}

}
