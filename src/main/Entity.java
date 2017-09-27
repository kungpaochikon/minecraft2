package main;

public class Entity extends WorldObject{
	protected int hp_max;
	protected int hp;
	protected boolean alive;
	
	public Entity(double xx, double yy, double ww, double hh){
		super(xx,yy,ww,hh);
		xsp = 0;
		ysp = 0;
		grav = 0;
		solid = false;
		hp_max = 3;
		hp = hp_max;
		alive = true;
	}
	
	public void kill(){
		hp = 0;
		alive = false;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void step(){
		if(alive){
			super.step();
		}
		if(hp<1){
			alive = false;
		}
	}
	
	public int getHP(){
		return hp;
	}
	
	public void setHP(int h){
		hp = h;
	}
}
