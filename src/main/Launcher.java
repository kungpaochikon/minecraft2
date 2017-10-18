package main;

import javax.swing.JFrame;

/**
 * @author gary, logun, alex
 *
 */
public class Launcher {
	/**
	 * @param args game launger arguments
	 */
	public static void main(final String[] args) {
		JFrame obj = new JFrame();
		Game game = new Game();
		obj.setBounds(0, 0, 1280, 720);
		obj.setTitle("Minecraft 2");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setLocationRelativeTo(null);
		obj.add(game);
	}
}
