package main;

public class Player extends Entity{
	private boolean grounded;
	private float xsp_max;
	private double jumpSequence;
	private double jumpHeight;
	private double jumpSequenceCooldown;
	private double hunger;
	private double hunger_max;
	public Player(double xx, double yy){
		super(xx,yy,32,32);
		xsp = 0;
		ysp = 0;
		grav = 0.5;
		solid = false;
		hp_max = 3;
		hp = hp_max;
		hunger_max = 100;
		hunger = hunger_max;
		grounded = false;
		xsp_max = 8;
		jumpSequence = 0;
		jumpHeight = 10;
		jumpSequenceCooldown = 0;
	}
	
	public double getJumpSequence(){
		return jumpSequence;
	}
	
	public void jump(){
		jumpSequence++;
		if(jumpSequence>3)jumpSequence = 3;
		ysp = -(jumpHeight + (jumpHeight/2) * (jumpSequence/3));
		
	}
	
	public boolean isGrounded(){
		return grounded;
	}
	
	public void setGrounded(boolean g){
		grounded = g;
	}
	
	public double getXsp_max(){
		return xsp_max;
	}
	
	public void step(){
		if(grounded){
			jumpSequenceCooldown++;
			if(jumpSequenceCooldown>15){
				jumpSequence = 0;
			}
		}
		else{
			jumpSequenceCooldown = 0;
		}
		hunger-=0.01;
		if(hunger<0){
			kill();
		}
	super.step();
	}
	
	public void destroy(){
		super.destroy();
		
	}
	
	public double getHunger(){ return hunger;}
	public void setHunger(double h){ hunger = h;}
	public double getHungerMax(){ return hunger_max;}
}
