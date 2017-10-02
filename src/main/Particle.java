package main;

public class Particle extends WorldObject{
	private int life;
	public Particle(double xx, double yy, double xxx, double yyy, int l){
		super(xx, yy);
		width = 16;
		height = 16;
		xsp = xxx;
		ysp = yyy;
		life = l;
	}
	
	public void step(Game g){
		if(life<1 || width<=0){
			destroy();
		}
		life--;
		width-=0.5;
		height-=0.5;
		super.step(g);
	}

}
