package main;
/**
 * A class that represents the recipes
 * that a player can craft.
 * @author Logun DeLeon
 *
 */
public class Recipe {
	/**
	 * The recipe that must be matched to craft the
	 * item.
	 */
	private Inventory recipe;
	
	/**
	 * The resulting item from using the recipe.
	 */
	private Item result;
	
	/**
	 * A constructor that assigns the recipe and result.
	 * @param i The recipe.
	 * @param result The result of the recipe.
	 */
	Recipe(final Inventory i, final Item result) {
		recipe = i;
		this.result = result;
	}
	
	/**
	 * If an inventory matches the recipe.
	 * @param i the inventory checked.
	 * @return if an inventory matches the recipe.
	 */
	public boolean isEqual(final Inventory i) {
		boolean rtn = true;
		if (i.getMax() == recipe.getMax()) {
			for (int j = 0; j < i.getMax(); j++) {
				if ((i.get(j) != null || recipe.get(j) != null)
						&& (i.get(j) == null || !i.get(j).equals(recipe.get(j)))) {
					rtn = false;
					return rtn;
				}
			}
		} else {
			rtn = false;
		}
		return rtn;
	}
	
	/**
	 * Gets the result of crafting.
	 * @return the result.
	 */
	public Item getResult() {
		return result;
	}
	
	/**
	 * Creates a recipe from an array of items given the result.
	 * @param itemArr an array of items to create a recipe.
	 * @param result the result fort the new recipe.
	 * @return A new Recipe with the recipe specified
	 * by the array and result.
	 */
	public static Recipe createRecipe(final Item[] itemArr, final Item result) {
		Inventory inv = new Inventory(9);
		for (int k = 0; k < 9; k++) {
			inv.add(itemArr[k], k);
		}
		
		return new Recipe(inv, result);
	}
}
