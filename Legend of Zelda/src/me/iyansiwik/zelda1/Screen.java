package me.iyansiwik.zelda1;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	public void load(String filePath, Tile[] tiles) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("res/"+filePath+".screen"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<String> lines = new ArrayList<String>();
		while(scanner.hasNextLine()) {
			lines.add(scanner.nextLine());
		}
		
		for(int i=0;i<this.tiles.length;i++) {
			for(int j=0;j<this.tiles[i].length;j++) {
				Tile tile;
				char c = '.';
				if(lines.get(j).length() > i) c = lines.get(j).charAt(i);
				switch(c) {
				default:
				case '0':
					tile = tiles[0];
					break;
				case '1':
					tile = tiles[1];
					break;
				case '2':
					tile = tiles[2];
					break;
				case '3':
					tile = tiles[3];
					break;
				case '4':
					tile = tiles[4];
					break;
				case '5':
					tile = tiles[5];
					break;
				case '6':
					tile = tiles[6];
					break;
				case '7':
					tile = tiles[7];
					break;
				case '8':
					tile = tiles[8];
					break;
				case '9':
					tile = tiles[9];
					break;
				case 'A':
					tile = tiles[10];
					break;
				case 'B':
					tile = tiles[11];
					break;
				case 'C':
					tile = tiles[12];
					break;
				case 'D':
					tile = tiles[13];
					break;
				case 'E':
					tile = tiles[14];
					break;
				case 'F':
					tile = tiles[15];
					break;
				case 'G':
					tile = tiles[16];
					break;
				case 'H':
					tile = tiles[17];
					break;
				case 'I':
					tile = tiles[18];
					break;
				case 'J':
					tile = tiles[19];
					break;
				}
				this.tiles[i][j] = tile;
			}
		}
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
