package main;

/**
 * The player's inventory. This class holds all items
 * that the player will gather on their journey through
 * Minecraft 2.
 * @author Gary Fleming
 *
 */
public class Inventory {
	   /**
	    * The actual inventory of the player.
	    */
	   private Item[] inventory;
	   
	   /**
	    * The maximum number of items in the player's
	    * inventory.
	    */
	   private int inventoryMax;
	   
	   /**
	    * The maximum number of items in the player's
	    * hotbar.
	    */
	   private int hotbarMax;
	   
	   /**
	    * The index of the item that the player
	    * is currently holding.
	    */
	   private int hotbarFocus;
	   
	   /**
	    * The number of items in the inventory.
	    */
	   private int size;
	   
	   /**
	    * The constructor instantiates all variables
	    * and sets them to their initial values.
	    */
	   public Inventory() {
		   this(30);
	   }
	   
	   /**
	    * The constructor instantiates all variables
	    * and sets them to their initial values.
	    * This constructor allows the user to create
	    * an inventory with a different max size.
	    * @param max The max size of the inventory.
	    */
	   public Inventory(final int max) {
		   inventoryMax = max;
		   inventory = new Item[inventoryMax];
		   hotbarMax = 10;
		   hotbarFocus = 0;
	   }
	   
	   /**
	    * Takes the item in index i out of the
	    * inventory.
	    * @param i the index to remove from.
	    */
	   public void remove(final int i) {
		   inventory[i] = null;
		   size--;
	   }
	   
	   /**
	    * Returns the Item that is in index i
	    * in the inventory.
	    * @param i the index to look at.
	    * @return the item in index i of the inventory.
	    */
	   public Item get(final int i) {
		   return inventory[i];
	   }
	   
	   /**
	    * Returns the Item that is in the hotbar focus.
	    * @return the item in the hotbar focus.
	    */
	   public Item getFocused() {
		   return inventory[hotbarFocus];
	   }
	   
	   /**
	    * Puts an item in the inventory.
	    * @param item the item to put in the inventory.
	    */
	   public void add(final Item item) {
		   for (int i = 0; i < inventoryMax; i++) {
			   if (inventory[i] == null) {
				   inventory[i] = item;
				   size++;
				   return;
			   }
		   }
	   }
	   
	   /**
	    * Adds an item to the inventory.
	    * @param item The item added to the inventory.
	    * @param i the index to put it in.
	    */
	   public void add(final Item item, final int i) {
		   inventory[i] = item;
		   size++;
	   }
	   /**
	    * Checks if an item is in the inventory.
	    * @param item the item we want to know is in the
	    * inventory or not.
	    * @return whether item is in the inventory.
	    */
	   public boolean has(final Item item) {
		   for (int i = 0; i < inventory.length; i++) {
			   if (inventory[i] != null && inventory[i].getType() == item.getType() && inventory[i].getId() == item.getId()) {
				   return true;
			   }
		   }
		   return false;
	   }
	   
	   /**
	    * Sets the max number of items in the inventory.
	    * @param i the number to set the max to.
	    */
	   public void setMax(final int i) {
		   inventoryMax = i;
	   }
	   
	   /**
	    * Gets the max number of items in the inventory.
	    * @return the max number of items in the inventory.
	    */
	   public int getMax() {
		   return inventoryMax;
	   }
	   
	   /**
	    * Sets the max number of items in the hotbar.
	    * @param i the max number of items in the hotbar.
	    */
	   public void setHotbarMax(final int i) {
		   hotbarMax = i;
	   }
	   
	   /**
	    * Gets the max number of items in the hotbar.
	    * @return the max number of items in the hotbar.
	    */
	   public int getHotbarMax() {
		   return hotbarMax;
	   }
	   /**
	    * Sets the hotbar focus.
	    * @param i the number to set the focus to.
	    */
	   public void setFocus(final int i) {
		   hotbarFocus = i;
	   }
	   
	   /**
	    * Gets the hotbar focus.
	    * @return the hotbar focus.
	    */
	   public int getFocus() {
		   return hotbarFocus;
	   }
	   
	   /**
	    * Gets the number of items in the inventory.
	    * @return the number of items in the inventory.
	    */
	   public int size() {
		   return size;
	   }
}
