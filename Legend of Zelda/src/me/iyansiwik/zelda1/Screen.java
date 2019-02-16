package me.iyansiwik.zelda1;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Screen {

	private Tile[][] tiles;
	private List<Entity> entities;
	
	private Audio audio;
	
	public Screen(Audio audio) {
		tiles = new Tile[16][11];
		entities = new ArrayList<Entity>();
		
		this.audio = audio;
	}
	
	public Screen() {
		this(null);
	}
	
	public void setTile(Tile tile, int x, int y) {
		tiles[x][y] = tile;
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void addEntity(Entity entity) {
		entity.setScreen(this);
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entity.setScreen(null);
		entities.remove(entity);
	}
	
	public boolean isCollidingWithTile(Rectangle r1) {
		for(int i=0;i<tiles.length;i++) {
			for(int j=0;j<tiles[i].length;j++) {
				int size = tiles[i][j].getSize();
				Rectangle r2I = new Rectangle(i*size + size/2, j*size+56, size/2, size/2); //TODO un-hard code 56 in (y_offset)
				Rectangle r2II = new Rectangle(i*size, j*size + 56, size/2, size/2);
				Rectangle r2III = new Rectangle(i*size, j*size + size/2 + 56, size/2, size/2);
				Rectangle r2IV = new Rectangle(i*size + size/2, j*size + size/2 + 56, size/2, size/2);
				boolean[] w = tiles[i][j].getWalkthroughable();
				if(!w[0] && r1.intersects(r2I)) return true;
				if(!w[1] && r1.intersects(r2II)) return true;
				if(!w[2] && r1.intersects(r2III)) return true;
				if(!w[3] && r1.intersects(r2IV)) return true;
			}
		}
		return false;
	}
	
	public void tick(boolean[] keys) {
		for(Entity entity : entities) {
			entity.tick(keys);
		}
	}
	
	public void render(Graphics g, float interpolation, int x_offset, int y_offset) {
		for(int i=0;i<tiles.length;i++) {
			for(int j=0;j<tiles[i].length;j++) {
				tiles[i][j].render(g, i*tiles[i][j].getSize()+x_offset, j*tiles[i][j].getSize()+y_offset);
			}
		}
		for(Entity entity : entities) {
			entity.render(g, interpolation);
		}
	}
	
	public void playAudio() {
		if(audio != null) audio.play();
	}
	
	public void stopAudio() {
		if(audio != null) audio.stop();
	}
	
	public int getWidth() {
		return 16;
	}
	
	public int getHeight() {
		return 11;
	}
}
