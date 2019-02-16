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

	private final int WIDTH = 256;
	private final int HEIGHT = 224;
	private final double SCALE;

	private BufferedImage image;
	private JFrame frame;
	
	private Link link;
	
	private Screen screen;
	
	private boolean running = false;
	
	public Game(double scale) {
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
		while(running) {
			tick();
			render();
		}
	}
	
	private void tick() {
		if(screen != null) screen.tick();
	}
	
	private void render() {
		Graphics g = image.getGraphics();
		g.setColor(new Color(0x000000));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if(screen != null) screen.render(g, 0, 56);
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
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
