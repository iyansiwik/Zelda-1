package me.iyansiwik.zelda1;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Link extends Entity {

	private final int SIZE = 16;
	
	private final int ANIMATION_SPEED = 100;
	
	private BufferedImage[] move;
	
	private int state; //0 = Moving
	
	private int frame;
	
	private int dx;
	private int dy;
	
	public Link(int x, int y) {
		super("link/link_green", x, y);
		move = new BufferedImage[8];
		for(int i=0;i<move.length;i++) {
			move[i] = strip.getSubimage(i*SIZE, 0, SIZE, SIZE);
		}
		state = 0;
		frame = 0;
		
		lastFrameChange = System.currentTimeMillis();
	}
	
	private long lastFrameChange;
	
	public void tick(boolean[] keys) {
		dy = 0;
		dx = 0;
		if(keys[KeyEvent.VK_RIGHT]) {
			direction = RIGHT;
			dy = 0;
			dx = 1;
		}
		if(keys[KeyEvent.VK_LEFT]) {
			direction = LEFT;
			dy = 0;
			dx = -1;
		}
		if(keys[KeyEvent.VK_UP]) {
			direction = UP;
			dy = -1;
			dx = 0;
		}
		if(keys[KeyEvent.VK_DOWN]) {
			direction = DOWN;
			dy = 1;
			dx = 0;
		}

		if((dx != 0 || dy != 0) && System.currentTimeMillis()-lastFrameChange >= ANIMATION_SPEED) {
			frame = (frame+1) % 2;
			lastFrameChange = System.currentTimeMillis();
		}
		
		int xx = dx*speed;
		if(screen != null) {
			if(screen.isCollidingWithTile(new Rectangle(x+xx, y+SIZE/2, SIZE, SIZE/2))) {
				xx = 0;
				while(!screen.isCollidingWithTile(new Rectangle(x+xx, y+SIZE/2, SIZE, SIZE/2))) {
					xx += dx;
				}
				xx -= dx;
			}
		}
		x += xx;
		
		int yy = dy*speed;
		if(screen != null) {
			if(screen.isCollidingWithTile(new Rectangle(x, y+yy+SIZE/2, SIZE, SIZE/2))) {
				yy = 0;
				while(!screen.isCollidingWithTile(new Rectangle(x, y+yy+SIZE/2, SIZE, SIZE/2))) {
					yy += dy;
				}
				yy -= dy;
			}
		}
		y += yy;
		
		if(xx == 0) dx = 0;
		if(yy == 0) dy = 0;
	}
	
	public void render(Graphics g, float interpolation) {
		if(state == 0) {
			switch(direction) {
			case UP:
				if(frame == 0) g.drawImage(move[2], (int)(x+(dx*speed*interpolation)), (int)(y+(dy*speed*interpolation)), null);
				if(frame == 1) g.drawImage(move[3], (int)(x+(dx*speed*interpolation)), (int)(y+(dy*speed*interpolation)), null);
				break;
			case RIGHT:
				if(frame == 0) g.drawImage(move[4], (int)(x+(dx*speed*interpolation)), (int)(y+(dy*speed*interpolation)), null);
				if(frame == 1) g.drawImage(move[5], (int)(x+(dx*speed*interpolation)), (int)(y+(dy*speed*interpolation)), null);
				break;
			case DOWN:
				if(frame == 0) g.drawImage(move[0], (int)(x+(dx*speed*interpolation)), (int)(y+(dy*speed*interpolation)), null);
				if(frame == 1) g.drawImage(move[1], (int)(x+(dx*speed*interpolation)), (int)(y+(dy*speed*interpolation)), null);
				break;
			case LEFT:
				if(frame == 0) g.drawImage(move[6], (int)(x+(dx*speed*interpolation)), (int)(y+(dy*speed*interpolation)), null);
				if(frame == 1) g.drawImage(move[7], (int)(x+(dx*speed*interpolation)), (int)(y+(dy*speed*interpolation)), null);
			}
		}
	}
}
