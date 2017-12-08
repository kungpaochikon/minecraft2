package main;

import javax.swing.JLabel;

/**
 * A JLabel that represents the result of crafting.
 * @author Logun DeLeon
 *
 */
public class ResultLabel extends JLabel {
	/**
	 * The item that was crafted.
	 */
	private Item item;
	
	/**
	 * A constructor that sets the crafted item
	 * to the parameter.
	 * @param item The item crafted.
	 */
	public ResultLabel(final Item item) {
		this.item = item;
	}
	
	/**
	 * Gets the item crafted.
	 * @return the item crafted.
	 */
	public Item getItem() {
		return item;
	}
	
	/**
	 * Sets the crafted item.
	 * @param item the item thats crafted.
	 */
	public void setItem(final Item item) {
		this.item = item;
	}
	
	/**
	 * Sets the crafted item.
	 * @param type the type of the item crafted.
	 * @param id the ID of the item crafted.
	 */
	public void setItem(final int type, final int id) {
		this.item = new Item(type, id, 1);
	}
}
