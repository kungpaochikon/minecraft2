package main;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * An item that can be picked up by the player.
 * @author gary, logun, alex
 *
 */
public class Item {
	/**
	 * The type of item.
	 */
	private int type;
	
	/**
	 * The id of the item.
	 */
	private int id;
	
	/**
	 * The number of this item.
	 */
	private int count;
	
	/**
	 * The constructor creates a new Item of type t,
	 * with id i, and count c. 
	 * @param t the type of item.
	 * @param i the id of the item.
	 * @param c the number of the item.
	 */
	public Item(final int t, final int i, final int c) {
		type = t;
		id = i;
		count = c;
		//type = ItemType.BLOCK;
	}
	
	/**
	 * Gets the type of item.
	 * @return the item type.
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Gets the item's id.
	 * @return the item's id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the count of the item.
	 * @return the item count.
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Sets the type of the item.
	 * @param t the value to set type to.
	 */
	public void setType(final int t) {
		type = t;
	}
	
	/**
	 * Sets the id of the item.
	 * @param i the value to set id to.
	 */
	public void setId(final int i) {
		id = i;
	}
	
	/**
	 * Sets the count of the item.
	 * @param c the value to set count to.
	 */
	public void setCount(final int c) {
		count = c;
	}
	
	/**
	 * Increments the count by a certain amount.
	 * @param c the amount to increment count by.
	 */
	public void changeCount(final int c) {
		count += c;
	}
	
}
