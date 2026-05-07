package main;

import javax.swing.*;
import entity.Tile;
import entity.Player;
import java.awt.*;
import java.awt.event.*;


public class Main extends JPanel implements KeyListener {
    // ===== WINDOW SETTINGS =====

    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    
    Timer gameTimer;

    // ===== GAME STATES =====

    final int WELCOME = 0;
    final int PLAYING = 1;
    final int WIN = 2;
    
    int gameState = WELCOME;
  
    // ===== PLAYER VARIABLES =====
    
    Player [] player;
    Image player1Pic;
    Image player2Pic;
    
    
    //background tiles
    Tile[][] tiles;

    // ===== MAIN METHOD =====

    public static void main(String[] args) {
        JFrame window = new JFrame("Colour Smash");

        Main gamePanel = new Main();

        window.add(gamePanel);
        window.setSize(WIDTH, HEIGHT);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        window.addKeyListener(gamePanel);
    } // main

    // ===== CONSTRUCTOR =====

    public Main() {
        setBackground(Color.WHITE);
        
        //initialize player1
        player = new Player[2];
        
        for(int i = 0;i < player.length; i++) {
        	player[i] = new Player();
        }
        
        player[0].color = Color.RED;
        player[1].color = Color.BLUE;
        
        //load image
        Toolkit tk = Toolkit.getDefaultToolkit();
        player1Pic = tk.getImage("redPaintBrush.png");
        player2Pic = tk.getImage("bluePaintBrush.png");
        
        
        //initialize tiles
        tiles = new Tile[400][300];
        for(int i = 0; i < tiles.length; i++) {
        	for(int j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = new Tile();
				tiles[i][j].setColour(Color.WHITE);
				tiles[i][j].setX(i * (Tile.WIDTH));
				tiles[i][j].setY(j * (Tile.HEIGHT));	
        	}//for
        }//for
        
        // Game loop runs every 50 ms (~20 FPS)
        gameTimer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               gameLoop();
            }
        });

        gameTimer.start(); // start loop
       
    }
    
    public void gameLoop() {
        if (gameState == PLAYING) {
        	for(int i = 0; i < player.length; i++) {
        		if (player[i].up && !player[i].down){
    			    player[i].movePlayerUp();   
    			} else if (player[i].up && !player[i].down){
    			    player[i].movePlayerUp();   
    			}
    			
        		if (player[i].up && !player[i].down){
    			    player[i].movePlayerUp();   
    			}
        		
        		colourTile(player[i], tiles);
        	}//for
        keepPlayerOnScreen();
        }//if

        repaint(); // redraw screen every frame
    } // gameLoop

    // ===== DRAW SCREEN =====

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        // ===== WELCOME SCREEN =====
        if (gameState == WELCOME) {
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.setColor(Color.blue);
            g.drawString("Colour", 230, 220);
            g.setColor(Color.red);
            g.drawString("Smash", 395, 220);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.setColor(Color.black);
            g.drawString("Timed Mode - Press 1 to Play", 235, 300);
            g.drawString("2v2 Battle - Press 2 to Play", 250, 350);
            g.drawString("Sumo Battle - Press 3 to Play", 235, 400);
        }

        // ===== PLAY SCREEN =====
        else if (gameState == PLAYING) {
        	
        	//draw tiles
        	for(int i = 0; i < tiles.length; i++) {
            	for(int j = 0; j < tiles[i].length; j++) {
                	g.setColor(tiles[i][j].getColour());
            		g.fillRect(tiles[i][j].x, tiles[i][j].y, Tile.WIDTH, Tile.HEIGHT);	
            	}
            }
        	
            // Draw player
            g.drawImage(player1Pic, player[0].x, player[0].y, player[0].size, player[0].size, this);
            g.drawImage(player2Pic, player[1].x, player[1].y, player[1].size, player[1].size, this);
        }

        // ===== WIN SCREEN =====
        else if (gameState == WIN) {
            showResult(g);
        } // win screen
    } // paintComponent

    // ===== KEY PRESS =====

    public void keyPressed(KeyEvent e) {
        // SPACE starts or restarts game
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            startGame();
        }

        // Only move during gameplay
        if (gameState == PLAYING) {
			if (e.getKeyCode() == KeyEvent.VK_UP){
			    player[0].up = true;      
			}
			
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				player[0].down = true;          
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player[0].left = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player[0].right = true; 
			}
			
			if (e.getKeyCode() == KeyEvent.VK_W){
				player[1].up = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_S) {
				player[1].down = true;
			}
			 
			if (e.getKeyCode() == KeyEvent.VK_A) {
				player[1].left = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_D) {
				player[1].right = true;
			}

        } // if
    } // keyPressed

    public void keyReleased(KeyEvent e){
    	if (gameState == PLAYING) {
			if (e.getKeyCode() == KeyEvent.VK_UP){
			    player[0].up = false;      
			}
			
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				player[0].down = false;          
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				player[0].left = false;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player[0].right = false; 
			}
			
			if (e.getKeyCode() == KeyEvent.VK_W){
				player[1].up = false;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_S) {
				player[1].down = false;
			}
			 
			if (e.getKeyCode() == KeyEvent.VK_A) {
				player[1].left = false;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_D) {
				player[1].right = false;
			}
        } // if
    } // keyPressed
    	
    	
    }

    public void keyTyped(KeyEvent e){}

    // ===== GAME LOGIC =====

    public void startGame() {

        player[0].x = 100;
        player[0].y = 100;
        
        player[1].x = 100;
        player[1].y = 100;

    	gameState = PLAYING;
    	repaint();
    	
    } // startGame


    // ===== CHECK COLLISION =====

    	public void colourTile(Player player, Tile[][] tiles) {
    	for(int i = player.x; i < player.x + player.size;i++) {
    		for(int j = player.y; j < player.y + player.size; j++) {
	    	
	    		tiles[i / 2][j / 2].setColour(player.color);
	    	}//if
    	}
    	
    } // colourTile

    // ===== KEEP PLAYER ON SCREEN =====

    public void keepPlayerOnScreen() {
    	for(int i = 0; i < player.length; i++) {
	    	if(player[i].x < 0) {
	    		player[i].x = 0;
	    	}
	    	
	    	if(player[i].x > WIDTH - (20 + player[i].size)) {
	    		player[i].x = WIDTH - (20 + player[i].size);
	    	}
	    	
	    	if(player[i].y < 0) {
	    		player[i].y = 0;
	    	}
	    	
	    	if(player[i].y > HEIGHT - 2*player[i].size) {
	    		player[i].y = HEIGHT - 2*player[i].size;
	    	}
    	}
    } // keepPlayerOnScreen
    
    public void showResult(Graphics g) {
      //  super.paintComponent(g);
    	
    	int countRed = 0;
    	int countBlue = 0;
    	int totalColouredTiles = 0;
    	double percentRed = 0.0;
    	double percentBlue = 0.0;
    	
    	for(int i = 0; i < tiles.length; i++) {
        	for(int j = 0; j < tiles[i].length; j++) {
            	if(tiles[i][j].getColour().equals(Color.RED)) {
            		countRed++;
                	totalColouredTiles++;
            	} else if(tiles[i][j].getColour().equals(Color.BLUE)) {
            		countBlue++;
                	totalColouredTiles++;
            	}//if
        	}//for
        }//for
    	
    	percentRed = Math.round(1000.0 * countRed / totalColouredTiles) / 10.0;
    	percentBlue = Math.round(1000.0 * countBlue / totalColouredTiles) / 10.0;
    	
    	g.setFont(new Font("Arial", Font.BOLD, 36));
        g.setColor(Color.red);
        g.drawString(percentRed + "%", 100, 150);
        g.setColor(Color.BLUE);
        g.drawString(percentBlue + "%", 250, 150);

        g.drawString("YOU WIN!", 290, 240);
    	
    }//calculateResult
} // class