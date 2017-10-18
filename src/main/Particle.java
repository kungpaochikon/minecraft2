package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author gary, logun, alex
 *
 */
public class Particle extends WorldObject {
	/**
	 * The life span of the particle.
	 */
	private int life;
	/**
	 * The image of the particle.
	 */
	private BufferedImage img;
	/**
	 * The constructor sets x, y, change in x, change in y, and lifespan.
	 * @param xx particle x
	 * @param yy particle y
	 * @param xxx particle xsp
	 * @param yyy particle ysp
	 * @param l life
	 */
	public Particle(final double xx, final double yy, final double xxx, final double yyy, final int l) {
		super(xx, yy);
		width = 16;
		height = 16;
		xsp = xxx;
		ysp = yyy;
		life = l;
		try {
			img = ImageIO.read(new File("images\\spr_square.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * The constructor sets x, y, change in x, change in y,
	 * lifespan and sprite.
	 * @param xx particle x
	 * @param yy particle y
	 * @param xxx particle xsp
	 * @param yyy particle ysp
	 * @param l life
	 * @param i img
	 */
	public Particle(final double xx, final double yy, final double xxx, final double yyy, final int l, final BufferedImage i) {
		super(xx, yy);
		width = 16;
		height = 16;
		xsp = xxx;
		ysp = yyy;
		life = l;
		img = i;
	}
	
	/**
	 * @see main.WorldObject#step(main.Game)
	 * 
	 * @param g game step
	 */
	public void step(final Game g) {
		if (life < 1 || width <= 0) {
			destroy();
		}
		life--;
		width -= 0.5;
		height -= 0.5;
		super.step(g);
	}
	
	/**
	 * Gets the sprite of the particle.
	 * @return the particle sprite.
	 */
	public BufferedImage getSprite() {
		return img;
	}

}
