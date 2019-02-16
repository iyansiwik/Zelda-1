package me.iyansiwik.zelda1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public abstract class Game extends Canvas implements KeyListener, WindowListener {
	
	private boolean[] keys;

	private final int WIDTH = 256;
	private final int HEIGHT = 224;
	private final double SCALE;

	private final int TICKS_PER_SECOND = 25;
	private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	private final int MAX_FRAMESKIP = 5;
	
	private BufferedImage image;
	private JFrame frame;
	
	private Link link;
	
	private Screen screen;
	
	private boolean running = false;
	
	public Game(double scale) {
		keys = new boolean[256];
		
		SCALE = scale;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		frame = new JFrame("Legend of Zelda, The - Java Edition");
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
		link = new Link();
		
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
		if(screen != null) screen.tick(keys);
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
	
	public void setScreen(Screen screen) {
		if(this.screen != null) this.screen.removeEntity(link);
		screen.addEntity(link);
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
		keys[e.getExtendedKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {
		keys[e.getExtendedKeyCode()] = false;
	}
	public void keyTyped(KeyEvent e) {}
}
