package main;

public class Recipe {
	private Inventory recipe;
	private Item result;
	
	Recipe(Inventory i, Item result) {
		recipe = i;
		this.result = result;
	}
	
	public boolean isEqual(Inventory i) {
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
	
	public Item getResult() {
		return result;
	}
	
	public static Recipe createRecipe(Item[] itemArr, Item result) {
		Inventory inv = new Inventory(9);
		for (int k = 0; k < 9; k++) {
			inv.add(itemArr[k], k);
		}
		
		return new Recipe(inv, result);
	}
}
