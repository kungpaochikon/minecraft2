package main;

import javax.swing.JFrame;

public class Launcher {
	public static void main(String[] args) {
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
