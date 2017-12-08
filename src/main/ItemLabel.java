package main;

import javax.swing.JLabel;

/**
 * A label that represents a slot in the inventory.
 * @author Logun DeLeon
 *
 */
public class ItemLabel extends JLabel {
	/**
	 * The index of the inventory that is represented
	 * by the label.
	 */
	private int inventoryIndex;
	
	/**
	 * If the slot is used for crafting.
	 */
	private boolean crafting;
	
	/**
	 * Constructor that sets the index to i
	 * and if the label is used for crafting.
	 * @param i the inventory index that the label represents.
	 * @param crafting if the slot is used for crafting.
	 */
	public ItemLabel(final int i, final boolean crafting) {
		super();
		inventoryIndex = i;
		this.crafting = crafting;
	}
	
	/**
	 * Gets the inventory index represented.
	 * @return the inventory index represented.
	 */
	public int getIndex() {
		return inventoryIndex;
	}
	
	/**
	 * Sets the index represented.
	 * @param i the index to be represented.
	 */
	public void setIndex(final int i) {
		inventoryIndex = i;
	}
	
	/**
	 * If the slot is used for crafting.
	 * @return if the slot is used for crafting.
	 */
	public boolean isCrafting() {
		return crafting;
	}
}
