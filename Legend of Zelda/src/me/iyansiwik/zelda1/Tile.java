package me.iyansiwik.zelda1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	
	private boolean[] walkthrough;
	
	public Tile(Tileset tileset, int x, int y, boolean walk_I, boolean walk_II, boolean walk_III, boolean walk_IV) {
		image = tileset.getTile(x, y);
		
		walkthrough = new boolean[4];
		walkthrough[0] = walk_I;
		walkthrough[1] = walk_II;
		walkthrough[2] = walk_III;
		walkthrough[3] = walk_IV;
	}
	
	public Tile(Tileset tileset, int x, int y, boolean walk) {
		this(tileset, x, y, walk, walk, walk, walk);
	}
	
	public Tile(Tileset tileset, int x, int y) {
		this(tileset, x, y, true);
	}
	
	public boolean[] getWalkthroughable() {
		return walkthrough;
	}
	
	public void render(Graphics g, int x_offset, int y_offset) {
		g.drawImage(image, x_offset, y_offset, null);
	}
	
	public int getSize() {
		return image.getWidth();
	}
}
