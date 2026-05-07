package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Player;

public class Main extends JPanel implements KeyListener, Runnable {

	//screen settings
		final int ORIGINAL_TILE_SIZE = 16;
		final int TILE_SCALE = 3;
		public final int TILE_SIZE = ORIGINAL_TILE_SIZE * TILE_SCALE; //48 X 48 tile
		
		public final int MAX_COLUMN = 16;
		public final int MAX_ROW = 12;
		
		final int WIDTH = TILE_SIZE * MAX_COLUMN;	//768 pixels
		final int HEIGHT = TILE_SIZE * MAX_ROW; 	//576 pixels
		
		final int FPS = 60;
		
		public boolean up, down, left, right;
		
		Thread gameThread;
		KeyHandler keyH = new KeyHandler();
		Player player = new Player(this, keyH);
	
	public static void main(String[] args) {
		
		//set window
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Crazy 2D Rider");
		
		Main gamePanel = new Main();	//make what appears on window
		window.add(gamePanel);					//put it on window
		window.pack();
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		
		gamePanel.startGameThread();
	}//main
	
	public Main() {
			
			this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			this.setBackground(Color.GRAY);
			this.setDoubleBuffered(true);
			this.addKeyListener(keyH);
			this.setFocusable(true);
		}
	
	public void startGameThread() {
			
			gameThread = new Thread(this);
			gameThread.start();
			
		}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double drawInterval = 1000000000/FPS;
		double timer = 0;
		int drawCount = 0;

		//game loop
		while(gameThread != null) {
			//set FPS
			if(System.nanoTime() - lastTime < drawInterval) {
				continue;
			}
			
			//update info
			update();
			
			//draw
			repaint();

			timer += System.nanoTime() - lastTime;
			lastTime = System.nanoTime();
			drawCount++;
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}//while
	}//run
	
	public void update() {
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;

		player.draw(g2);
		
		g2.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}//class
