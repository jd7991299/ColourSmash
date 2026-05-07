package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player {

	KeyHandler keyH;
	Main gp;
	
	public int x, y;
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter = 0;
	public boolean spriteCheck = true;
	
	public Player(Main gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		setDefault();
		getPlayerImage();
		
	}
	
	public void setDefault() {
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}
	
	public void getPlayerImage() {
		
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/dao_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/dao_up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/dao_down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/dao_down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/dao_left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/dao_left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/dao_right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/dao_right2.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		if(keyH.up || keyH.down || keyH.left || keyH.right) {
			if(keyH.up == true) {
				direction = "up";
				y -= speed;
			}
			else if(keyH.down == true) {
				direction = "down";
				y += speed;
			}
			else if(keyH.left == true) {
				direction = "left";
				x -= speed;
			}
			else if(keyH.right == true) {
				direction = "right";
				x += speed;
			}
			
				spriteCounter++;
			if(spriteCounter > 9) {
				spriteCheck = !spriteCheck;
				spriteCounter = 0;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteCheck)
				image = up1;
			else
				image = up2;
			break;
		case "down":
			if(spriteCheck)
				image = down1;
			else
				image = down2;
			break;
		case "left":
			if(spriteCheck)
				image = left1;
			else
				image = left2;
			break;
		case "right":
			if(spriteCheck)
				image = right1;
			else
				image = right2;
		}
		
		g2.drawImage(image, x, y, gp.TILE_SIZE, gp.TILE_SIZE, null);
		
	}
	
	
}
