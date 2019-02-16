package me.iyansiwik.zelda1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage image;
	
	private boolean walkthrough_ul;
	private boolean walkthrough_ur;
	private boolean walkthrough_dl;
	private boolean walkthrough_dr;
	
	public Tile(Tileset tileset, int x, int y, boolean walkthrough_ul, boolean walkthrough_ur, boolean walkthrough_dl, boolean walkthrough_dr) {
		image = tileset.getTile(x, y);
		
		this.walkthrough_ul = walkthrough_ul;
		this.walkthrough_ur = walkthrough_ur;
		this.walkthrough_dl = walkthrough_dl;
		this.walkthrough_dr = walkthrough_dr;
	}
	
	public Tile(Tileset tileset, int x, int y, boolean walkthrough) {
		this(tileset, x, y, walkthrough, walkthrough, walkthrough, walkthrough);
	}
	
	public Tile(Tileset tileset, int x, int y) {
		this(tileset, x, y, true);
	}
	
	public void render(Graphics g, int x_offset, int y_offset) {
		g.drawImage(image, x_offset, y_offset, null);
	}
	
	public int getSize() {
		return image.getWidth();
	}
}
