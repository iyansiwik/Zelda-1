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
	
	public int tick(Game game, boolean[] keys) {
		dy = 0;
		dx = 0;
		if(keys[1]) { //RIGHT
			if(getX() >= (screen.getWidth()*SIZE)-16) {
				game.goRight();
				return 1;
			}
			direction = RIGHT;
			dx = 1;
		}
		if(keys[3]) { //LEFT
			if(getX() <= 0) {
				game.goLeft();
				return 1;
			}
			direction = LEFT;
			dx = -1;
		}
		if(keys[0]) { //UP
			if(getY() <= 56) {
				game.goUp();
				return 1;
			}
			direction = UP;
			dy = -1;
		}
		if(keys[2]) { //DOWN
			if(getY() >= (screen.getHeight()*SIZE)+56-16) {
				game.goDown();
				return 1;
			}
			direction = DOWN;
			dy = 1;
		}
		
		

		if((dx != 0 || dy != 0) && System.currentTimeMillis()-lastFrameChange >= ANIMATION_SPEED) {
			frame = (frame+1) % 2;
			lastFrameChange = System.currentTimeMillis();
		}
		
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
		if(yy != 0) {
			x = (int) (Math.round(x / 4.0) * 4);
		}
		y += yy;
		
		int xx = dx*speed;
		if(yy == 0) {
			if(xx != 0) {
				y = (int) (Math.round(y / 4.0) * 4);
			}
			if(direction == UP || direction == DOWN) {
				if(dx == -1) direction = LEFT;
				if(dx == 1) direction = RIGHT;
			}
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
		} else {
			xx = 0;
		}
		
		if(xx == 0) dx = 0;
		if(yy == 0) dy = 0;
		
		return 0;
	}
	
	public void render(Graphics g, float interpolation) {
		int modifier = 1;
		if(x+(dx*speed*interpolation*modifier) >= 16*screen.getWidth()) modifier = 0;
		if(x+(dx*speed*interpolation*modifier) < 0) modifier = 0;
		if(y+(dy*speed*interpolation*modifier) >= 16*screen.getHeight()+56) modifier = 0;
		if(y+(dy*speed*interpolation*modifier) < 56) modifier = 0;
		if(state == 0) {
			switch(direction) {
			case UP:
				if(frame == 0) g.drawImage(move[2], (int)(x+(dx*speed*interpolation*modifier)), (int)(y+(dy*speed*interpolation*modifier)), null);
				if(frame == 1) g.drawImage(move[3], (int)(x+(dx*speed*interpolation*modifier)), (int)(y+(dy*speed*interpolation*modifier)), null);
				break;
			case RIGHT:
				if(frame == 0) g.drawImage(move[4], (int)(x+(dx*speed*interpolation*modifier)), (int)(y+(dy*speed*interpolation*modifier)), null);
				if(frame == 1) g.drawImage(move[5], (int)(x+(dx*speed*interpolation*modifier)), (int)(y+(dy*speed*interpolation*modifier)), null);
				break;
			case DOWN:
				if(frame == 0) g.drawImage(move[0], (int)(x+(dx*speed*interpolation*modifier)), (int)(y+(dy*speed*interpolation*modifier)), null);
				if(frame == 1) g.drawImage(move[1], (int)(x+(dx*speed*interpolation*modifier)), (int)(y+(dy*speed*interpolation*modifier)), null);
				break;
			case LEFT:
				if(frame == 0) g.drawImage(move[6], (int)(x+(dx*speed*interpolation*modifier)), (int)(y+(dy*speed*interpolation*modifier)), null);
				if(frame == 1) g.drawImage(move[7], (int)(x+(dx*speed*interpolation*modifier)), (int)(y+(dy*speed*interpolation*modifier)), null);
			}
		}
	}
}
