package main;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game extends JFrame implements ActionListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
   private GamePanel gamePanel = new GamePanel();
   private boolean running = false;
   private boolean paused = false;
   private int fps = 60;
   private int frameCount = 0;
   
   //Player Instance
   private Player player;
   
   //instance list for game
   private ArrayList<WorldObject> objList;
   
   //Inventory
   private ArrayList<Item> inventory;
   private int inventoryFocus;
   
   //View Handling
   private double viewX;
   private double viewY;
   private double viewXFinal;
   private double viewYFinal;
   private int viewW;
   private int viewH;
   private int viewShakeX;
   private int viewShakeY;
   private int viewShakeTime;
   private int viewShakeMag;
   private boolean viewShaking;
   
   //Control booleans
   private boolean moveL;
   private boolean moveR;
   private boolean mousePressed;
   private double mouseX;
   private double mouseY;
   
   //random
   private Random random;
   
   //Constants
   private int TYPE_BLOCK = 0;
   private int TYPE_BACK = 1;
   private int TYPE_TOOL = 2;
   private int TYPE_ITEM = 3;
   private int TYPE_FOOD = 4;
   
   
   //Sprites
   private BufferedImage spr_player;
   private BufferedImage spr_spike;
   private BufferedImage spr_wall;
   private BufferedImage bg_sky;
   private BufferedImage spr_black;
   private BufferedImage spr_diamond;
   private BufferedImage spr_heart;
   private BufferedImage spr_chicken;
   //private BufferedImage spr_block;
   private BufferedImage[][] sprites;
   //Sounds
   private File snd_jump;
   private File snd_death;
   private File snd_explosion;
   private File snd_bop;
   
   //World Grid
   private WorldGrid world;
   private int wGridSizeX = 256;
   private int wGridSizeY = 256;
   private int wBlockSize = 48;
   private int wBlockLen = 256;
   private int bBlockLen = 256;
   private int lBlockLen = 5;
   private int updateInterval = 1;
   private int updateIntervalCount = 0;
   
   /*******************************************************************
    * 
    * Game
    * ----
    * Set the panel and all other meta things
    * 
    *******************************************************************/
   public Game()
   {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());
      JPanel p = new JPanel();
      p.setLayout(new GridLayout(1,2));
      cp.add(gamePanel, BorderLayout.CENTER);
      cp.add(p, BorderLayout.SOUTH);
      setSize(1280, 720);
      setLocationRelativeTo(null);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      objList = new ArrayList<WorldObject>();
      addKeyListener(this);
      addMouseListener(this);
      addMouseMotionListener(this);
      addMouseWheelListener(this);
      random = new Random();
      
      
      //Load Sprites/sounds
      try {
    	  sprites = new BufferedImage[10][256];
		spr_player = ImageIO.read(new File("images\\spr_player.png"));
		spr_spike = ImageIO.read(new File("images\\spr_spike.png"));
		spr_wall = ImageIO.read(new File("images\\spr_wall.png"));
		bg_sky = ImageIO.read(new File("images\\bg_clouds.png"));
		spr_black = ImageIO.read(new File("images\\spr_black.png"));
		spr_heart = ImageIO.read(new File("images\\spr_heart.png"));
		spr_chicken = ImageIO.read(new File("images\\spr_chicken.png"));
		//spr_diamond = ImageIO.read(new File("images\\spr_diamond.png"));
		//Terrain
		sprites[TYPE_BLOCK][1] = ImageIO.read(new File("images\\spr_dirt.png"));
		sprites[TYPE_BLOCK][2] = ImageIO.read(new File("images\\spr_dirt_grass.png"));
		sprites[TYPE_BLOCK][3] = ImageIO.read(new File("images\\spr_leaves.png"));
		sprites[TYPE_BLOCK][4] = ImageIO.read(new File("images\\spr_water.png"));
		sprites[TYPE_BLOCK][5] = ImageIO.read(new File("images\\spr_diamond_ore.png"));
		sprites[TYPE_BLOCK][6] = ImageIO.read(new File("images\\spr_stone.png"));
		sprites[TYPE_BLOCK][7] = ImageIO.read(new File("images\\spr_cobblestone.png"));
		//Backs
		sprites[TYPE_BACK][1] = ImageIO.read(new File("images\\spr_wood.png"));
		sprites[TYPE_BACK][2] = ImageIO.read(new File("images\\spr_back_cave.png"));
		sprites[TYPE_BACK][3] = ImageIO.read(new File("images\\spr_back_cave_stone.png"));
		//Tools
		sprites[TYPE_TOOL][0] = ImageIO.read(new File("images\\spr_pickaxe.png"));
		sprites[TYPE_TOOL][1] = ImageIO.read(new File("images\\spr_axe.png"));
		sprites[TYPE_TOOL][2] = ImageIO.read(new File("images\\spr_sword.png"));
		//Items
		sprites[TYPE_ITEM][0] = ImageIO.read(new File("images\\spr_diamond.png"));
		//Food
		sprites[TYPE_FOOD][0] = ImageIO.read(new File("images\\spr_apple.png"));
		//Sounds
		snd_jump = new File("sounds\\jump.wav").getAbsoluteFile();
		snd_death = new File("sounds\\death.wav").getAbsoluteFile();
		snd_explosion = new File("sounds\\explosion.wav").getAbsoluteFile();
		snd_bop = new File("sounds\\bop.wav").getAbsoluteFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      setGame();
      runGameLoop();
   }
   
   /*******************************************************************
    * 
    * Main
    * ----
    * Main Method, basically just run the game.
    * 
    *******************************************************************/
   public static void main(String[] args)
   {
      Game game = new Game();
      game.setVisible(true);
   }
   
   /*******************************************************************
    * 
    * Add World Object
    * ----------------
    * Add instance to the world objects list.
    * There is probably a better way to do this.
    * 
    *******************************************************************/
   private WorldObject addWorldObject(WorldObject obj){
	   objList.add(obj);
	   sortWorldObjectDepth();
	   return obj;
   }
   
   /*******************************************************************
    * 
    * Sort World Object Depth
    * -----------------------
    * Sort the object list by depth.
    * 
    *******************************************************************/
   private void sortWorldObjectDepth(){
	   Collections.sort(objList);
   }
   
   /*******************************************************************
    * 
    * Remove World Object
    * ----------------
    * Remove instance from the world objects list.
    * There is probably a better way to do this.
    * 
    *******************************************************************/
   private void removeWorldObject(WorldObject obj){
	   objList.remove(obj);
   }
   
   /*******************************************************************
    * 
    * Remove World Object index
    * ----------------
    * Remove instance from the world objects list at index.
    * There is probably a better way to do this.
    * 
    *******************************************************************/
   private void removeWorldObject(int i){
	   objList.remove(i);
   }
   
   /*******************************************************************
    * 
    * Game Set
    * --------
    * Set the game with standard values
    * 
    *******************************************************************/
   private void setGame(){
		  objList = new ArrayList<WorldObject>();
		  inventory = new ArrayList<Item>();
		  inventory.add(new Item(TYPE_TOOL,0,1));
		  inventory.add(new Item(TYPE_TOOL,1,1));
		  inventory.add(new Item(TYPE_TOOL,2,1));
		  inventoryFocus = 0;
	      running = true;
	      viewX = 0;
	      viewY = 0;
	      viewW = 1280;
	      viewH = 720;
	      viewXFinal = 0;
	      viewYFinal = 0;
	      viewShakeX = 0;
	      viewShakeY = 0;
	      viewShakeTime = 0;
	      viewShakeMag = 0;
	      viewShaking = false;
	      moveL = false;
	      moveR = false;
	      //Player
	      player = new Player(100,100);
	      player.setDepth(2);
	      addWorldObject(player);
	      //addWorldObject(new Chicken(xx,yy,ww,hh));
	      world = new WorldGrid(wGridSizeX,wGridSizeY,wBlockSize);
	      world.generate();
	      boolean go = true;
	      for(int j = 0;j<wGridSizeY;j++){
	    	  if(world.getWID(wGridSizeX/2, j)!=0 && go){
	    		  player.setX(wGridSizeX*wBlockSize/2);
	    		  player.setY((j-1)*wBlockSize);
	    		  go = false;
	    	  }
	      }
	      viewX = player.getX()-viewW/2;
	      viewY = player.getY()-viewH/2;
	      
	      //World Grid
	      
	      
	      //for(int i = 0;i<wGridSizeX;i++){
	    	//	  wGrid[i][11] = 2;
	      //}
	      //wGenTree(10,10,2,5);
   }

   /*******************************************************************
    * 
    * Game Reset
    * ----------
    * Reset the game
    * 
    *******************************************************************/
   private void reset(){
	   setGame();
   }
   
   public void actionPerformed(ActionEvent e)
   {
	   
   }
   
   /*******************************************************************
    * 
    * Game Input
    * ----------
    * Get the keyboard input and respond
    * 
    *******************************************************************/
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_SPACE){
			if(player.isGrounded()){
				player.jump();
				playSound(snd_jump);
			}
		}
		if(arg0.getKeyCode() == KeyEvent.VK_R){
			reset();
		}
		if(arg0.getKeyCode() == KeyEvent.VK_E){
			inventoryFocus++;
			if(inventoryFocus>=inventory.size()) inventoryFocus = 0;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_Q){
			inventoryFocus--;
			if(inventoryFocus<0) inventoryFocus = inventory.size()-1;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_A){
			moveL = true;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_D){
			moveR = true;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_F){
			player.destroy();
			viewShake(20, 50);
		}
		//Select Inventory with Numbs
		if(arg0.getKeyCode() == KeyEvent.VK_1){
			int num = 0;
			if(inventory.size()>num) inventoryFocus = num;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_2){
			int num = 1;
			if(inventory.size()>num) inventoryFocus = num;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_3){
			int num = 2;
			if(inventory.size()>num) inventoryFocus = num;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_4){
			int num = 3;
			if(inventory.size()>num) inventoryFocus = num;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_A){
			moveL = false;
		}
		if(arg0.getKeyCode() == KeyEvent.VK_D){
			moveR = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
   
   /*******************************************************************
    * 
    * Run Game Loop
    * -------------
    * Simple init of the game loop itself
    * 
    *******************************************************************/
   public void runGameLoop()
   {
      Thread loop = new Thread()
      {
         public void run()
         {
            gameLoop();
         }
      };
      loop.start();
   }
   
   /*******************************************************************
    * 
    * Game Loop
    * ---------
    * Better game loop, doesn't max out cpu like a plain old
    * while loop and has measures in place to smooth out the 
    * updates and renders
    * 
    *******************************************************************/
   //Only run this in another Thread!
   private void gameLoop()
   {
      //This value would probably be stored elsewhere.
      final double GAME_HERTZ = 60.0;
      //Calculate how many ns each frame should take for our target game hertz.
      final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
      //At the very most we will update the game this many times before a new render.
      //If you're worried about visual hitches more than perfect timing, set this to 1.
      final int MAX_UPDATES_BEFORE_RENDER = 100;
      //We will need the last update time.
      double lastUpdateTime = System.nanoTime();
      //Store the last time we rendered.
      double lastRenderTime = System.nanoTime();
      
      //If we are able to get as high as this FPS, don't render again.
      final double TARGET_FPS = 60;
      final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
      
      //Simple way of finding FPS.
      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
      
      while (running)
      {
         double now = System.nanoTime();
         int updateCount = 0;
         if (!paused)
         {
             //Do as many game updates as we need to, potentially playing catchup.
            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
            {
               updateGame();
               lastUpdateTime += TIME_BETWEEN_UPDATES;
               updateCount++;
            }
            
   
            //If for some reason an update takes forever, we don't want to do an insane number of catchups.
            //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
            {
               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }
         
            //Render. To do so, we need to calculate interpolation for a smooth render.
            float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES) );
            drawGame(interpolation);
            lastRenderTime = now;
         
            //Update the frames we got.
            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime)
            {
               //System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
               fps = frameCount;
               frameCount = 0;
               lastSecondTime = thisSecond;
            }
         
            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
            while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
            {
               Thread.yield();
            
               //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
               //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
               //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
               try {Thread.sleep(1);} catch(Exception e) {} 
            
               now = System.nanoTime();
            }
         }
      }
   }
   /*******************************************************************
    * 
    * Update Game
    * ---------
    * Update the game by going through everything that changes
    * per step.
    * 
    *******************************************************************/
   private void updateGame()
   {  
	   //Update inventory
	   inventoryUpdate();
	   if(mousePressed){
		   
	   }
	   
	   //Player Movement
	   if(moveL){
		   player.setXsp(player.getXsp()-1);
	   }
	   if(moveR){
		   player.setXsp(player.getXsp()+1);
	   }
	   if(Math.abs(player.getXsp())>player.getXsp_max() ){
		   player.setXsp(player.getXsp_max()*Math.signum(player.getXsp()));
	   }
	   if(!moveL && !moveR){
		   if(Math.abs(player.getXsp())>1){
			   player.setXsp(player.getXsp()-1*Math.signum(player.getXsp()));
		   }
		   else{
			   player.setXsp(0);
		   }
	   }
	   
	   
	   //Update World In View
	   if(updateIntervalCount>=updateInterval){
		   updateIntervalCount = 0;
		   world.update((int) Math.floor(viewX/wBlockSize),(int) Math.floor((viewX + viewW)/wBlockSize),(int) Math.floor(viewY/wBlockSize),(int) Math.floor((viewY + viewH)/wBlockSize));
	   }
	   updateIntervalCount++;
	   if(player.getJumpSequence()>2){
		   addWorldObject(new Particle(player.getX()+8,player.getY()+8,16,16,0,0,30));
	   }
	   //if(!player.isGrounded()) player.setAngle(player.getAngle()+player.getXsp());
	   //else player.setAngle(Math.round(player.getAngle()/90)*90);
	   for(int i = 0;i<objList.size();i++){
		   WorldObject obj = objList.get(i);
		   if(obj.destroyed()){
			   removeWorldObject(i);
			   i--;
			   continue;
		   }
		   obj.doGrav();
		   if(obj instanceof Player){
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
			   xx = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+axsp), wBlockSize);
			   for(int yy = yLow;yy<=yHi;yy++){
				   if(wGridBounds(xx,yy) && world.getWID(xx,yy)!=0 && world.getWID(xx,yy)!=4){
					   int xx2 = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+Math.signum(axsp)), wBlockSize);
					   while(wGridBounds(xx,yy) && (world.getWID(xx2,yy)==0 || world.getWID(xx2,yy)==4)){
						   ax1+=Math.signum(axsp);
						   xx2 = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+Math.signum(axsp)), wBlockSize);
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
			   int yy = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+aysp), wBlockSize);
			   for(xx = xLow;xx<=xHi;xx++){
				   if(wGridBounds(xx,yy) && world.getWID(xx,yy)!=0 && world.getWID(xx,yy)!=4){
					   int yy2 = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+Math.signum(aysp)), wBlockSize);
					   while(wGridBounds(xx,yy) && (world.getWID(xx,yy2)==0 || world.getWID(xx,yy2)==4)){
						   ay1+=Math.signum(aysp);
						   yy2 = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+Math.signum(aysp)), wBlockSize);
					   }
					   //Damage
					   if(player.getYsp()>28){
						   player.setHP(player.getHP()-1);
						   viewShake(7, 20);
					   }
					   obj.setY(ay1);
					   obj.setYsp(0);
					   yCol = true;
				   }
			   }
			   
			   //Corner Collision
			   if(!xCol && !yCol){
				   yLow = Math.floorDiv((int) ((int) ay1 + aysp), wBlockSize);
				   yHi = Math.floorDiv((int) ((int) ay2 + aysp), wBlockSize);
				   xLow = Math.floorDiv((int) ((int) ax1 + axsp), wBlockSize);
				   xHi = Math.floorDiv((int) ((int) ax2 + axsp), wBlockSize);
				   //yy = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+aysp), wBlockSize);
				   for(xx = xLow;xx<=xHi;xx++){
					   for(yy = yLow;yy<=yHi;yy++){
						   if(wGridBounds(xx,yy) && world.getWID(xx,yy)!=0 && world.getWID(xx,yy)!=4){
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
			   for(int o = 0;o<objList.size();o++){
				   WorldObject objCol = objList.get(o);
				   if(objCol instanceof Item_Drop){
					   double bx1 = objCol.getX();
					   double bx2 = objCol.getX() + objCol.getWidth();
					   double by1 = objCol.getY();
					   double by2 = objCol.getY() + objCol.getHeight();
					   //double bxsp = objCol.getXsp();
					   //double bysp = objCol.getYsp();
					   
					   //General Collision
					   if (ax1+axsp < bx2 && ax2+axsp > bx1 &&
						ay1+aysp < by2 && ay2+aysp > by1 && player.isAlive()){
						   inventoryAdd(((Item_Drop) objCol).getType(), ((Item_Drop) objCol).getId());
						   removeWorldObject(objCol);
						   playSound(snd_bop);
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
			   if(xCol || yCol){
				   ((Player) obj).setGrounded(true);
			   }
			   else{
				   ((Player) obj).setGrounded(false);
			   }
		   }
		   if(obj instanceof Item_Drop){
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
			   xx = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+axsp), wBlockSize);
			   for(int yy = yLow;yy<=yHi;yy++){
				   if(wGridBounds(xx,yy) && world.getWID(xx,yy)!=0 && world.getWID(xx,yy)!=4){
					   int xx2 = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+Math.signum(axsp)), wBlockSize);
					   /*
					   while(wGridBounds(xx,yy) && (world.getWID(xx2,yy)==0 || world.getWID(xx2,yy)==4)){
						   ax1+=Math.signum(axsp);
						   xx2 = Math.floorDiv((int) (ax1+aw/2+aw*Math.signum(axsp)/2+Math.signum(axsp)), wBlockSize);
					   }
					   */
					   //obj.setX((ax1));
					   obj.setXsp(-obj.getXsp()*0.5);
					   obj.setYsp(obj.getYsp()*0.5);
					   xCol = true;
				   }
			   }
			   
			   
			   //Grid Collision
			   //Y Collision
			   int xLow = Math.floorDiv((int) ax1, wBlockSize);
			   int xHi = Math.floorDiv((int) ax2, wBlockSize);
			   int yy = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+aysp), wBlockSize);
			   for(xx = xLow;xx<=xHi;xx++){
				   if(wGridBounds(xx,yy) && world.getWID(xx,yy)!=0 && world.getWID(xx,yy)!=4){
					   int yy2 = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+Math.signum(aysp)), wBlockSize);
					   /*
					   while(wGridBounds(xx,yy) && (world.getWID(xx,yy2)==0 || world.getWID(xx,yy2)==4)){
						   ay1+=Math.signum(aysp);
						   yy2 = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+Math.signum(aysp)), wBlockSize);
					   }
					   */
					   //obj.setY(ay1);
					   obj.setXsp(obj.getXsp()*0.5);
					   obj.setYsp(-obj.getYsp()*0.5);
					   yCol = true;
				   }
			   }
			   
			   //Corner Collision
			   if(!xCol && !yCol){
				   yLow = Math.floorDiv((int) ((int) ay1 + aysp), wBlockSize);
				   yHi = Math.floorDiv((int) ((int) ay2 + aysp), wBlockSize);
				   xLow = Math.floorDiv((int) ((int) ax1 + axsp), wBlockSize);
				   xHi = Math.floorDiv((int) ((int) ax2 + axsp), wBlockSize);
				   //yy = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+aysp), wBlockSize);
				   for(xx = xLow;xx<=xHi;xx++){
					   for(yy = yLow;yy<=yHi;yy++){
						   if(wGridBounds(xx,yy) && world.getWID(xx,yy)!=0 && world.getWID(xx,yy)!=4){
							   obj.setXsp(0);
						   }
					   }
				   }
			   }
		   }
		   obj.step();
	   }
	   //View Shake
	   if(viewShaking){
		   viewShakeTime--;
		   if(viewShakeTime<1){
			   viewShaking = false;
		   }
		   viewShakeX = random.nextInt(viewShakeMag*2 + 1) - viewShakeMag;
		   viewShakeY = random.nextInt(viewShakeMag*2 + 1) - viewShakeMag;
	   }
	   else{
		   viewShakeX = 0;
		   viewShakeY = 0;
	   }
	  //View Follow Player
      viewX += (player.getX()-viewW/2 - viewX)*0.3;
      viewY += (player.getY()-viewH/2 - viewY)*0.1;
      viewX = bindDouble(viewX,0,(wGridSizeX-1)*wBlockSize-viewW);
      viewY = bindDouble(viewY,0,(wGridSizeY-1)*wBlockSize-viewH);
      viewXFinal = viewX + viewShakeX;
      viewYFinal = viewY + viewShakeY;
      viewXFinal = Math.round(viewXFinal);
      viewYFinal = Math.round(viewYFinal);
      gamePanel.update();
   }
   
   /*******************************************************************
    * 
    * View Shake
    * -----------
    * Init the camera shake.
    * Will change to a list later to allow for multiple shakes
    * 
    *******************************************************************/
   private void viewShake(int time, int mag){
	   viewShakeTime = time;
	   viewShakeMag = mag;
	   viewShaking = true;
   }
   
   /*******************************************************************
    * 
    * Draw Game
    * ---------
    * Repaint the game
    * 
    *******************************************************************/
   private void drawGame(float interpolation)
   {
      gamePanel.setInterpolation(interpolation);
      gamePanel.repaint();
   }
   
   private class GamePanel extends JPanel
   {
	
	float interpolation;
      
      public GamePanel()
      {
    	  interpolation = 0;
      }
      
      //???????????????????????
      public void setInterpolation(float interp)
      {
         interpolation = interp;
      }
      
      //?????????????????????
      public void update()
      {
    	  
      }

      /*******************************************************************
       * 
       * Paint Component
       * ---------------
       * Update the game draw
       * 
       *******************************************************************/
      public void paintComponent(Graphics g)
      {
    	  //Draw Back
         //g.setColor(Color.blue);
         //g.fillRect(0, 0, 1280, 720);
    	  g.drawImage(bg_sky, (int) (0-viewXFinal*0.2),0,1280,720,null);
    	  g.drawImage(bg_sky, (int) (0-viewXFinal*0.2+1280),0,1280,720,null);
         //Draw Terrain
         for(int i = (int) Math.floor(viewX/wBlockSize);i<Math.floor((viewX + viewW+128)/wBlockSize);i++){
        	 for(int j = (int) Math.floor(viewY/wBlockSize);j<Math.floor((viewY + viewH+128)/wBlockSize);j++){
        		 if(i<0) i = 0;
        		 if(j<0) j = 0;
        		 if(i>wGridSizeX-1) i = wGridSizeX-1;
        		 if(j>wGridSizeY-1) j = wGridSizeY-1;
        		 if(world.getBID(i,j)!=0 && world.getWID(i,j)==0 || world.isWater(i, j)){
        			 g.drawImage(sprites[TYPE_BACK][world.getBID(i,j)], (int)(i*wBlockSize-viewXFinal), (int)(j*wBlockSize-viewYFinal), wBlockSize, wBlockSize, null);
        		 }
        		 if(world.getWID(i,j)!=0){
        			 //g.fillRect((int)(i*32-viewX), (int)(j*32-viewY), 32, 32);
        			 //g.drawImage(blockSprites[wGrid[i][j]],(int)(i*wBlockSize-viewXFinal), (int)(j*wBlockSize-viewYFinal), null);
        			 if(!world.isWater(i, j)) g.drawImage(sprites[TYPE_BLOCK][world.getWID(i,j)], (int)(i*wBlockSize-viewXFinal), (int)(j*wBlockSize-viewYFinal), wBlockSize, wBlockSize, null);
        			 else drawTile(i,j,sprites[TYPE_BLOCK][world.getWID(i, j)],g,(float)world.getWaterLevel(i, j)/4);
        			 //g.dispose();
        		 }
        		 if(world.getLight(i,j)!=0){
        			 //drawTile(i,j,spr_black,g,(float)wGrid[i][j].getLight()/lBlockLen);
        		 }
        	 }
         }
         
         //Draw
         for(int i = 0;i<objList.size();i++){
        	 WorldObject obj = objList.get(i);
        	 int vx = (int) Math.round(viewXFinal);
        	 int vy = (int) Math.round(viewYFinal);
        	 int vw = viewW;
        	 int vh = viewH;
        	 int xx = (int) (Math.round(obj.getX())-vx);
        	 int yy = (int) (Math.round(obj.getY())-vy);
        	 int ww = (int) Math.round(obj.getWidth());
        	 int hh = (int) Math.round(obj.getHeight());
			 if (xx+vx < vx+vw && xx+ww+vx > vx &&
				yy+vy < vy+vh && yy+hh+vy > vy){
	        	 if(obj instanceof Particle){
	        		 g.setColor(Color.WHITE);
	        		 g.fillRect(xx, yy, ww, hh);
	        	 }
	        	 if(obj instanceof Player && ((Entity) obj).isAlive()){
	        		 drawSprite(obj,spr_player,g);
	        		 BufferedImage img = null;
	        		 img = sprites[inventory.get(inventoryFocus).getType()][inventory.get(inventoryFocus).getId()];
	        		 g.drawImage(img, (int)(obj.getX()-viewXFinal+10), (int)(obj.getY()-viewYFinal+10), (int)obj.getWidth(), (int)obj.getHeight(), null);
	        	 }
	        	 if(obj instanceof Wall){
	        		 g.setColor(Color.GRAY);
	        		 g.fillRect(xx, yy, ww, hh);
	        	 }
	        	 if(obj instanceof Item_Drop){
	            	 BufferedImage image = null;
	            	 image = sprites[((Item_Drop)obj).getType()][((Item_Drop)obj).getId()];
	            	 g.drawImage(image, (int)(obj.getX()-viewXFinal), (int)(obj.getY()-viewYFinal), (int)obj.getWidth(), (int)obj.getHeight(), null);
	        	 }
	        	 if(obj instanceof Spike){
	        		 drawSprite(obj,spr_spike,g);
	        	 }
			 }
         }
         /*
         //Draw Lighitng
         for(int i = (int) Math.floor(viewX/lBlockSize);i<Math.floor((viewX + viewW)/lBlockSize);i++){
        	 for(int j = (int) Math.floor(viewY/lBlockSize);j<Math.floor((viewY + viewH)/lBlockSize);j++){
        		 //if(wGridBounds(i,j)){
					 Color c = new Color(0,0,0,255*lGrid[i][j]/lBlockLen);
	    			 g.setColor(c);
	    			 g.fillRect((int)(i*wBlockSize-viewXFinal), (int)(j*wBlockSize-viewYFinal), wBlockSize, wBlockSize);
        		 //}
    		}
         }
         */
         int xx = Math.floorDiv((int) (mouseX + viewXFinal),wBlockSize)*wBlockSize;
         int yy = Math.floorDiv((int) (mouseY + viewYFinal),wBlockSize)*wBlockSize;
         g.setColor(Color.red);
         g.drawRect(xx, yy, wBlockSize, wBlockSize);
         g.drawImage(spr_black, (int)mouseX, (int)mouseY, 16, 16, null);
         
         //Draw HUD
         g.setColor(Color.white);
         g.drawString(Double.toString(fps), 0, 10);
         g.drawString("Inventory Focus: "+Integer.toString(inventoryFocus), 0, 20);
         g.drawString("Inventory Size: "+Integer.toString(inventory.size()), 0, 30);
         //Hearts
         for(int i=0;i<player.getHP();i++){
        	 g.drawImage(spr_heart,32*i,0,32,32,null);
         }
         //Hunger
         g.setColor(Color.ORANGE);
         g.fillRect(0, 0, (int) ((player.getHunger()/player.getHungerMax())*256), 64);
         g.setColor(Color.white);
         g.drawRect(0, 0, 256, 64);
         
         //Inventory Bar
         g.setColor(new Color(0,0,(float)0.4,(float) 0.5));
         g.fillRect(0, 720-64-40, inventory.size()*54+4, 64);
         for(int i = 0;i<inventory.size();i++){
        	 BufferedImage image = null;
        	 image = sprites[inventory.get(i).getType()][inventory.get(i).getId()];
        	 g.drawImage(image, 54*i, 720-48*2, 48, 48, null);
        	 g.setColor(Color.black);
        	 g.fillRect(54*i+40, 720-48-12, 12, 12);
        	 g.setColor(Color.white);
             g.drawString(Integer.toString(inventory.get(i).getCount()), 54*i+40,720-48);
             //g.drawString((inventory.get(i).getType().name()), 54*i+40,720-48);
             
         }
         g.setColor(Color.cyan);
         g.drawRect(54*inventoryFocus, 720-48*2, 48, 48);
         
         frameCount++;
      }
      
      public void drawSprite(WorldObject obj, BufferedImage img, Graphics g){
     	 int vx = (int) Math.round(viewXFinal);
     	 int vy = (int) Math.round(viewYFinal);
     	 //int vw = viewW;
     	 //int vh = viewH;
     	 int xx = (int) (Math.round(obj.getX())-vx);
     	 int yy = (int) (Math.round(obj.getY())-vy);
     	 //int ww = (int) Math.round(obj.getWidth());
     	 //int hh = (int) Math.round(obj.getHeight());
 		 AffineTransform at = new AffineTransform();
         at.translate(getWidth() / 2, getHeight() / 2);
         at.rotate(Math.toRadians(obj.getAngle()));
         at.translate(-img.getWidth()/2, -img.getHeight()/2);
         // draw the image
         Graphics2D g2d = (Graphics2D) g;
		 AffineTransform backup = g2d.getTransform();
		 AffineTransform trans = new AffineTransform();
		 trans.rotate( Math.toRadians(obj.getAngle()), xx+img.getWidth()/2, yy+img.getHeight()/2 ); // the points to rotate around (the center in my example, your left side for your problem)
		 //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5));
		 g2d.transform( trans );
		 g2d.drawImage(img, xx, yy, null);
		 g2d.setTransform( backup ); // restore previous transform
      }
      
      public void drawTile(int x, int y, BufferedImage img, Graphics g, float opacity){
      	 int vx = (int) Math.round(viewXFinal);
      	 int vy = (int) Math.round(viewYFinal);
     	 int xx = (int) (Math.round(x*wBlockSize)-vx);
     	 int yy = (int) (Math.round(y*wBlockSize)-vy);
         Graphics2D g2d = (Graphics2D) g;
 		 g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
 		 g2d.drawImage(img, xx, yy, wBlockSize, wBlockSize, null);
 		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
       }
      
   }
   
   public void playSound(File snd) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(snd);
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
   
   private double bindDouble(double val, int min, int max){
		if(val<min) val = min;
		if(val>max) val = max;
		return val;
   }
   
   private boolean wGridBounds(int i, int j){
	   return i>=0 && i<wGridSizeX && j>=0 && j<wGridSizeY;
   }
   
   private void inventoryUpdate(){
	   for(int i= 0;i<inventory.size();i++){
		   if(inventory.get(i).getCount()<1){
			   inventory.remove(i);
			   i--;
			   continue;
		   }
	   }
	   if(inventoryFocus>=inventory.size()) inventoryFocus = inventory.size()-1;
   }
   
   private void inventoryAdd(int type, int id){
	   boolean found = false;
	   for(int i = 0;i<inventory.size();i++){
		   if(inventory.get(i).getType()==type && inventory.get(i).getId()==id){
			   inventory.get(i).changeCount(1);
			   System.out.println(inventory.get(i).getCount());
			   found = true;
		   }
	   }
	   if(!found){
		   inventory.add(new Item(type, id,1));
	   }
   }

