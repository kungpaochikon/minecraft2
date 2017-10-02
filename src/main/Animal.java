package main;

import java.util.Random;

public class Animal extends Entity{
	int state;
	Random random;
	public Animal(double xx, double yy) {
		super(xx, yy);
		width = 32;
		height = 32;
		state = 0;
		random = new Random();
	}
	
	public void step(Game g){
		
		if(random.nextInt(80)==1){
			state = random.nextInt(3);
		}
		switch(state){
		case(0):
			xsp = 0;
			break;
		case(1):
			xsp = -1;
			break;
		case(2):
			xsp = 1;
			break;
		}
		Animal obj = this;
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
		   int yLow = Math.floorDiv((int) ay1, g.wBlockSize);
		   int yHi = Math.floorDiv((int) ay2, g.wBlockSize);
		   xx = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+axsp), g.wBlockSize);
		   for(int yy = yLow;yy<=yHi;yy++){
			   if(g.wGridBounds(xx,yy) && g.world.getWID(xx,yy)!=0 && g.world.getWID(xx,yy)!=4){
				   int xx2 = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+Math.signum(axsp)), g.wBlockSize);
				   while(g.wGridBounds(xx,yy) && (g.world.getWID(xx2,yy)==0 || g.world.getWID(xx2,yy)==4)){
					   ax1+=Math.signum(axsp);
					   xx2 = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+Math.signum(axsp)), g.wBlockSize);
				   }
				   obj.setX((ax1));
				   obj.setXsp(0);
				   xCol = true;
			   }
		   }
		   
		   
		   //Grid Collision
		   //Y Collision
		   int xLow = Math.floorDiv((int) ax1, g.wBlockSize);
		   int xHi = Math.floorDiv((int) ax2, g.wBlockSize);
		   int yy = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+aysp), g.wBlockSize);
		   for(xx = xLow;xx<=xHi;xx++){
			   if(g.wGridBounds(xx,yy) && g.world.getWID(xx,yy)!=0 && g.world.getWID(xx,yy)!=4){
				   int yy2 = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+Math.signum(aysp)), g.wBlockSize);
				   while(g.wGridBounds(xx,yy) && (g.world.getWID(xx,yy2)==0 || g.world.getWID(xx,yy2)==4)){
					   ay1+=Math.signum(aysp);
					   yy2 = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+Math.signum(aysp)), g.wBlockSize);
				   }
				   //Damage
				   if(getYsp()>28){
					   setHP(getHP()-1);
					   //viewShake(7, 20);
				   }
				   obj.setY(ay1);
				   obj.setYsp(0);
				   yCol = true;
			   }
		   }
		   
		   //Corner Collision
		   if(!xCol && !yCol){
			   yLow = Math.floorDiv((int) ((int) ay1 + aysp), g.wBlockSize);
			   yHi = Math.floorDiv((int) ((int) ay2 + aysp), g.wBlockSize);
			   xLow = Math.floorDiv((int) ((int) ax1 + axsp), g.wBlockSize);
			   xHi = Math.floorDiv((int) ((int) ax2 + axsp), g.wBlockSize);
			   //yy = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+aysp), wBlockSize);
			   for(xx = xLow;xx<=xHi;xx++){
				   for(yy = yLow;yy<=yHi;yy++){
					   if(g.wGridBounds(xx,yy) && g.world.getWID(xx,yy)!=0 && g.world.getWID(xx,yy)!=4){
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
		   
		   for(int o = 0;o<g.objList.size();o++){
			   WorldObject objCol = g.objList.get(o);
			   if(objCol instanceof Item_Drop){
				   double bx1 = objCol.getX();
				   double bx2 = objCol.getX() + objCol.getWidth();
				   double by1 = objCol.getY();
				   double by2 = objCol.getY() + objCol.getHeight();
				   
				   //General Collision
				   if (ax1+axsp < bx2 && ax2+axsp > bx1 &&
					ay1+aysp < by2 && ay2+aysp > by1 && isAlive()){
					   g.inventoryAdd(((Item_Drop) objCol).getType(), ((Item_Drop) objCol).getId());
					   g.removeWorldObject(objCol);
					   g.playSound(g.snd_bop);
				   }
			   }
			   if(objCol.isSolid()){
				   double bx1 = objCol.getX();
				   double bx2 = objCol.getX() + objCol.getWidth();
				   double by1 = objCol.getY();
				   double by2 = objCol.getY() + objCol.getHeight();
				   //double bxsp = objCol.getXsp();
				   //double bysp = objCol.getYsp();
				   //X collision
				   if (ax1+axsp < bx2 && ax2+axsp > bx1 &&
					ay1 < by2 && ay2 > by1){
					   while(!(ax1+1*Math.signum(axsp) < bx2 && ax2+1*Math.signum(axsp) > bx1 &&
								ay1 < by2 && ay2 > by1)){
						   ax1+=Math.signum(axsp);
						   ax2+=Math.signum(axsp);
					   }
					   obj.setX(ax1);
					   obj.setXsp(0);
					   xCol = true;
				   }
				   //Y collision
				   if (ax1 < bx2 && ax2 > bx1 &&
					ay1+aysp < by2 && ay2+aysp > by1){
					   while(!(ax1 < bx2 && ax2 > bx1 &&
							ay1+1*Math.signum(aysp) < by2 && ay2+1*Math.signum(aysp) > by1)){
						   ay1+=Math.signum(aysp);
						   ay2+=Math.signum(aysp);
					   }
					   obj.setY(ay1);
					   obj.setYsp(0);
					   yCol = true;
				   }
			   }
		   }
		super.step(g);
	}

}
