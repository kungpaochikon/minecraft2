package main;

public class Player extends Entity{
	/**
	 * If the player is on the ground.
	 */
	private boolean grounded;
	
	/**
	 * 
	 */
	private float xsp_max;
	private double jumpSequence;
	private double jumpHeight;
	private double jumpSequenceCooldown;
	private double hunger;
	private double hunger_max;
	private static boolean swinging;
	private long lastSwing;
	
	public Player(double xx, double yy) {
		super(xx, yy);
		width = 32;
		height = 32;
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
		swinging = true;
		lastSwing = 0;
	}
	
	public double getJumpSequence(){
		return jumpSequence;
	}
	
	public void jump(){
		jumpSequence++;
		if (jumpSequence > 3) {
			jumpSequence = 3;
		}
		ysp = -(jumpHeight + (jumpHeight / 2) * (jumpSequence / 3));
		
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
	
	public void step(Game g) {
		WorldGrid world = g.getWorld();
		int wBlockSize = g.getwBlockSize();
		if (grounded) {
			jumpSequenceCooldown++;
			if (jumpSequenceCooldown > 15) {
				jumpSequence = 0;
			}
		} else {
			jumpSequenceCooldown = 0;
		}
		hunger -= 0.01;
		if (hunger < 0) {
			kill();
		}
		Player obj = this;
	   double ax1 = obj.getX();
	   double ax2 = obj.getX() + obj.getWidth();
	   double ay1 = obj.getY();
	   double ay2 = obj.getY() + obj.getHeight();
	   double axsp = obj.getXsp();
	   double aysp = obj.getYsp();
	   double aw = obj.getWidth();
	   double ah =  obj.getHeight();
	   boolean xCol = false;
	   boolean yCol = false;
	   
	   //Grid Collision
	   int xx;
	   //X Collision
	   int yLow = Math.floorDiv((int) ay1, wBlockSize);
	   int yHi = Math.floorDiv((int) ay2, wBlockSize);
	   xx = Math.floorDiv((int) (ax1 + aw / 2 + aw * Math.signum(axsp) / 2 + axsp), wBlockSize);
	   for(int yy = yLow; yy <= yHi; yy++) {
		   if (g.wGridBounds(xx, yy) && world.getWID(xx, yy) != 0 && world.getWID(xx, yy) != 4) {
			   int xx2 = Math.floorDiv((int) (ax1 + aw / 2 + aw * Math.signum(axsp) / 2 + Math.signum(axsp)), wBlockSize);
			   while (g.wGridBounds(xx, yy) && (world.getWID(xx2, yy) == 0 || world.getWID(xx2, yy) == 4)) {
				   ax1 += Math.signum(axsp);
				   xx2 = Math.floorDiv((int) (ax1 + aw / 2 + aw * Math.signum(axsp) / 2 + Math.signum(axsp)), wBlockSize);
			   }
			   obj.setX((ax1));
			   obj.setXsp(0);
			   xCol = true;
		   }
	   }
	   
	   
	   //Grid Collision
	   //Y Collision
	   int xLow = Math.floorDiv((int) ax1, wBlockSize);
	   int xHi = Math.floorDiv((int) ax2, wBlockSize);
	   int yy = Math.floorDiv((int) (ay1 + ah / 2 + ah
			   * Math.signum(aysp) / 2 + aysp), wBlockSize);
	   for (xx = xLow; xx <= xHi; xx++) {
		   if (g.wGridBounds(xx, yy) && world.getWID(xx, yy)
				   != 0 && world.getWID(xx, yy) != 4) {
			   int yy2 = Math.floorDiv((int) (ay1 + ah / 2 + ah
					   * Math.signum(aysp) / 2
					   + Math.signum(aysp)), wBlockSize);
			   while (g.wGridBounds(xx, yy) && (world.getWID(
					   xx, yy2) == 0 || world.getWID(
					   xx, yy2) == 4)) {
				   ay1 += Math.signum(aysp);
				   yy2 = Math.floorDiv((int) (ay1 + ah / 2 + ah
						   * Math.signum(aysp) / 2
						   + Math.signum(aysp)), wBlockSize);
			   }
			   //Damage
			   if (getYsp() > 28) {
				   setHP(getHP() - 1);
				   //viewShake(7, 20);
			   }
			   obj.setY(ay1);
			   obj.setYsp(0);
			   yCol = true;
		   }
	   }
	   
	   //Corner Collision
	   if (!xCol && !yCol) {
		   yLow = Math.floorDiv((int) ((int) ay1 + aysp), wBlockSize);
		   yHi = Math.floorDiv((int) ((int) ay2 + aysp), wBlockSize);
		   xLow = Math.floorDiv((int) ((int) ax1 + axsp), wBlockSize);
		   xHi = Math.floorDiv((int) ((int) ax2 + axsp), wBlockSize);
		   //yy = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+aysp), wBlockSize);
		   for (xx = xLow; xx <= xHi; xx++) {
			   for (yy = yLow; yy <= yHi; yy++) {
				   if (g.wGridBounds(xx, yy) && world.getWID(
						   xx, yy) != 0 && world.getWID(
						   xx, yy) != 4) {
					   obj.setXsp(0);
				   }
			   }
		   }
	   }
	   
	   
	   ax1 = obj.getX();
	   ax2 = obj.getX() + obj.getWidth();
	   ay1 = obj.getY();
	   ay2 = obj.getY() + obj.getHeight();
	   axsp = obj.getXsp();
	   aysp = obj.getYsp();
	   aw = obj.getWidth();
	   ah =  obj.getHeight();
	   //xCol = false;
	   //yCol = false;
	   //Solid Object Collision
	   
	   for (int o = 0; o < g.getObjList().size(); o++) {
		   WorldObject objCol = g.getObjList().get(o);
		   if (objCol instanceof Item_Drop) {
			   double bx1 = objCol.getX();
			   double bx2 = objCol.getX() + objCol.getWidth();
			   double by1 = objCol.getY();
			   double by2 = objCol.getY() + objCol.getHeight();
			   Item_Drop drop = (Item_Drop) objCol;
			   //General Collision
			   if (ax1 + axsp < bx2 && ax2 + axsp > bx1
					   && ay1 + aysp < by2 && ay2 + aysp
					   > by1 && isAlive()) {
				   if (g.inventoryCheck(drop.getType(),
						   drop.getId())
						   || g.getInventory().size()
						   < g.getInventory().getMax()) {
					   g.inventoryAdd((drop).getType(), (drop).getId());
					   g.removeWorldObject(drop);
					   g.playSound(g.getSndBop());
				   }
			   }
		   }
		   if (objCol.isSolid()) {
			   double bx1 = objCol.getX();
			   double bx2 = objCol.getX() + objCol.getWidth();
			   double by1 = objCol.getY();
			   double by2 = objCol.getY() + objCol.getHeight();
			   //double bxsp = objCol.getXsp();
			   //double bysp = objCol.getYsp();
			   //X collision
			   if (ax1 + axsp < bx2 && ax2 + axsp > bx1
					   && ay1 < by2 && ay2 > by1) {
				   while (!(ax1 + 1 * Math.signum(axsp) < bx2
						   && ax2 + 1 * Math.signum(axsp)
						   > bx1 && ay1 < by2 && ay2 > by1)) {
					   ax1 += Math.signum(axsp);
					   ax2 += Math.signum(axsp);
				   }
				   obj.setX(ax1);
				   obj.setXsp(0);
				   xCol = true;
			   }
			   //Y collision
			   if (ax1 < bx2 && ax2 > bx1
					   && ay1 + aysp < by2
					   && ay2 + aysp > by1) {
				   while (!(ax1 < bx2 && ax2 > bx1
						   && ay1 + 1 * Math.signum(
						   aysp) < by2 && ay2 + 1
						   * Math.signum(aysp) > by1)) {
					   ay1 += Math.signum(aysp);
					   ay2 += Math.signum(aysp);
				   }
				   obj.setY(ay1);
				   obj.setYsp(0);
				   yCol = true;
			   }
		   }
	   }
	   if (yCol) {
		   ((Player) obj).setGrounded(true);
	   } else {
		   ((Player) obj).setGrounded(false);
	   }
	super.step(g);
	}
	
	public void destroy() {
		super.destroy();
		
	}
	
	public double getHunger() { 
		return hunger;
	}
	public void setHunger(double h) {
		hunger = h;
	}
	public double getHungerMax() {
		return hunger_max;
	}
	
	static public boolean isSwinging() {
		return swinging;
	}
	
	static public void setSwinging(boolean s) {
		swinging = s;
	}
	
	public void setLastSwing(long ls) {
		lastSwing = ls;
	}
	
	public long getLastSwing() {
		return lastSwing;
	}
}
