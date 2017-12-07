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

	private ItemLabel[] craftingLabels;

	private final int SPRITE_SIZE = 32;

	private Item tempItem;

	private Inventory tempInventory;
	/**
	 * The sprites of each inventory item.
	 */
	private BufferedImage[][] sprites;

	private Recipe[] recipes = {
			 Recipe.createRecipe(new Item[] {
					 null, Constants.DIAMOND_ITEM, null,
					 null, Constants.DIAMOND_ITEM, null,
					 null, Constants.WOOD_ITEM, null },
			 Constants.SWORD_ITEM),
			 Recipe.createRecipe(new Item[] {
					 Constants.DIAMOND_ITEM, Constants.DIAMOND_ITEM, Constants.DIAMOND_ITEM,
					 null, Constants.WOOD_ITEM, null,
					 null, Constants.WOOD_ITEM, null },
			 Constants.PICKAXE_ITEM),
			 Recipe.createRecipe(new Item[] {
					 null, Constants.DIAMOND_ITEM, Constants.DIAMOND_ITEM,
					 null, Constants.WOOD_ITEM, Constants.DIAMOND_ITEM,
					 null, Constants.WOOD_ITEM, null },
			 Constants.AXE_ITEM),
			 Recipe.createRecipe(new Item[] {
					 null, null, null,
					 null, Constants.WOOD_ITEM, null,
					 null, null, null },
			 Constants.PLANKS_ITEM),
			 Recipe.createRecipe(new Item[] {
					 null, null, null,
					 Constants.APPLE_ITEM, Constants.APPLE_ITEM, Constants.APPLE_ITEM,
					 null, null, null },
			 Constants.APPLE_PIE_ITEM)
	};

	private int numRecipes;

	private int mouseX;

	private int mouseY;

	private JLabel dragSource;
	
	private boolean dragResult;

	private ResultLabel result;

	public InventoryPanel(Inventory inventory, BufferedImage[][] sprites) {
		dragResult = false;
		this.numRecipes = 0;
		this.inventory = inventory;
		this.tempItem = null;
		this.tempInventory = new Inventory(9);
		this.sprites = sprites;
		this.craftingLabels = new ItemLabel[9];
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
			craftingLabels[i] = box;
			this.add(box, position);
			box.addMouseListener(this);
			box.addMouseMotionListener(this);
		}

		result = new ResultLabel(null);
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
				Image image = sprites[inventory.get(i).getType()][inventory.get(i).getId()]
						.getScaledInstance(SPRITE_SIZE, SPRITE_SIZE, BufferedImage.SCALE_FAST);
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

	private class ResultLabel extends JLabel {
		private Item item;

		ResultLabel(Item item) {
			this.item = item;
		}

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		public void setItem(int type, int id) {
			this.item = new Item(type, id, 1);
		}
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
		int t = 4;
		Recipe r = Recipe.createRecipe(
				new Item[] { null, new Item(Constants.TYPE_ITEM, Constants.ITEM_DIAMOND, 1), null, null,
						new Item(Constants.TYPE_ITEM, Constants.ITEM_DIAMOND, 1), null, null,
						new Item(Constants.TYPE_BACK, Constants.BACK_WOOD, 1), null },
				new Item(Constants.TYPE_ITEM, Constants.SWORD, 1));
		Inventory i = new Inventory();
		i.add(new Item(0, 3, 5));
		i.add(new Item(0, 2, 2));
		i.add(new Item(0, 4, 2));
		i.add(new Item(Constants.TYPE_BACK, Constants.BACK_WOOD, 5));
		i.add(new Item(Constants.TYPE_ITEM, Constants.ITEM_DIAMOND, 5));
		BufferedImage[][] sprites = new BufferedImage[10][256];
		try {
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
			sprites[Constants.TYPE_FOOD][Constants.APPLE_PIE] = ImageIO.read(new File("images\\spr_apple_pie.png"));

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
		if (!dragResult) {
			mouseX = arg0.getX() + dragSource.getX();
			mouseY = arg0.getY() + dragSource.getY();
		} else {
			mouseX = arg0.getX() + result.getX();
			mouseY = arg0.getY() + result.getY();
		}
		// repaint();
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
			Inventory targetInventory;

			if (label.isCrafting()) {
				targetInventory = tempInventory;
			} else {
				targetInventory = inventory;
			}

			if (arg0.getButton() == MouseEvent.BUTTON1) {
				tempItem = targetInventory.get(i);
				targetInventory.remove(i);
				label.setIcon(null);
				label.setText(null);
			}

			if (arg0.getButton() == MouseEvent.BUTTON3 && targetInventory.get(i) != null) {
				tempItem = new Item(targetInventory.get(i).getType(), targetInventory.get(i).getId(), 1);
				targetInventory.get(i).changeCount(-1);
				if (targetInventory.get(i).getCount() <= 0) {
					targetInventory.remove(i);
					label.setIcon(null);
					label.setText(null);
					return;
				}
				label.setText(new Integer(targetInventory.get(i).getCount()).toString());
			}
		}

		if (c instanceof ResultLabel) {
			ResultLabel label = (ResultLabel) c;
			ResultLabel dragSource = null;
			tempItem = label.getItem();
			label.setItem(null);
			label.setIcon(null);
			label.setText(null);
			dragResult = true;
			for (int i = 0; i < 9; i++) {
				if (tempInventory.get(i) != null) {
					tempInventory.get(i).changeCount(-1);
					craftingLabels[i].setText(new Integer(tempInventory.get(i).getCount()).toString());
					if (tempInventory.get(i).getCount() < 1) {
						tempInventory.remove(i);
						craftingLabels[i].setIcon(null);
						craftingLabels[i].setText(null);
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		dragResult = false;
		Component c = this.getComponentAt(mouseX, mouseY);
		ItemLabel label;
		Inventory targetInventory;
		// if the component released on is a box,
		// set label to that box.
		if (c instanceof ItemLabel) {
			label = (ItemLabel) c;
			if (label.isCrafting()) {
				targetInventory = tempInventory;
			} else {
				targetInventory = inventory;
			}
			// if the component is not an empty box
			if (targetInventory.get(label.getIndex()) != null) {
				// if the component does not hold the same item type.
				if (!sameItem(label, tempItem, targetInventory)) {
					if (dragSource instanceof ItemLabel) {
						label = (ItemLabel) dragSource;
						if (label.isCrafting()) {
							targetInventory = tempInventory;
						} else {
							targetInventory = inventory;
						}
					}
				}
			}
		} else { // if the component is null or not a box, set label to the source of the drag.
			if (dragSource instanceof ItemLabel) {
				label = (ItemLabel) dragSource;
				if (label.isCrafting()) {
					targetInventory = tempInventory;
				} else {
					targetInventory = inventory;
				}
			} else {
				label = null;
				targetInventory = null;
			}
		}

		if (tempItem != null) {
			if (label == null) {
				result.setItem(tempItem);
				tempItem = null;
				// repaint();
				return;
			} else {
				int i = label.getIndex();

				// if the drop target is the same item type as the tempItem,
				// just change the count of the tempItem.
				if (targetInventory.get(i) != null && sameItem(label, tempItem, targetInventory)) {
					targetInventory.get(i).changeCount(tempItem.getCount());
					label.setText(new Integer(targetInventory.get(i).getCount()).toString());
					Recipe recipeResult = recipe(tempInventory);
					if (recipeResult != null) {
						result.setIcon(new ImageIcon(
								sprites[recipeResult.getResult().getType()][recipeResult.getResult().getId()]));
						result.setText(new Integer(recipeResult.getResult().getCount()).toString());
						result.setVerticalTextPosition(JLabel.BOTTOM);
						result.setForeground(Color.WHITE);
						result.setIconTextGap(-10);
						result.setItem(recipeResult.getResult().getType(), recipeResult.getResult().getId());
					}
					tempItem = null;
					// repaint();
					return;
				}
				targetInventory.add(tempItem, i);
				label.setIcon(new ImageIcon(sprites[tempItem.getType()][tempItem.getId()]));
				label.setText(new Integer(targetInventory.get(i).getCount()).toString());
				label.setVerticalTextPosition(JLabel.BOTTOM);
				label.setForeground(Color.WHITE);
				label.setIconTextGap(-10);
			}
			Recipe recipeResult = recipe(tempInventory);
			if (recipeResult != null) {
				result.setIcon(
						new ImageIcon(sprites[recipeResult.getResult().getType()][recipeResult.getResult().getId()]));
				result.setText(new Integer(recipeResult.getResult().getCount()).toString());
				result.setVerticalTextPosition(JLabel.BOTTOM);
				result.setForeground(Color.WHITE);
				result.setIconTextGap(-10);
				result.setItem(recipeResult.getResult().getType(), recipeResult.getResult().getId());
			} else {
				result.setIcon(null);
				result.setText(null);
			}
			tempItem = null;
			// repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
		setBackground(new Color(39, 52, 130, 170));
		if (tempItem != null) {
			g.drawImage(sprites[tempItem.getType()][tempItem.getId()], mouseX, mouseY, SPRITE_SIZE, SPRITE_SIZE, null);
			g.setColor(Color.WHITE);
			g.drawString(new Integer(tempItem.getCount()).toString(), mouseX + 24, mouseY + 30);
		}
	}

	public boolean sameItem(ItemLabel label, Item i, Inventory inv) {
		return inv.get(label.getIndex()) != null && i != null
				&& inv.get(label.getIndex()).getType() == tempItem.getType()
				&& inv.get(label.getIndex()).getId() == tempItem.getId();
	}

	public Recipe recipe(Inventory i) {
		Recipe rtn = null;
		for (Recipe r : recipes) {
			if (r != null && r.isEqual(i)) {
				rtn = r;
			}
		}
		return rtn;
	}
}
