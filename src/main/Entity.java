package main;

public class Entity extends WorldObject{
	protected int hp_max;
	protected int hp;
	protected boolean alive;
	
	public Entity(double xx, double yy){
		super(xx,yy);
		width = 32;
		height = 32;
		xsp = 0;
		ysp = 0;
		grav = 1;
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
	
	public void step(Game g){
		if(alive){
			super.step(g);
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
