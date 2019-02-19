package me.iyansiwik.zelda1;

public abstract class Map {

	private Screen[][] screens;
	
	public Map(Screen[][] screens) {
		this.screens = screens;
	}
	
	public Screen getScreen(int x, int y) {
		return screens[x][y];
	}
	
	public int getWidth() {
		return screens.length;
	}
	
	public int getHeight() {
		return screens[0].length;
	}
}
