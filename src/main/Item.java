package main;

public class Item {
	private int type;
	private int id;
	private int count;
	
	public Item(int t, int i, int c){
		type = t;
		id = i;
		count = c;
		//type = ItemType.BLOCK;
	}
	
	public int getType(){return type;}
	
	public int getId(){return id;}
	
	public int getCount(){return count;}
	
	public void setType(int t){type = t;}
	
	public void setId(int i){id = i;}
	
	public void setCount(int c){count = c;}
	
	public void changeCount(int c){count+=c;}
	
}
