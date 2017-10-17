package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Particle extends WorldObject{
	private int life;
	private BufferedImage img;
	public Particle(double xx, double yy, double xxx, double yyy, int l){
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
	
	public Particle(double xx, double yy, double xxx, double yyy, int l, BufferedImage i){
		super(xx, yy);
		width = 16;
		height = 16;
		xsp = xxx;
		ysp = yyy;
		life = l;
		img = i;
	}
	
	public void step(Game g){
		if(life<1 || width<=0){
			destroy();
		}
		life--;
		width-=0.5;
		height-=0.5;
		super.step(g);
	}
	
	public BufferedImage getSprite(){
		return img;
	}

}
