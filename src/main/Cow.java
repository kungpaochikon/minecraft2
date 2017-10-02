package main;

import java.util.Random;

public class Cow extends Animal{
	int state;
	Random random;
	public Cow(double xx, double yy) {
		super(xx, yy);
		width = 64;
		height = 64;
		state = 0;
		random = new Random();
	}
	
	public void step(Game g){
		super.step(g);
	}

}
