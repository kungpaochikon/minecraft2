package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InventoryPanel extends JPanel implements MouseListener, MouseMotionListener {
	/**
	 * The player's inventory.
	 */
	private Inventory inventory;
	
	private Item[][] craftMatrix;
	
	private final int SPRITE_SIZE = 32;
	
	private Item tempItem;
	
	private Inventory tempInventory;
	/**
	 * The sprites of each inventory item.
	 */
	private BufferedImage[][] sprites;
	
	private int mouseX;
	
	private int mouseY;
	
	private ItemLabel dragSource;
	
	public InventoryPanel(Inventory inventory, BufferedImage[][] sprites) {
		this.inventory = inventory;
		this.tempItem = null;
		this.tempInventory = new Inventory();
		this.sprites = sprites;
		this.craftMatrix = new Item[3][3];
		this.setBackground(new Color(39, 52, 130, 170));
		this.setLayout(new GridBagLayout());
		GridBagConstraints position = new GridBagConstraints();
		position.insets = new Insets(5, 5, 5, 5);

		for (int i = 0; i < 9; i++) {
				ItemLabel box = new ItemLabel(i, true);
				box.setPreferredSize(new Dimension(SPRITE_SIZE, SPRITE_SIZE));
				box.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				position.gridx = i % 3 + 3;
				position.gridy = i / 3;
				this.add(box, position);
				box.addMouseListener(this);
				box.addMouseMotionListener(this);
		}
		
		JLabel result = new JLabel();
		result.setPreferredSize(new Dimension(SPRITE_SIZE, SPRITE_SIZE));
		result.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		position.insets = new Insets(5, 15, 5, 5);
		position.gridx = 3 + 3;
		position.gridy = 1;
		this.add(result, position);
		result.addMouseListener(this);
		result.addMouseMotionListener(this);
		
		position.insets = new Insets(5, 5, 5, 5);
		
		for (int i = 0; i < 30; i++) {
			ItemLabel item = new ItemLabel(i, false);
			item.setPreferredSize(new Dimension(SPRITE_SIZE, SPRITE_SIZE));
			if (inventory.get(i) != null) {
				Image image = sprites[inventory.get(i).getType()]
						[inventory.get(i).getId()].getScaledInstance(
						SPRITE_SIZE, SPRITE_SIZE, BufferedImage.SCALE_FAST);
				item.setIcon(new ImageIcon(image));
				item.setText(new Integer(inventory.get(i).getCount()).toString());
				item.setVerticalTextPosition(JLabel.BOTTOM);
				item.setForeground(Color.WHITE);
				item.setIconTextGap(-10);
			}
			position.gridx = i % 10;
			position.gridy = 3 + i / 10;
			item.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			this.add(item, position);
			item.addMouseListener(this);
			item.addMouseMotionListener(this);
		}
		this.setPreferredSize(new Dimension(1280 / 2, 720 / 2));
	}
	
	private class ItemLabel extends JLabel {
		private int inventoryIndex;
		private boolean crafting;
		
		ItemLabel(int i, boolean crafting) {
			super();
			inventoryIndex = i;
			this.crafting = crafting;
		}
		
		public int getIndex() {
			return inventoryIndex;
		}
		
		public void setIndex(int i) {
			inventoryIndex = i;
		}
		
		public boolean isCrafting() {
			return crafting;
		}
	}
	

	
	public static void main(String[] args) {
		Inventory i = new Inventory();
		i.add(new Item(0, 3, 5));
		i.add(new Item(0, 2, 2));
		i.add(new Item(0, 4, 2));
		BufferedImage[][] sprites = new BufferedImage[10][256];
		try {
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_DIRT] =
				ImageIO.read(new File(
				"images\\spr_dirt.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_GRASS] =
				ImageIO.read(new File(
				"images\\spr_dirt_grass.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_LEAVES] =
				ImageIO.read(new File(
				"images\\spr_leaves.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_WATER] =
				ImageIO.read(new File(
				"images\\spr_water.png"));
			sprites[Constants.TYPE_BLOCK]
				[Constants.BLOCK_DIAMOND_ORE] = 
				ImageIO.read(new File(
				"images\\spr_diamond_ore.png"));
			sprites[Constants.TYPE_BLOCK][Constants.BLOCK_STONE] =
				ImageIO.read(new File(
				"images\\spr_stone.png"));
			sprites[Constants.TYPE_BLOCK]
				[Constants.BLOCK_COBBLESTONE] = ImageIO.read(
				new File("images\\spr_cobblestone.png"));
			// Backs
			sprites[Constants.TYPE_BACK][Constants.BACK_WOOD] =
				ImageIO.read(new File(
				"images\\spr_wood.png"));
			sprites[Constants.TYPE_BACK][Constants.BACK_DIRT] =
				ImageIO.read(new File("images\\spr_back_cave.png"));
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		JFrame f = new JFrame();
		f.setBackground(Color.DARK_GRAY);
		InventoryPanel ip = new InventoryPanel(i, sprites);
		f.setSize(1280, 720);
		f.setLayout(new GridBagLayout());
		f.setResizable(false);
		GridBagConstraints gb = new GridBagConstraints();
		gb.anchor = GridBagConstraints.CENTER;
		gb.gridx = 0;
		gb.gridy = 0;
		f.add(ip, gb);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		mouseX = arg0.getX() + dragSource.getX();
		mouseY = arg0.getY() + dragSource.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		Component c = arg0.getComponent();
		if (c instanceof ItemLabel) {
			ItemLabel label = (ItemLabel) c;
			dragSource = label;
			int i = label.getIndex();
			if (arg0.getButton() == MouseEvent.BUTTON1) {
				tempItem = inventory.get(i);
				inventory.remove(i);
				label.setIcon(null);
				label.setText(null);
			}
			if (arg0.getButton() == MouseEvent.BUTTON3) {
				tempItem = new Item(inventory.get(i).getType(), inventory.get(i).getId(), 1);
				inventory.get(i).changeCount(-1);
				if (inventory.get(i).getCount() <= 0) {
					inventory.remove(i);
					label.setIcon(null);
					label.setText(null);
					return;
				}
				label.setText(new Integer(inventory.get(i).getCount()).toString());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		Component c = this.getComponentAt(mouseX, mouseY);
		ItemLabel label;
		if (c instanceof ItemLabel) {
			label = (ItemLabel) c;
			if (inventory.get(label.getIndex()) != null) {
				label = dragSource;
			}
		} else {
			label = dragSource;
		}
		if (tempItem != null) {
			int i = label.getIndex();
			if (label == dragSource && inventory.get(dragSource.getIndex()) != null) {
				inventory.get(i).changeCount(1);
				label.setText(new Integer(inventory.get(i).getCount()).toString());
				tempItem = null;
				repaint();
				return;
			}
			inventory.add(tempItem, i);
			label.setIcon(new ImageIcon(sprites[tempItem.getType()][tempItem.getId()]));
			label.setText(new Integer(inventory.get(i).getCount()).toString());
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setForeground(Color.WHITE);
			label.setIconTextGap(-10);
		}
		tempItem = null;
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
		if (tempItem != null) {
			g.drawImage(sprites[tempItem.getType()][tempItem.getId()], mouseX, mouseY, SPRITE_SIZE, SPRITE_SIZE, null);
			g.setColor(Color.WHITE);
			g.drawString(new Integer(tempItem.getCount()).toString(), mouseX + 24, mouseY + 30);
		}
	}
}
