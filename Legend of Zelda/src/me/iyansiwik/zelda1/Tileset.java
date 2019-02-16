package me.iyansiwik.zelda1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tileset {

	private BufferedImage image;
	
	private int tileSize;
	
	public Tileset(String imagePath, int tileSize) {
		try {
			image = ImageIO.read(new File("res/"+imagePath+".png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		this.tileSize = tileSize;
	}
	
	public BufferedImage getTile(int x, int y) {
		return image.getSubimage(x*tileSize, y*tileSize, tileSize, tileSize);
	}
}
