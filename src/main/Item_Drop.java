package main;

public class Item_Drop extends WorldObject{
	int type;
	int id;
	int count;
	public Item_Drop(double xx, double yy, int t, int i, int c) {
		super(xx, yy, 32, 32);
		type = t;
		id = i;
		count = c;
	}
	
	public int getType(){return type;}
	
	public int getId(){return id;}
	
	public int getCount(){return count;}
	
	public void setType(int t){type = t;}
	
	public void setId(int i){id = i;}
	
	public void setCount(int c){count = c;}
	
	public void changeCount(int c){count+=c;}
	
	public void step(){
		super.step();
		if(xsp<0.1) xsp=0;
		if(ysp<0.1) ysp=0;
	}

}
