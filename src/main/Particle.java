package main;

public class Particle extends WorldObject{
	private int life;
	public Particle(double xx, double yy, double ww, double hh, double xxx, double yyy, int l){
		super(xx, yy, ww, hh);
		xsp = xxx;
		ysp = yyy;
		life = l;
	}
	
	public void step(){
		super.step();
		if(life<1 || width<=0){
			destroy();
		}
		life--;
		width-=0.5;
		height-=0.5;
		
	}

}
