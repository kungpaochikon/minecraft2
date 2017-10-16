package main;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Game extends JFrame
		implements ActionListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	private GamePanel gamePanel = new GamePanel();
	private Menu menu;
	private boolean running = false;
	private boolean debug = false;
	private boolean paused = false;
	private boolean playerControl;
	private boolean menu_player;
	private int fps = 60;
	private int frameCount = 0;

	// Player Instance
	private Player player;

	// instance list for game
	public ArrayList<WorldObject> objList;

	// Inventory
	public Inventory inventory;

	// View Handling
	private View view;

	// Control booleans
	private boolean moveL;
	private boolean moveR;
	private boolean mousePressed;
	private double mouseX;
	private double mouseY;

	// random
	private Random random;

	// Sprites
	private BufferedImage sprPlayer;
	private BufferedImage bg_sky;
	private BufferedImage spr_black;
	private BufferedImage spr_diamond;
	private BufferedImage spr_heart;
	private BufferedImage spr_chicken;
	private BufferedImage spr_cow;
	private BufferedImage spr_zombie;
	// private BufferedImage spr_block;
	private BufferedImage[][] sprites;
	// Sounds
	private File snd_jump;
	private File snd_death;
	private File snd_explosion;
	public File snd_bop;
	public File snd_mus_overworld;
	public File snd_mus_overworldNight;
	public File snd_mus_underground;

	// World Grid
	public WorldGrid world;
	public int wGridSizeX = 256;
	public int wGridSizeY = 256;
	public int wBlockSize = 48;
	public int wBlockLen = 256;
	public int bBlockLen = 256;
	public int lBlockLen = 5;
	public int updateInterval = 1;
	public int updateIntervalCount = 0;

	/*******************************************************************
	 * 
	 * Game ---- Set the panel and all other meta things
	 * 
	 *******************************************************************/
	public Game() {
		menu = new Menu(this);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		cp.add(menu, BorderLayout.CENTER);
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

		// Load Sprites/sounds
		try {
			sprites = new BufferedImage[10][256];
			sprPlayer = ImageIO.read(new File("images\\spr_player.png"));
			bg_sky = ImageIO.read(new File("images\\bg_clouds.png"));
			spr_black = ImageIO.read(new File("images\\spr_black.png"));
			spr_heart = ImageIO.read(new File("images\\spr_heart.png"));
			spr_chicken = ImageIO.read(new File("images\\spr_chicken.png"));
			spr_cow = ImageIO.read(new File("images\\spr_cow.png"));
			spr_zombie = ImageIO.read(new File("images\\spr_zombie.png"));
			// Terrain
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_DIRT] = ImageIO.read(new File("images\\spr_dirt.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_GRASS] = ImageIO.read(new File("images\\spr_dirt_grass.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_LEAVES] = ImageIO.read(new File("images\\spr_leaves.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_WATER] = ImageIO.read(new File("images\\spr_water.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_DIAMOND_ORE] = ImageIO
					.read(new File("images\\spr_diamond_ore.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_STONE] = ImageIO.read(new File("images\\spr_stone.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_COBBLESTONE] = ImageIO
					.read(new File("images\\spr_cobblestone.png"));
			// Backs
			sprites[Constants.TYPE_BACK][Constants.BACK_WOOD] = ImageIO.read(new File("images\\spr_wood.png"));
			sprites[Constants.TYPE_BACK][Constants.BACK_DIRT] = ImageIO.read(new File("images\\spr_back_cave.png"));
			sprites[Constants.TYPE_BACK][Constants.BACK_STONE] = ImageIO
					.read(new File("images\\spr_back_cave_stone.png"));
			// Tools
			sprites[Constants.TYPE_TOOL][Constants.PICKAXE] = ImageIO.read(new File("images\\spr_pickaxe.png"));
			sprites[Constants.TYPE_TOOL][Constants.AXE] = ImageIO.read(new File("images\\spr_axe.png"));
			sprites[Constants.TYPE_TOOL][Constants.SWORD] = ImageIO.read(new File("images\\spr_sword.png"));
			// Items
			sprites[Constants.TYPE_ITEM][Constants.ITEM_DIAMOND] = ImageIO.read(new File("images\\spr_diamond.png"));
			// Food
			sprites[Constants.TYPE_FOOD][Constants.APPLE] = ImageIO.read(new File("images\\spr_apple.png"));
			sprites[Constants.TYPE_FOOD][Constants.RAW_CHICKEN] = ImageIO.read(new File("images\\spr_chicken_raw.png"));
			sprites[Constants.TYPE_FOOD][Constants.RAW_BEEF] = ImageIO.read(new File("images\\spr_beef_raw.png"));

			// Entities
			sprites[Constants.TYPE_ENTITY][Constants.ENTITY_PLAYER] = ImageIO.read(new File("images\\spr_player.png"));
			sprites[Constants.TYPE_ENTITY][Constants.ENTITY_CHICKEN] = ImageIO
					.read(new File("images\\spr_chicken.png"));
			sprites[Constants.TYPE_ENTITY][Constants.ENTITY_COW] = ImageIO.read(new File("images\\spr_cow.png"));
			sprites[Constants.TYPE_ENTITY][Constants.ENTITY_ZOMBIE] = ImageIO.read(new File("images\\spr_zombie.png"));
			// Sounds
			snd_jump = new File("sounds\\jump.wav").getAbsoluteFile();
			snd_death = new File("sounds\\death.wav").getAbsoluteFile();
			snd_explosion = new File("sounds\\explosion.wav").getAbsoluteFile();
			snd_bop = new File("sounds\\bop.wav").getAbsoluteFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*******************************************************************
	 * 
	 * Main ---- Main Method, basically just run the game.
	 * 
	 *******************************************************************/
	public static void main(String[] args) {
		Game game = new Game();
		game.setVisible(true);
	}

	/*******************************************************************
	 * 
	 * Add World Object ---------------- Add instance to the world objects list.
	 * There is probably a better way to do this.
	 * 
	 *******************************************************************/
	private WorldObject addWorldObject(WorldObject obj) {
		objList.add(obj);
		sortWorldObjectDepth();
		return obj;
	}

	/*******************************************************************
	 * 
	 * Sort World Object Depth ----------------------- Sort the object list by
	 * depth.
	 * 
	 *******************************************************************/
	private void sortWorldObjectDepth() {
		Collections.sort(objList);
	}

	/*******************************************************************
	 * 
	 * Remove World Object ---------------- Remove instance from the world objects
	 * list. There is probably a better way to do this.
	 * 
	 *******************************************************************/
	public void removeWorldObject(WorldObject obj) {
		objList.remove(obj);
	}

	/*******************************************************************
	 * 
	 * Remove World Object index ---------------- Remove instance from the world
	 * objects list at index. There is probably a better way to do this.
	 * 
	 *******************************************************************/
	private void removeWorldObject(int i) {
		objList.remove(i);
	}

	/*******************************************************************
	 * 
	 * Game Set -------- Set the game with standard values
	 * 
	 *******************************************************************/
	private void setGame() {
		playerControl = true;
		menu_player = false;
		objList = new ArrayList<WorldObject>();
		inventory = new Inventory();
		inventory.add(new Item(Constants.TYPE_TOOL, Constants.PICKAXE, 1));
		inventory.add(new Item(Constants.TYPE_TOOL, Constants.AXE, 1));
		inventory.add(new Item(Constants.TYPE_TOOL, Constants.SWORD, 1));
		// inventoryFocus = 0;
		running = true;
		moveL = false;
		moveR = false;
		// Player
		player = new Player(100, 100);
		player.setDepth(2);
		addWorldObject(player);
		world = new WorldGrid(wGridSizeX, wGridSizeY, wBlockSize);
		world.generate();
		boolean go = true;
		// Place Player
		for (int j = 0; j < wGridSizeY; j++) {
			if (world.getWID(wGridSizeX / 2, j) != 0 && go) {
				player.setX(wGridSizeX * wBlockSize / 2);
				player.setY((j - 1) * wBlockSize);
				go = false;
			}
		}
		// Spawn Animals
		for (int i = 0; i < wGridSizeX; i++) {
			for (int j = 0; j < wGridSizeY; j++) {
				if (world.getWID(i, j + 1) != 0) {
					if (random.nextInt(25) == 1) {
						if (random.nextInt(2) == 1)
							addWorldObject(new Chicken(i * wBlockSize, (j) * wBlockSize));
						else
							addWorldObject(new Cow(i * wBlockSize, (j) * wBlockSize));
					}
					break;
				}
			}
		}
		// view = new View(player.getX()-view.getViewH()/2,
		// player.getY()-view.getViewH()/2, 1280, 720);
		view = new View();
		view.setViewW(1280);
		view.setViewH(720);
	}

	/*******************************************************************
	 * 
	 * Game Reset ---------- Reset the game
	 * 
	 *******************************************************************/
	private void reset() {
		setGame();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == menu.getNewGameButton()) {
			Container cp = getContentPane();
			cp.remove(menu);
			cp.add(gamePanel, BorderLayout.CENTER);
			setGame();
			runGameLoop();
			cp.revalidate();
			this.requestFocusInWindow();
		}
		if(e.getSource() == menu.getLoadGameButton()) {
			Container cp = getContentPane();
			cp.remove(menu);
			cp.add(gamePanel, BorderLayout.CENTER);
			setGame();
			runGameLoop();
			loadGame();
			cp.revalidate();
			this.requestFocusInWindow();
		}
	}

	/*******************************************************************
	 * 
	 * Game Input ---------- Get the keyboard input and respond
	 * 
	 *******************************************************************/
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE && playerControl) {
			if (player.isGrounded()) {
				player.jump();
				playSound(snd_jump);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_R && playerControl) {
			reset();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_E && playerControl) {
			inventory.setFocus(inventory.getFocus() + 1);
			if (inventory.getFocus() >= inventory.size())
				inventory.setFocus(0);
			;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_Q && playerControl) {
			inventory.setFocus(inventory.getFocus() - 1);
			if (inventory.getFocus() < 0)
				inventory.setFocus(inventory.size() - 1);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_A && playerControl) {
			moveL = true;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_D && playerControl) {
			moveR = true;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_F && playerControl) {
			player.destroy();
			view.viewShake(20, 50);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_I) {
			menu_player = !menu_player;
		}
		// Select Inventory with Numbs
		if (arg0.getKeyCode() == KeyEvent.VK_1 && playerControl) {
			int num = 0;
			if (inventory.size() > num)
				inventory.setFocus(num);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_2 && playerControl) {
			int num = 1;
			if (inventory.size() > num)
				inventory.setFocus(num);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_3 && playerControl) {
			int num = 2;
			if (inventory.size() > num)
				inventory.setFocus(num);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_4 && playerControl) {
			int num = 3;
			if (inventory.size() > num)
				inventory.setFocus(num);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_M) {
			debug = !debug;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_K) {
			saveGame();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_L) {
			loadGame();
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_A && playerControl) {
			moveL = false;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_D && playerControl) {
			moveR = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*******************************************************************
	 * 
	 * Save Game ------------- Save game to file
	 * 
	 *******************************************************************/
	public void saveGame() {
		PrintWriter out = null;
		try {

			out = new PrintWriter(new BufferedWriter(new FileWriter("save")));

		}

		catch (IOException e) {

			e.printStackTrace();

		}
		out.println(Double.toString(player.getX()) + ":" + Double.toString(player.getY()));
		for (int i = 0; i < wGridSizeX; i++) {
			for (int j = 0; j < wGridSizeY; j++) {
				out.println(Integer.toString(i) + ":" + Integer.toString(j) + ":" + Integer.toString(world.getWID(i, j))
						+ ":" + Integer.toString(world.getBID(i, j)));
			}
		}
		out.close();
	}

	public void loadGame() {
		try {
			Scanner fileReader = new Scanner(new File("save"));
			String playerLine = fileReader.nextLine();
			String[] playerData = playerLine.split(":");
			player.setX(Double.parseDouble(playerData[0]));
			player.setY(Double.parseDouble(playerData[1]));
			while (fileReader.hasNextLine()) {
				String line = fileReader.nextLine();
				String[] data = line.split(":");
				world.setWID(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
				world.setBID(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[3]));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*******************************************************************
	 * 
	 * Run Game Loop ------------- Simple init of the game loop itself
	 * 
	 *******************************************************************/
	public void runGameLoop() {
		Thread loop = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		loop.start();
	}

	/*******************************************************************
	 * 
	 * Game Loop --------- Better game loop, doesn't max out cpu like a plain old
	 * while loop and has measures in place to smooth out the updates and renders
	 * 
	 *******************************************************************/
	// Only run this in another Thread!
	private void gameLoop() {
		// This value would probably be stored elsewhere.
		final double GAME_HERTZ = 60.0;
		// Calculate how many ns each frame should take for our target game hertz.
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		// At the very most we will update the game this many times before a new render.
		// If you're worried about visual hitches more than perfect timing, set this to
		// 1.
		final int MAX_UPDATES_BEFORE_RENDER = 100;
		// We will need the last update time.
		double lastUpdateTime = System.nanoTime();
		// Store the last time we rendered.
		double lastRenderTime = System.nanoTime();

		// If we are able to get as high as this FPS, don't render again.
		final double TARGET_FPS = 60;
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

		// Simple way of finding FPS.
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);

		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;
			if (!paused) {
				// Do as many game updates as we need to, potentially playing catchup.
				while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
					updateGame();
					lastUpdateTime += TIME_BETWEEN_UPDATES;
					updateCount++;
				}

				// If for some reason an update takes forever, we don't want to do an insane
				// number of catchups.
				// If you were doing some sort of game that needed to keep EXACT time, you would
				// get rid of this.
				if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
					lastUpdateTime = now - TIME_BETWEEN_UPDATES;
				}

				// Render. To do so, we need to calculate interpolation for a smooth render.
				float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
				drawGame(interpolation);
				lastRenderTime = now;

				// Update the frames we got.
				int thisSecond = (int) (lastUpdateTime / 1000000000);
				if (thisSecond > lastSecondTime) {
					// System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
					fps = frameCount;
					frameCount = 0;
					lastSecondTime = thisSecond;
				}

				// Yield until it has been at least the target time between renders. This saves
				// the CPU from hogging.
				while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS
						&& now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
					Thread.yield();

					// This stops the app from consuming all your CPU. It makes this slightly less
					// accurate, but is worth it.
					// You can remove this line and it will still work (better), your CPU just
					// climbs on certain OSes.
					// FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a
					// look at different peoples' solutions to this.
					try {
						Thread.sleep(1);
					} catch (Exception e) {
					}

					now = System.nanoTime();
				}
			}
		}
	}

	/*******************************************************************
	 * 
	 * Update Game --------- Update the game by going through everything that
	 * changes per step.
	 * 
	 *******************************************************************/
	private void updateGame() {
		// Update inventory
		inventoryUpdate();
		// Player Movement
		if (moveL) {
			player.setXsp(player.getXsp() - 1);
		}
		if (moveR) {
			player.setXsp(player.getXsp() + 1);
		}
		if (Math.abs(player.getXsp()) > player.getXsp_max()) {
			player.setXsp(player.getXsp_max() * Math.signum(player.getXsp()));
		}
		if (!moveL && !moveR) {
			if (Math.abs(player.getXsp()) > 1) {
				player.setXsp(player.getXsp() - 1 * Math.signum(player.getXsp()));
			} else {
				player.setXsp(0);
			}
		}

		// Update World In View
		if (updateIntervalCount >= updateInterval) {
			updateIntervalCount = 0;
			world.update((int) Math.floor(view.getViewX() / wBlockSize),
					(int) Math.floor((view.getViewX() + view.getViewH()) / wBlockSize),
					(int) Math.floor(view.getViewY() / wBlockSize),
					(int) Math.floor((view.getViewY() + view.getViewH()) / wBlockSize));
		}
		updateIntervalCount++;
		if (player.getJumpSequence() > 2) {
			addWorldObject(new Particle(player.getX() + 8, player.getY() + 8, 0, 0, 30));
		}
		// if(!player.isGrounded()) player.setAngle(player.getAngle()+player.getXsp());
		// else player.setAngle(Math.round(player.getAngle()/90)*90);
		for (int i = 0; i < objList.size(); i++) {
			WorldObject obj = objList.get(i);
			if (obj.destroyed()) {
				removeWorldObject(i);
				i--;
				continue;
			}
			obj.doGrav();
			// obj.step();
			if (obj instanceof Item_Drop) {
				double ax1 = obj.getX();
				double ax2 = obj.getX() + obj.getWidth();
				double ay1 = obj.getY();
				double ay2 = obj.getY() + obj.getHeight();
				double axsp = obj.getXsp();
				double aysp = obj.getYsp();
				double aw = obj.getWidth();
				double ah = obj.getHeight();
				boolean xCol = false;
				boolean yCol = false;

				// Grid Collision
				int xx;
				// X Collision
				int yLow = Math.floorDiv((int) ay1, wBlockSize);
				int yHi = Math.floorDiv((int) ay2, wBlockSize);
				xx = Math.floorDiv((int) (ax1 + aw / 2 + aw * Math.signum(axsp) / 2 + axsp), wBlockSize);
				for (int yy = yLow; yy <= yHi; yy++) {
					if (wGridBounds(xx, yy) && world.getWID(xx, yy) != 0 && world.getWID(xx, yy) != 4) {
						int xx2 = Math.floorDiv((int) (ax1 + aw / 2 + aw * Math.signum(axsp) / 2 + Math.signum(axsp)),
								wBlockSize);
						/*
						 * while(wGridBounds(xx,yy) && (world.getWID(xx2,yy)==0 ||
						 * world.getWID(xx2,yy)==4)){ ax1+=Math.signum(axsp); xx2 = Math.floorDiv((int)
						 * (ax1+aw/2+aw*Math.signum(axsp)/2+Math.signum(axsp)), wBlockSize); }
						 */
						// obj.setX((ax1));
						obj.setXsp(-obj.getXsp() * 0.5);
						obj.setYsp(obj.getYsp() * 0.5);
						xCol = true;
					}
				}

				// Grid Collision
				// Y Collision
				int xLow = Math.floorDiv((int) ax1, wBlockSize);
				int xHi = Math.floorDiv((int) ax2, wBlockSize);
				int yy = Math.floorDiv((int) (ay1 + ah / 2 + ah * Math.signum(aysp) / 2 + aysp), wBlockSize);
				for (xx = xLow; xx <= xHi; xx++) {
					if (wGridBounds(xx, yy) && world.getWID(xx, yy) != 0 && world.getWID(xx, yy) != 4) {
						int yy2 = Math.floorDiv((int) (ay1 + ah / 2 + ah * Math.signum(aysp) / 2 + Math.signum(aysp)),
								wBlockSize);
						/*
						 * while(wGridBounds(xx,yy) && (world.getWID(xx,yy2)==0 ||
						 * world.getWID(xx,yy2)==4)){ ay1+=Math.signum(aysp); yy2 = Math.floorDiv((int)
						 * (ay1+ah/2+ah*Math.signum(aysp)/2+Math.signum(aysp)), wBlockSize); }
						 */
						// obj.setY(ay1);
						obj.setXsp(obj.getXsp() * 0.5);
						obj.setYsp(-obj.getYsp() * 0.5);
						yCol = true;
					}
				}

				// Corner Collision
				if (!xCol && !yCol) {
					yLow = Math.floorDiv((int) ((int) ay1 + aysp), wBlockSize);
					yHi = Math.floorDiv((int) ((int) ay2 + aysp), wBlockSize);
					xLow = Math.floorDiv((int) ((int) ax1 + axsp), wBlockSize);
					xHi = Math.floorDiv((int) ((int) ax2 + axsp), wBlockSize);
					// yy = Math.floorDiv((int) (ay1+ah/2+ah*Math.signum(aysp)/2+aysp), wBlockSize);
					for (xx = xLow; xx <= xHi; xx++) {
						for (yy = yLow; yy <= yHi; yy++) {
							if (wGridBounds(xx, yy) && world.getWID(xx, yy) != 0 && world.getWID(xx, yy) != 4) {
								obj.setXsp(0);
							}
						}
					}
				}
			}
			obj.step(this);
		}
		view.step();
		// View Follow Player
		view.setViewX(view.getViewX() + (player.getX() - view.getViewW() / 2 - view.getViewX()) * 0.3);
		view.setViewY(view.getViewY() + (player.getY() - view.getViewH() / 2 - view.getViewY()) * 0.1);
		view.setViewX(bindDouble(view.getViewX(), 0, (wGridSizeX - 1) * wBlockSize - view.getViewW()));
		view.setViewY(bindDouble(view.getViewY(), 0, (wGridSizeX - 1) * wBlockSize - view.getViewH()));
		gamePanel.update();
	}

	/*******************************************************************
	 * 
	 * Draw Game --------- Repaint the game
	 * 
	 *******************************************************************/
	private void drawGame(float interpolation) {
		gamePanel.setInterpolation(interpolation);
		gamePanel.repaint();
	}

	private class GamePanel extends JPanel {

		float interpolation;

		public GamePanel() {
			interpolation = 0;
		}

		// ???????????????????????
		public void setInterpolation(float interp) {
			interpolation = interp;
		}

		// ?????????????????????
		public void update() {

		}

		/*******************************************************************
		 * 
		 * Paint Component --------------- Update the game draw
		 * 
		 *******************************************************************/
		public void paintComponent(Graphics g) {
			// Draw Back
			// g.setColor(Color.blue);
			// g.fillRect(0, 0, 1280, 720);
			g.drawImage(bg_sky, (int) (0 - view.getViewXFinal() * 0.2), 0, 1280, 720, null);
			g.drawImage(bg_sky, (int) (0 - view.getViewXFinal() * 0.2 + 1280), 0, 1280, 720, null);
			g.drawImage(bg_sky, (int) (0 - view.getViewXFinal() * 0.2 + 1280), 0, 1280 * 2, 720, null);
			g.drawImage(bg_sky, (int) (0 - view.getViewXFinal() * 0.2 + 1280), 0, 1280 * 3, 720, null);

			// Draw Terrain
			for (int i = (int) Math.floor(view.getViewX() / wBlockSize); i < Math
					.floor((view.getViewX() + view.getViewW() + 128) / wBlockSize); i++) {
				for (int j = (int) Math.floor(view.getViewY() / wBlockSize); j < Math
						.floor((view.getViewY() + view.getViewH() + 128) / wBlockSize); j++) {

					// if(i<0) i = 0;
					// if(j<0) j = 0;
					// if(i>wGridSizeX-1) i = wGridSizeX-1;
					// if(j>wGridSizeY-1) j = wGridSizeY-1;
					if (wGridBounds(i, j) && world.getBID(i, j) != 0 && world.getWID(i, j) == 0
							|| world.isWater(i, j)) {
						g.drawImage(sprites[Constants.TYPE_BACK][world.getBID(i, j)],
								(int) (i * wBlockSize - view.getViewXFinal()),
								(int) (j * wBlockSize - view.getViewYFinal()), wBlockSize, wBlockSize, null);
					}
					if (wGridBounds(i, j) && world.getWID(i, j) != 0) {
						if (!world.isWater(i, j)) {
							g.drawImage(sprites[Constants.TYPE_BLOCK][world.getWID(i, j)],
									(int) (i * wBlockSize - view.getViewXFinal()),
									(int) (j * wBlockSize - view.getViewYFinal()), wBlockSize, wBlockSize, null);
						} else {
							drawTile(i, j, sprites[Constants.TYPE_BLOCK][world.getWID(i, j)], g,
									(float) world.getWaterLevel(i, j) / 4);
						}
					}

				}
			}

			// Draw
			for (int i = 0; i < objList.size(); i++) {
				WorldObject obj = objList.get(i);
				int vx = (int) Math.round(view.getViewXFinal());
				int vy = (int) Math.round(view.getViewYFinal());
				int vw = view.getViewW();
				int vh = view.getViewH();
				int xx = (int) (Math.round(obj.getX()) - vx);
				int yy = (int) (Math.round(obj.getY()) - vy);
				int ww = (int) Math.round(obj.getWidth());
				int hh = (int) Math.round(obj.getHeight());
				if (xx + vx < vx + vw && xx + ww + vx > vx && yy + vy < vy + vh && yy + hh + vy > vy) {
					if (obj instanceof Particle) {
						g.setColor(Color.WHITE);
						g.fillRect(xx, yy, ww, hh);
					}
					if (obj instanceof Player && ((Entity) obj).isAlive()) {
						AffineTransform at = new AffineTransform();
						drawSprite(obj, sprPlayer, g);
						BufferedImage img = null;
						img = sprites[inventory.getFocused().getType()][inventory.getFocused().getId()];
						at.translate((int) (obj.getX() - view.getViewXFinal() + 10),
								(int) (obj.getY() - view.getViewYFinal() + 10));
						if (mousePressed) {
							pressMouse(mouseX, mouseY);
							if (System.currentTimeMillis() - player.getLastSwing() <= Constants.SWING_COOL_DOWN / 2) {
								at.rotate(Math.PI * (System.currentTimeMillis() - player.getLastSwing()) / 500);
							} else {
								at.rotate(
										Math.PI * (System.currentTimeMillis() - player.getLastSwing()) / -500 + 0.628);
							}
						}
						Graphics2D g2d = (Graphics2D) g;
						g2d.drawImage(img, at, null);
					}
					if (obj instanceof Wall) {
						g.setColor(Color.GRAY);
						g.fillRect(xx, yy, ww, hh);
					}
					if (obj instanceof Item_Drop) {
						BufferedImage image = null;
						image = sprites[((Item_Drop) obj).getType()][((Item_Drop) obj).getId()];
						g.drawImage(image, (int) (obj.getX() - view.getViewXFinal()),
								(int) (obj.getY() - view.getViewYFinal()), (int) obj.getWidth(), (int) obj.getHeight(),
								null);
					}
					if (obj instanceof Chicken) {
						drawSprite(obj, spr_chicken, g);
					}
					if (obj instanceof Cow) {
						drawSprite(obj, spr_cow, g);
					}
					if (obj instanceof Enemy) {
						drawSprite(obj, spr_zombie, g);
					}
				}
			}
			/*
			 * //Draw Lighitng for(int i = (int)
			 * Math.floor(viewX/lBlockSize);i<Math.floor((viewX + viewW)/lBlockSize);i++){
			 * for(int j = (int) Math.floor(viewY/lBlockSize);j<Math.floor((viewY +
			 * viewH)/lBlockSize);j++){ //if(wGridBounds(i,j)){ Color c = new
			 * Color(0,0,0,255*lGrid[i][j]/lBlockLen); g.setColor(c);
			 * g.fillRect((int)(i*wBlockSize-viewXFinal), (int)(j*wBlockSize-viewYFinal),
			 * wBlockSize, wBlockSize); //} } }
			 */
			int xx = (int) (Math.floorDiv((int) (mouseX + view.getViewXFinal()), wBlockSize) * wBlockSize
					- view.getViewXFinal());
			int yy = (int) (Math.floorDiv((int) (mouseY + view.getViewYFinal()), wBlockSize) * wBlockSize
					- view.getViewYFinal());
			if (world.getWID(Math.floorDiv((int) (mouseX + view.getViewXFinal()), wBlockSize),
					Math.floorDiv((int) (mouseY + view.getViewYFinal()), wBlockSize)) != 0) {
				g.setColor(Color.white);
				g.drawRect(xx, yy, wBlockSize, wBlockSize);
			}
			g.drawImage(spr_black, (int) mouseX, (int) mouseY, 16, 16, null);

			// Player Menu
			if (menu_player) {
				g.setColor(new Color(0, 0, (float) 0.4, (float) 0.9));
				g.fillRect(1280 / 4, 720 / 4, 1280 / 2, 720 / 2);
				g.setColor(Color.white);
				g.drawRect(1280 / 4, 720 / 4, 1280 / 2, 720 / 2);
				for (int i = 0; i < inventory.size(); i++) {
					BufferedImage image = null;
					image = sprites[inventory.get(i).getType()][inventory.get(i).getId()];
					g.drawImage(image, 1280 / 4 + 54 * i, 720 / 2 - 48 * 2, 48, 48, null);
					g.setColor(Color.black);
					g.fillRect(1280 / 4 + 54 * i + 40, 720 / 2 - 48 - 12, 12, 12);
					g.setColor(Color.white);
					g.drawString(Integer.toString(inventory.get(i).getCount()), 1280 / 4 + 54 * i + 40, 720 / 2 - 48);
					// g.drawString((inventory.get(i).getType().name()), 54*i+40,720-48);

				}
			}

			// Draw HUD

			// Hearts
			for (int i = 0; i < player.getHP(); i++) {
				g.drawImage(spr_heart, 32 * i, 0, 32, 32, null);
			}
			// Hunger
			g.setColor(Color.ORANGE);
			g.fillRect(0, 32, (int) ((player.getHunger() / player.getHungerMax()) * 96), 32);
			g.setColor(Color.white);
			g.drawRect(0, 32, 96, 32);

			// Inventory Bar
			g.setColor(new Color(0, 0, (float) 0.4, (float) 0.5));
			g.fillRect(0, 720 - 64 - 40, inventory.getMax() * 54 + 4, 64);
			for (int i = 0; i < inventory.getMax(); i++) {
				g.setColor(Color.white);
				g.drawRect(i * 54, 720 - 64 - 40 + 8, 48, 48);
			}
			for (int i = 0; i < inventory.size(); i++) {
				BufferedImage image = null;
				image = sprites[inventory.get(i).getType()][inventory.get(i).getId()];
				g.drawImage(image, 54 * i, 720 - 48 * 2, 48, 48, null);
				g.setColor(Color.black);
				g.fillRect(54 * i + 40, 720 - 48 - 12, 12, 12);
				g.setColor(Color.white);
				g.drawString(Integer.toString(inventory.get(i).getCount()), 54 * i + 40, 720 - 48);
				// g.drawString((inventory.get(i).getType().name()), 54*i+40,720-48);

			}
			g.setColor(Color.cyan);
			g.drawRect(54 * inventory.getFocus(), 720 - 48 * 2, 48, 48);

			// DRAW DEBUG
			if (debug) {
				// Info
				g.setColor(new Color((float) 0.1, (float) 0.1, (float) 0.1, (float) 0.5));
				g.fillRect(1280 - 256, 0, 256, 256);
				g.setColor(Color.white);
				g.drawRect(1280 - 256, 0, 256, 256);
				int i = 1280 - 256 + 2;
				int j = 1;
				int h = 16;
				g.drawString("DEBUG MENU", i, j * h);
				j++;
				g.drawString("FPS: " + Double.toString(fps), i, j * h);
				j++;
				g.drawString("X: " + Double.toString(player.getX()), i, j * h);
				j++;
				g.drawString("Y: " + Double.toString(player.getY()), i, j * h);
				j++;
				g.drawString("X Grid: " + Integer.toString(Math.floorDiv((int) player.getX(), wBlockSize)), i, j * h);
				j++;
				g.drawString("Y Grid: " + Integer.toString(Math.floorDiv((int) player.getY(), wBlockSize)), i, j * h);
				j++;
				g.drawString("objList Size: " + Integer.toString(objList.size()), i, j * h);
				j++;
				g.drawString("ViewX: " + Double.toString(view.getViewX()), i, j * h);
				j++;
				g.drawString("ViewY: " + Double.toString(view.getViewY()), i, j * h);
				j++;
				g.drawString("ViewW: " + Integer.toString(view.getViewW()), i, j * h);
				j++;
				g.drawString("ViewH: " + Integer.toString(view.getViewH()), i, j * h);
				j++;

				// Mouse
				i = (int) mouseX;
				j = (int) mouseY;
				g.setColor(Color.black);
				// g.drawString(str, x, y);
			}

			frameCount++;
		}

		public void drawSprite(WorldObject obj, BufferedImage img, Graphics g) {
			int vx = (int) Math.round(view.getViewXFinal());
			int vy = (int) Math.round(view.getViewYFinal());
			// int vw = viewW;
			// int vh = viewH;
			int xx = (int) (Math.round(obj.getX()) - vx);
			int yy = (int) (Math.round(obj.getY()) - vy);
			// int ww = (int) Math.round(obj.getWidth());
			// int hh = (int) Math.round(obj.getHeight());
			AffineTransform at = new AffineTransform();
			at.translate(getWidth() / 2, getHeight() / 2);
			at.rotate(Math.toRadians(obj.getAngle()));
			at.translate(-img.getWidth() / 2, -img.getHeight() / 2);
			// draw the image
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform backup = g2d.getTransform();
			AffineTransform trans = new AffineTransform();
			trans.rotate(Math.toRadians(obj.getAngle()), xx + img.getWidth() / 2, yy + img.getHeight() / 2); // the
																												// points
																												// to
																												// rotate
																												// around
																												// (the
																												// center
																												// in my
																												// example,
																												// your
																												// left
																												// side
																												// for
																												// your
																												// problem)
			// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)
			// 0.5));
			g2d.transform(trans);
			g2d.drawImage(img, xx, yy, null);
			g2d.setTransform(backup); // restore previous transform
		}

		public void drawTile(int x, int y, BufferedImage img, Graphics g, float opacity) {
			int vx = (int) Math.round(view.getViewXFinal());
			int vy = (int) Math.round(view.getViewYFinal());
			int xx = (int) (Math.round(x * wBlockSize) - vx);
			int yy = (int) (Math.round(y * wBlockSize) - vy);
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
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	private double bindDouble(double val, int min, int max) {
		if (val < min)
			val = min;
		if (val > max)
			val = max;
		return val;
	}

	public boolean wGridBounds(int i, int j) {
		return i >= 0 && i < wGridSizeX && j >= 0 && j < wGridSizeY;
	}

	private void inventoryUpdate() {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getCount() < 1) {
				inventory.remove(i);
				i--;
				continue;
			}
		}
		if (inventory.getFocus() >= inventory.size())
			inventory.setFocus(inventory.size() - 1);
	}

	public void inventoryAdd(int type, int id) {
		boolean found = false;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getType() == type && inventory.get(i).getId() == id) {
				inventory.get(i).changeCount(1);
				System.out.println(inventory.get(i).getCount());
				found = true;
			}
		}
		if (!found) {
			inventory.add(new Item(type, id, 1));
		}
	}

	public boolean inventoryCheck(int type, int id) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getType() == type && inventory.get(i).getId() == id) {
				return true;
			}
		}
		return false;
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

	private Item_Drop ItemDropCreate(int xx, int yy, int type, int id) {
		WorldObject drop = addWorldObject(new Item_Drop(xx, yy, type, id, 1));
		drop.setGrav(1);
		drop.setXsp(randomRange(-5, 5));
		drop.setYsp(randomRange(-5, 5));
		return (Item_Drop) drop;
	}

	private void pressMouse(double mouseX2, double mouseY2) {
		if (System.currentTimeMillis() - player.getLastSwing() >= Constants.SWING_COOL_DOWN) {
			player.setLastSwing(System.currentTimeMillis());
			if (Math.abs(mouseX2 + view.getViewXFinal() - player.getX()) < 128
					&& Math.abs(mouseY2 + view.getViewYFinal() - player.getY()) < 128) {
				int xx = Math.floorDiv((int) ((int) mouseX2 + view.getViewXFinal()), wBlockSize);
				int yy = Math.floorDiv((int) ((int) mouseY2 - 24 + view.getViewYFinal()), wBlockSize);
				int xxx = xx * wBlockSize + wBlockSize / 4;
				int yyy = yy * wBlockSize + wBlockSize / 4;
				if (inventory.size() > 0 && inventory.getFocused().getCount() > 0) {
					// Place Block
					if (inventory.getFocused().getType() == Constants.TYPE_BLOCK) {
						if (world.getWID(xx, yy) == Constants.BLOCK_AIR || world.getWID(xx, yy) == Constants.BLOCK_WATER
								|| world.getWID(xx, yy) == 5) {
							world.setWID(xx, yy, inventory.getFocused().getId());
							inventory.getFocused().changeCount(-1);
						}
					}
					// Place Back
					if (inventory.getFocused().getType() == Constants.TYPE_BACK) {
						if (world.getBID(xx, yy) == Constants.BACK_AIR) {
							world.setBID(xx, yy, inventory.getFocused().getId());
							inventory.getFocused().changeCount(-1);
						}
					}
					// Use Tool
					if (inventory.getFocused().getType() == Constants.TYPE_TOOL
							&& inventory.getFocused().getId() == Constants.PICKAXE) {
						Player.setSwinging(true);
						if (world.getWID(xx, yy) != Constants.BLOCK_AIR) {
							boolean drop = true;
							// CUSTOMS
							int myType = Constants.TYPE_BLOCK;
							int myID = world.getWID(xx, yy);
							world.getBlock(xx, yy).hitBlock(30);
							if (world.getBlock(xx, yy).getIntegrity() < 0) {
								if (myID == Constants.BLOCK_GRASS) {
									myID = Constants.BLOCK_DIRT;
								}
								if (myID == Constants.BLOCK_STONE) {
									myID = Constants.BLOCK_COBBLESTONE;
								}
								if (myID == Constants.BLOCK_DIAMOND_ORE) {
									myID = Constants.ITEM_DIAMOND;
									myType = Constants.TYPE_ITEM;
								}
								if (myID == Constants.BLOCK_LEAVES) {
									drop = false;
									if (random.nextInt(10) == 1) {
										ItemDropCreate(xxx, yyy, Constants.TYPE_FOOD, Constants.APPLE);
									}
								}
								if (drop) {
									ItemDropCreate(xxx, yyy, myType, myID);
								}
								world.setWID(xx, yy, Constants.BLOCK_AIR);
							}
						}
					}
					if (inventory.getFocused().getType() == Constants.TYPE_TOOL
							&& inventory.getFocused().getId() == Constants.AXE) {
						Player.setSwinging(true);
						if (world.getBID(xx, yy) != Constants.BACK_AIR) {
							ItemDropCreate(xxx, yyy, Constants.TYPE_BACK, world.getBID(xx, yy));
							world.setBID(xx, yy, Constants.BACK_AIR);
						}
					}

					if (inventory.getFocused().getType() == Constants.TYPE_TOOL
							&& inventory.getFocused().getId() == Constants.SWORD) {
						Player.setSwinging(true);
						double x = mouseX2 + view.getViewXFinal();
						double y = mouseY2 + view.getViewYFinal();
						for (int i = 0; i < objList.size(); i++) {
							WorldObject obj = objList.get(i);
							if (obj instanceof Animal) {
								if (x > obj.getX() && x < obj.getX() + obj.getWidth() && y > obj.getY()
										&& y < obj.getY() + obj.getHeight()) {
									if (obj instanceof Chicken) {
										ItemDropCreate((int) x, (int) y, Constants.TYPE_FOOD, Constants.RAW_CHICKEN);
									}
									if (obj instanceof Cow) {
										ItemDropCreate((int) x, (int) y, Constants.TYPE_FOOD, Constants.RAW_BEEF);
									}
									obj.destroy();
								}
							}
						}
					}

					// Eat Food
					if (inventory.getFocused().getType() == Constants.TYPE_FOOD) {
						player.setHunger(player.getHungerMax());
						inventory.getFocused().changeCount(-1);
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			mousePressed = true;
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (debug) {
				addWorldObject(new Enemy(e.getX() + view.getViewXFinal(), e.getY() + view.getViewYFinal()));
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY() - 24;

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY() - 24;

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int notches = arg0.getWheelRotation();
		if (notches > 0) {
			inventory.setFocus(inventory.getFocus() + 1);
			if (inventory.getFocus() >= inventory.size())
				inventory.setFocus(0);
		} else {
			inventory.setFocus(inventory.getFocus() - 1);
			if (inventory.getFocus() < 0)
				inventory.setFocus(inventory.size() - 1);
		}
	}

	public void debugMsg(String message) {
		System.out.println(message);
	}

	public int randomRange(int min, int max) {
		int randomNum = random.nextInt((max - min) + 1) + min;
		return randomNum;
	}

}
