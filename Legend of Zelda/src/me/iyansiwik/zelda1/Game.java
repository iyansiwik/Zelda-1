package me.iyansiwik.zelda1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public abstract class Game extends Canvas implements KeyListener, WindowListener {
	
	private boolean up;
	private boolean right;
	private boolean down;
	private boolean left;
	private boolean A;
	private boolean B;
	private boolean Start;
	private boolean Select;
	
	private final int WIDTH = 256;
	private final int HEIGHT = 224;
	private final double SCALE;

	private final int TICKS_PER_SECOND = 20;
	private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	
	private BufferedImage image;
	private JFrame frame;
	
	private Link link;
	
	private Map map;
	private Screen screen;
	private int screen_x;
	private int screen_y;
	
	private boolean running = false;
	
	public Game(double scale) {
		SCALE = scale;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		frame = new JFrame("Legend of Zelda, The - Java Edition");
		try {
			frame.setIconImage(ImageIO.read(new File("res/icon.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.addWindowListener(this);
		setSize((int)(WIDTH*SCALE), (int)(HEIGHT*SCALE));
		addKeyListener(this);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		requestFocus();
		
		start();
	}
	
	public void start() {
		if(running) return;
		running = true;
		run();
	}
	
	public void stop() {
		running = false;
	}
	
	private void run() {
		link = new Link(120, 136);
		
		init();
		
		long next_tick = System.currentTimeMillis();
		int loops;
		while(running) {
			loops = 0;
			while(System.currentTimeMillis() > next_tick && loops < MAX_FRAMESKIP) {
				tick();
				
				next_tick += SKIP_TICKS;
				loops++;
			}
			
			float interpolation = (float)(System.currentTimeMillis() + SKIP_TICKS - next_tick) / (float)SKIP_TICKS;
			render(interpolation);
		}
	}
	
	private void tick() {
		if(screen != null) screen.tick(this, new boolean[] {up, right, down, left, A, B, Start, Select});
	}
	
	private void render(float interpolation) {
//		interpolation = 0;
		Graphics g = image.getGraphics();
		g.setColor(new Color(0x000000));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if(screen != null) screen.render(g, interpolation, 0, 56);
		g.dispose();
		g = getGraphics();
		g.drawImage(image, 0, 0, (int)(WIDTH*SCALE), (int)(HEIGHT*SCALE), null);
		g.dispose();
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public void goUp() {
		link.setLocation(link.getX(), (screen.getHeight()*16)-16+56);
		System.out.println(link.getY());
		setScreen(screen_x, screen_y-1);
	}
	
	public void goRight() {
		link.setLocation(0, link.getY());
		setScreen(screen_x+1, screen_y);
	}
	
	public void goDown() {
		link.setLocation(link.getX(), 56);
		setScreen(screen_x, screen_y+1);
	}
	
	public void goLeft() {
		link.setLocation((screen.getWidth()*16)-16, link.getY());
		setScreen(screen_x-1, screen_y);
	}
	
	public void setScreen(int x, int y) {
		System.out.println("Setting Screen");
		if(x < 0 || y < 0 || x >= map.getWidth() || y >= map.getHeight()) return;
		screen_x = x;
		screen_y = y;
		Screen screen = map.getScreen(x, y);
		boolean change_audio = true;
		if(this.screen != null) {
			this.screen.removeEntity(link);
			if(this.screen.getAudio() == screen.getAudio()) change_audio = false;
			else this.screen.stopAudio();
		}
		screen.addEntity(link);
		if(change_audio) screen.playAudio();
		this.screen = screen;
	}
	
	public abstract void init();
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void keyPressed(KeyEvent e) {
		switch(e.getExtendedKeyCode()) {
		case KeyEvent.VK_UP:
			down = false;
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			up = false;
			down = true;
			break;
		case KeyEvent.VK_LEFT:
			right = false;
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			left = false;
			right = true;
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch(e.getExtendedKeyCode()) {
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		}
	}
	public void keyTyped(KeyEvent e) {}
}