@Override
public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

private Item_Drop ItemDropCreate(int xx, int yy, int type, int id){
	   WorldObject drop = addWorldObject(new Item_Drop(xx*wBlockSize+wBlockSize/4,yy*wBlockSize+wBlockSize/4,type,id,1));
	   drop.setGrav(1);
	   drop.setXsp(randomRange(-5,5));
	   drop.setYsp(randomRange(-5,5));
	   return (Item_Drop) drop;
}

@Override
public void mousePressed(MouseEvent e) {
	if(e.getButton() == MouseEvent.BUTTON1){
		if(Math.abs(e.getX()+viewXFinal - player.getX()) < 128 && Math.abs(e.getY()+viewYFinal - player.getY()) < 128){
		   int xx = Math.floorDiv((int) ((int) e.getX()+viewXFinal), wBlockSize);
		   int yy = Math.floorDiv((int) ((int) e.getY()+viewYFinal), wBlockSize);
		   if(inventory.size()>0 && inventory.get(inventoryFocus).getCount()>0){
			   //Place Block
			   if(inventory.get(inventoryFocus).getType()==TYPE_BLOCK){
				   if(world.getWID(xx,yy)==0 || world.getWID(xx,yy)==4 || world.getWID(xx,yy)==5){
					   world.setWID(xx, yy, inventory.get(inventoryFocus).getId());
					   inventory.get(inventoryFocus).changeCount(-1);
				   }
			   }
			   //Place Back
			   if(inventory.get(inventoryFocus).getType()==TYPE_BACK){
				   if(world.getBID(xx,yy)==0){
					   world.setBID(xx, yy, inventory.get(inventoryFocus).getId());
					   inventory.get(inventoryFocus).changeCount(-1);
				   }
			   }
			   //Use Tool
			   if(inventory.get(inventoryFocus).getType()==TYPE_TOOL && inventory.get(inventoryFocus).getId()==0){
				   if(world.getWID(xx,yy)!=0){
					   boolean drop = true;
					   //CUSTOMS
					   int myType = TYPE_BLOCK;
					   int myID = world.getWID(xx, yy);
					   if(myID == 2){
						   myID = 1;
					   }
					   if(myID == 6){
						   myID = 7;
					   }
					   if(myID == 5){
						   myID = 0;
						   myType = TYPE_ITEM;
					   }
					   if(myID == 3){
						   drop = false;
						   if(random.nextInt(10)==1)ItemDropCreate(xx, yy, TYPE_FOOD, 0);
					   }
					   if(drop) ItemDropCreate(xx, yy, myType, myID);
					   world.setWID(xx,yy,0);
				   }
			   }
			   if(inventory.get(inventoryFocus).getType()==TYPE_TOOL && inventory.get(inventoryFocus).getId()==1){
				   if(world.getBID(xx,yy)!=0){
					   ItemDropCreate(xx, yy, TYPE_BACK, world.getBID(xx, yy));
					   world.setBID(xx,yy,0);
				   }
			   }
			   
			   //Eat Food
			   if(inventory.get(inventoryFocus).getType()==TYPE_FOOD){
				   player.setHunger(player.getHungerMax());
				   inventory.get(inventoryFocus).changeCount(-1);
			   }
		   }
		}

	}
	if(e.getButton() == MouseEvent.BUTTON3){
	   //int xx = Math.floorDiv((int) ((int) e.getX()+viewXFinal), wBlockSize);
	   //int yy = Math.floorDiv((int) ((int) e.getY()+viewYFinal), wBlockSize);
	}
	
}

@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseDragged(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseMoved(MouseEvent arg0) {
	mouseX = arg0.getX();
	mouseY = arg0.getY()-24;
	
}

@Override
public void mouseWheelMoved(MouseWheelEvent arg0) {
	int notches = arg0.getWheelRotation();
	if(notches>0){
		inventoryFocus++;
		if(inventoryFocus>=inventory.size()) inventoryFocus = 0;
	}
	else{
		inventoryFocus--;
		if(inventoryFocus<0) inventoryFocus = inventory.size()-1;
	}
}

public void debugMsg(String message){
	System.out.println(message);
}

public int randomRange(int min, int max){
	int randomNum = random.nextInt((max - min) + 1) + min;
	return randomNum;
}

}
