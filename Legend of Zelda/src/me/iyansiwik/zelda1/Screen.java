package me.iyansiwik.zelda1;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Screen {

	private Tile[][] tiles;
	private List<Entity> entities;
	
	public Screen() {
		tiles = new Tile[16][11];
		entities = new ArrayList<Entity>();
	}
	
	public void setTile(Tile tile, int x, int y) {
		tiles[x][y] = tile;
	}

	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
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
	
	public int getWidth() {
		return 16;
	}
	
	public int getHeight() {
		return 11;
	}
}
