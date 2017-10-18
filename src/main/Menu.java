package main;

//import java.awt.BorderLayout;
import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
//import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The main menu of Minecraft 2.
 * @author gary, logun, alex
 *
 */
public class Menu extends JPanel {
	/**
	 * The icon for the buttons.
	 */
	private BufferedImage buttonIcon;
	/**
	 * The scroll over icon for the buttons.
	 */
	private BufferedImage buttonHover;
	/**
	 * The pressed icon for the buttons.
	 */
	private BufferedImage buttonPressed;
	/**
	 * The background image.
	 */
	private BufferedImage bg;
	/**
	 * The title.
	 */
	private BufferedImage title;
	
	/**
	 * The new game button.
	 */
	private JButton newGame;
	/**
	 * The load game button.
	 */
	private JButton loadGame;
	
	/**
	 * The constructor adds the buttons to the panel and
	 * changes their icons. It finally adds the buttons to
	 * the action listener.
	 * @param al action listener for the buttons.
	 */
	public Menu(final ActionListener al) {
		try {
			buttonIcon = ImageIO.read(new File("images\\button.png"));
			buttonHover = ImageIO.read(new File("images\\buttonhover.png"));
			buttonPressed = ImageIO.read(new File("images\\buttonclick.png"));
			bg = ImageIO.read(new File("images\\menubackground.png"));
			title = ImageIO.read(new File("images\\title.png"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading image assests...");
		}
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints position = new GridBagConstraints();
		
		newGame = new JButton("New Game");
		loadGame = new JButton("Load Game");
		GraphicsEnvironment ge;
		try {
			ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("images\\Minecrafter.Reg.ttf")));
		} catch (FontFormatException | IOException e) {
			JOptionPane.showMessageDialog(this, "Error loading fonts...");
		}
		
		newGame.setBorder(BorderFactory.createEmptyBorder());
		newGame.setContentAreaFilled(false);
		
		newGame.setIcon(new ImageIcon(buttonIcon));
		newGame.setRolloverIcon(new ImageIcon(buttonHover));
		newGame.setPressedIcon(new ImageIcon(buttonPressed));
		newGame.setHorizontalTextPosition(JButton.CENTER);
		newGame.setVerticalTextPosition(JButton.CENTER);
		newGame.setFont(new Font("Minecrafter", Font.TRUETYPE_FONT, 50));
		
		loadGame.setBorder(BorderFactory.createEmptyBorder());
		loadGame.setContentAreaFilled(false);
		
		loadGame.setIcon(new ImageIcon(buttonIcon));
		loadGame.setRolloverIcon(new ImageIcon(buttonHover));
		loadGame.setPressedIcon(new ImageIcon(buttonPressed));
		loadGame.setHorizontalTextPosition(JButton.CENTER);
		loadGame.setVerticalTextPosition(JButton.CENTER);
		loadGame.setFont(new Font("Minecrafter", Font.TRUETYPE_FONT, 50));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(0, 0, 0, 0));
		buttonPanel.setLayout(new GridBagLayout());
		position.gridx = 0;
		position.gridy = 0;
		position.insets = new Insets(50, 0, 0, 50);
		position.anchor = GridBagConstraints.CENTER;
		
		buttonPanel.add(newGame, position);
		
		position.gridy = 1;
		buttonPanel.add(loadGame, position);
		
		position = new GridBagConstraints();
		position.gridy = 1;
		position.weighty = 3;
		position.gridheight = 3;
		position.insets = new Insets(150, 0, 0, 0);
		this.add(buttonPanel, position);
		
		newGame.addActionListener(al);
		loadGame.addActionListener(al);
	}
	
	/**
	 * @return new game
	 */
	public JButton getNewGameButton() {
		return newGame;
	}
	
	/**
	 * @return load game
	 */
	public JButton getLoadGameButton() {
		return loadGame;
	}
	
	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * @param g graphics component
	 */
	public void paintComponent(final Graphics g) {
		g.drawImage(bg, 0, 0, this);
		g.drawImage(title, 0, 10, this);
	}
}
