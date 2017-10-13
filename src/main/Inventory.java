package main;

import java.util.ArrayList;

public class Inventory {
	   public ArrayList<Item> inventory;
	   public int inventoryMax;
	   public int hotbarMax;
	   public int hotbarFocus;
	   
	   public Inventory(){
		   inventory = new ArrayList<Item>();
		   inventoryMax = 20;
		   hotbarMax = 10;
		   hotbarFocus = 0;
	   }
	   
	   public void remove(int i){
		   inventory.remove(i);
	   }
	   
	   public Item get(int i){
		   return inventory.get(i);
	   }
	   
	   public Item getFocused(){
		   return inventory.get(hotbarFocus);
	   }
	   
	   public void add(Item i){
		   inventory.add(i);
	   }
	   
	   public boolean has(Item item){
		   for(int i = 0;i<inventory.size();i++){
			   if(inventory.get(i).getType()==item.getType() && inventory.get(i).getId()==item.getId()){
				   return true;
			   }
		   }
		   return false;
	   }
	   
	   public void setMax(int i){
		   inventoryMax = i;
	   }
	   
	   public int getMax(){
		   return inventoryMax;
	   }
	   
	   public void setHotbarMax(int i){
		   hotbarMax = i;
	   }
	   
	   public int getHotbarMax(){
		   return hotbarMax;
	   }
	   
	   public void setFocus(int i){
		   hotbarFocus = i;
	   }
	   
	   public int getFocus(){
		   return hotbarFocus;
	   }
	   
	   public int size(){
		   return inventory.size();
	   }
}
