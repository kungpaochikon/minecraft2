package main;

import java.util.Random;

public class Chicken extends Animal{
	int state;
	Random random;
	public Chicken(double xx, double yy) {
		super(xx, yy);
		width = 32;
		height = 32;
		state = 0;
		random = new Random();
	}
	
	public void step(final Game g){
		super.step(g);
	}

}
