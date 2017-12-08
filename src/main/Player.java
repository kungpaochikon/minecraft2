package main;

/**
 * This class is for the player character.
 * It holds data for the player and handles
 * collision detection.
 * @author Gary Fleming
 *
 */
public class Player extends Entity {
	/**
	 * If the player is on the ground.
	 */
	private boolean grounded;
	
	/**
	 * The maximum change in x.
	 */
	private float xspMax;
	
	/**
	 * How far along the player is in a jump.
	 */
	private double jumpSequence;
	
	/**
	 * The height of a jump.
	 */
	private double jumpHeight;
	
	/**
	 * The number of iterations the jump has
	 * been cooling down for. This number must
	 * be big enough for the player to jump again.
	 */
	private double jumpSequenceCooldown;
	
	/**
	 * The current level of the player's
	 * hunger bar.
	 */
	private double hunger;
	
	/**
	 * The max level of the player's hunger bar.
	 */
	private double hungerMax;
	
	/**
	 * If the player is currently swinging.
	 */
	private static boolean swinging = false;
	
	/**
	 * The time of the last swing of the player.
	 */
	private long lastSwing;
	
	/**
	 * The time of the last swing of the player.
	 */
	private boolean isHurt;
	
	/**
	 * The time of the last swing of the player.
	 */
	private int hurtCD = 0;
	
	/**
	 * The constructor for Player that sets the x and y location
	 * of the player. Sets all instance variables to their starting
	 * values.
	 * @param xx the x location of the player.
	 * @param yy the y location of the player.
	 */
	public Player(final double xx, final double yy) {
		super(xx, yy);
		width = 32;
		height = 32;
		xsp = 0;
		ysp = 0;
		grav = 0.5;
		solid = false;
		hpMax = 3;
		hp = hpMax;
		hungerMax = 100;
		hunger = hungerMax;
		grounded = false;
		xspMax = 8;
		jumpSequence = 0;
		jumpHeight = 10;
		jumpSequenceCooldown = 0;
		lastSwing = 0;
	}
	
	/**
	 * Gets the jump sequence.
	 * @return the value of the jump sequence.
	 */
	public double getJumpSequence() {
		return jumpSequence;
	}
	
	/**
	 * Makes the player jump.
	 */
	public void jump() {
		jumpSequence++;
		if (jumpSequence > 3) {
			jumpSequence = 3;
		}
		ysp = -(jumpHeight + (jumpHeight / 2) * (jumpSequence / 3));
		
	}
	
	/**
	 * Returns  if the player is currently on the ground.
	 * @return if the player is on the ground.
	 */
	public boolean isGrounded() {
		return grounded;
	}
	
	/**
	 * Sets the value of grounded.
	 * @param g the value to set grounded to.
	 */
	public void setGrounded(final boolean g) {
		grounded = g;
	}
	
	/**
	 * Gets the value of xspMax.
	 * @return the maximum change in x.
	 */
	public double getXspMax() {
		return xspMax;
	}
	
	/**
	 * The actions taken by the player on each iteration
	 * of the game loop. It decreases hunger, checks the
	 * jump cool down, and performs collision detection.
	 * @param g the game the Player is stepping through.
	 */
	@Override
	public void step(final Game g) {
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
	   for (int yy = yLow; yy <= yHi; yy++) {
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
	   //xCol = false;
	   //yCol = false;
	   //Solid Object Collision
	   
	   for (int o = 0; o < g.getObjList().size(); o++) {
		   WorldObject objCol = g.getObjList().get(o);
		   //Item Collision
		   if (objCol instanceof Item_Drop) {
			   double bx1 = objCol.getX();
			   double bx2 = objCol.getX() + objCol.getWidth();
			   double by1 = objCol.getY();
			   double by2 = objCol.getY() + objCol.getHeight();
			   Item_Drop drop = (Item_Drop) objCol;
			   //General Collision
			   //Pickin up and Item
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
		   //Enemy Collision
		   if (objCol instanceof Enemy) {
			   double bx1 = objCol.getX();
			   double bx2 = objCol.getX() + objCol.getWidth();
			   double by1 = objCol.getY();
			   double by2 = objCol.getY() + objCol.getHeight();
			   Enemy drop = (Enemy) objCol;
			   //General Collision
			   //Pickin up and Item
			   if (ax1 + axsp < bx2 && ax2 + axsp > bx1
					   && ay1 + aysp < by2 && ay2 + aysp
					   > by1 && isAlive()) {
				   if (!isHurt) {
					   isHurt = true;
					   hurtCD = 60;
					   hp--;
					   g.getView().viewShake(10, 20);
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
	   
	   //ISHURT
	   if (isHurt) {
		   hurtCD--;
		   if (hurtCD < 0) {
			   isHurt = false;
		   }
	   }
	super.step(g);
	}
	
	/**
	 * Destroys this entity.
	 */
	public void destroy() {
		super.destroy();
		
	}
	
	/**
	 * Gets the value of hunger.
	 * @return the current level of hunger.
	 */
	public double getHunger() { 
		return hunger;
	}
	
	/**
	 * Sets the value of hunger.
	 * @param h the value to set hunger to.
	 */
	public void setHunger(final double h) {
		hunger = h;
	}
	
	/**
	 * Gets the maximum value of hunger.
	 * @return the maximum value of hunger.
	 */
	public double getHungerMax() {
		return hungerMax;
	}
	
	/**
	 * Returns whether the player is swinging.
	 * @return if the player is swinging or not.
	 */
	public static boolean isSwinging() {
		return swinging;
	}
	
	/**
	 * Sets the value of swinging.
	 * @param s the value to set swinging to.
	 */
	public static void setSwinging(final boolean s) {
		swinging = s;
	}
	
	/**
	 * Sets the last swing time.
	 * @param ls the value to set last swing to.
	 */
	public void setLastSwing(final long ls) {
		lastSwing = ls;
	}
	
	/**
	 * Returns whether or not the player should be opaque
	 * (relating to hurtCD).
	 * @return whether or not the player should be opaque.
	 */
	public boolean showSprite() {
		return !isHurt | hurtCD % 2 == 0; 
	}
	
	/**
	 * Gets the last time the player
	 * swinged.
	 * @return the last time the player swinged.
	 */
	public long getLastSwing() {
		return lastSwing;
	}
}
