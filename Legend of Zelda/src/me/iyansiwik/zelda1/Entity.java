package me.iyansiwik.zelda1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Entity {
	
	public static final int UP = 0;
	public static final int RIGHT = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	
	protected Screen screen;
	
	protected int speed = 4;
	
	protected BufferedImage strip;

	protected int x, y;
	protected int direction;
		
	public Entity(String imagePath, int x, int y) {
		try {
			strip = ImageIO.read(new File("res/"+imagePath+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		direction = DOWN;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public abstract int tick(Game game, boolean[] keys);
	public abstract void render(Graphics g, float interpolation);
}
