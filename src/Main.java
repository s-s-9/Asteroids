import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

public class Main extends Applet implements Runnable, KeyListener {
	//game loop
	Thread gameloop;
	
	//double buffer
	BufferedImage backbuffer;
	
	//main drawing object for the back buffer
	Graphics2D g2d;
	
	//show or hide bounding boxes
	boolean showBounds;
	
	//create the ship
	Ship ship = new Ship();
	
	//create the bullets
	final int BULLETS = 10;
	Bullet[] bullets = new Bullet[BULLETS];
	int currentBullet = 0;
	
	//create the asteroids
	final int ASTEROIDS = 20;
	Asteroid [] asteroids = new Asteroid[ASTEROIDS];
	
	//create the identity transform (0, 0)
	AffineTransform identity = new AffineTransform();
	
	//random number generator
	Random rand = new Random();
	
	//this gets called when setVisible(true) is seen
	public void init() {
		//create the back buffer for smooth graphics
		backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		
		//setup the ship
		ship.setx(320);
		ship.sety(240);
		
		//setup bullets
		for(int i = 0; i<BULLETS; i++) {
			bullets[i] = new Bullet();
		}
		
		//setup asteroids
		for(int i = 0; i<ASTEROIDS; i++) {
			asteroids[i] = new Asteroid();
			
			//assign initial values to this asteroid
			asteroids[i].setx((double)rand.nextInt(600) + 20);
			asteroids[i].sety((double)rand.nextInt(440) + 20);
			asteroids[i].setMoveAngle((double)rand.nextInt(360));
			double angle = asteroids[i].getMoveAngle()-90;
			asteroids[i].setvx(calcAngleMoveX(angle));
			asteroids[i].setvy(calcAngleMoveY(angle));
			asteroids[i].setRotateVel((double)rand.nextInt(3) + 1);
		}
		
		//add user event listeners
		this.addKeyListener(this);	
	}
	
	//update event to redraw the screen
	public void update(Graphics g) {
		System.out.println("update called " + Thread.currentThread().getName());
		//start transforms at identity
		g2d.setTransform(identity);
		
		//erase the background
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		
		//draw game components
		this.drawShip();
		this.drawBullets();
		this.drawAsteroids();
		
		//repaint the applet window
		paint(g);
	}
	
	//draw the back buffer
	public void paint(Graphics g) {
		System.out.println("paint " + Thread.currentThread().getName());
		//draw the back buffer onto the applet window
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	//draw components
	public void drawShip() {
		g2d.setTransform(identity);
		g2d.setColor(Color.ORANGE);
		g2d.translate(ship.getx(), ship.gety());
		g2d.rotate(Math.toRadians(ship.getFaceAngle()));
		g2d.fill(ship.getShape());
	}
	public void drawBullets() {
		for(int i = 0; i<BULLETS; i++) {
			if(bullets[i].isAlive()) {
				g2d.setTransform(identity);
				g2d.setColor(Color.MAGENTA);
				g2d.translate(bullets[i].getx(), bullets[i].gety());
				g2d.draw(bullets[i].getShape());
			}
		}
	}
	public void drawAsteroids() {
		for(int i = 0; i<ASTEROIDS; i++) {
			if(asteroids[i].isAlive()) {
				g2d.setTransform(identity);
				g2d.setColor(Color.CYAN);
				g2d.translate(asteroids[i].getx(), asteroids[i].gety());
				g2d.rotate(Math.toRadians(asteroids[i].getMoveAngle()));
				g2d.fill(asteroids[i].getShape());
			}
		}
	}
	
	//calculate x and y movement values based on direction angle
	public double calcAngleMoveX(double angle) {
		return (double)(Math.cos(angle*Math.PI/180));
	}
	public double calcAngleMoveY(double angle) {
		return (double)(Math.sin(angle*Math.PI/180));
	}
	
	//start the game loop
	public void start() {
		gameloop = new Thread(this);
		gameloop.start();
	}
	
	//gameloop
	public void run() {
		//acquire the current thread
		Thread t = Thread.currentThread();
		while(t==gameloop) {
			try {
				this.gameUpdate();
				Thread.sleep(20);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	//update components of the game
	private void gameUpdate() {
		//System.out.println("updategame " + Thread.currentThread().getName());
		updateShip();
		updateBullets();
		updateAsteroids();
		checkCollisions();
	}
	
	//update individual components
	public void updateShip() {
		ship.changex(ship.getvx());
		//bring it back if it goes out of screen
		if(ship.getx()<0) {
			ship.setx(this.getSize().width);
		}
		else if(ship.getx()>this.getSize().width) {
			ship.setx(0);
		}
		
		ship.changey(ship.getvy());
		//bring it back if it goes out of screen
		if(ship.gety()<0) {
			ship.sety(this.getSize().height);
		}
		else if(ship.gety()>this.getSize().height) {
			ship.sety(0);
		}
	}
	public void updateBullets() {
		for(int i = 0; i<BULLETS; i++) {
			if(bullets[i].isAlive()) {
				//adjust x stuffs
				bullets[i].changex(bullets[i].getvx());
				if(bullets[i].getx()<0 || bullets[i].getx()>this.getSize().width) {
					bullets[i].setAlive(false);
				}
				//adjust y stuffs
				bullets[i].changey(bullets[i].getvy());
				if(bullets[i].gety()<0 || bullets[i].gety()>this.getSize().height) {
					bullets[i].setAlive(false);
				}
			}
		}
	}
	public void updateAsteroids() {
		for(int i = 0; i<ASTEROIDS; i++) {
			if(asteroids[i].isAlive()) {
				//update x stuffs
				asteroids[i].changex(asteroids[i].getvx());
				if(asteroids[i].getx()<0) {
					asteroids[i].setx(this.getSize().width);
				}
				else if(asteroids[i].getx()>this.getSize().width) {
					asteroids[i].setx(0);
				}
				//update y stuffs
				asteroids[i].changey(asteroids[i].getvy());
				if(asteroids[i].gety()<0) {
					asteroids[i].sety(this.getSize().height);
				}
				else if(asteroids[i].gety()>this.getSize().height) {
					asteroids[i].sety(0);
				}
				//adjust rotation
				asteroids[i].changeMoveAngle(asteroids[i].getRotateVel());
				//keep the angle within range (0-359)
				
			}
		}
	}

	//test for collisions
	public void checkCollisions() {
		for(int i = 0; i<ASTEROIDS; i++) {
			if(asteroids[i].isAlive()) {
				//check collision with a bullet
				for(int j = 0; j<BULLETS; j++) {
					if(bullets[j].isAlive()) {
						if(asteroids[i].getBounds().contains(bullets[j].getx(), bullets[j].gety())) {
							bullets[j].setAlive(false);
							asteroids[i].setAlive(false);
							break;
						}
					}
				}
				//check collision with the ship
				if(asteroids[i].getBounds().intersects(ship.getBounds())) {
					asteroids[i].setAlive(false);
					ship.setx(320);
					ship.sety(240);
					ship.setvx(0);
					ship.setvy(0);
					ship.setFaceAngle(0);
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent k) {
		
	}
	public void keyTyped(KeyEvent k) {
		
	}
	public void keyPressed(KeyEvent k) {
		System.out.println("key pressed " + Thread.currentThread().getName());
		int keyCode = k.getKeyCode();
		switch(keyCode) {
		case KeyEvent.VK_LEFT:
			//rotate ship left 5 degrees
			ship.changeFaceAngle(-5);
			//keep the angle within positive
			if(ship.getFaceAngle()<0) {
				ship.setFaceAngle(355);
			}
			break;
		case KeyEvent.VK_RIGHT:
			//rotate ship right 5 degrees
			ship.changeFaceAngle(5);
			//keep the angle within 360
			if(ship.getFaceAngle()>359) {
				ship.setFaceAngle(5);
			}
			break;
		case KeyEvent.VK_UP:
			//accelerate
			ship.setMoveAngle(ship.getFaceAngle()-90);
			ship.changevx(this.calcAngleMoveX(ship.getMoveAngle())*0.1);
			ship.changevy(this.calcAngleMoveY(ship.getMoveAngle())*0.1);
			break;
		case KeyEvent.VK_CONTROL:
		case KeyEvent.VK_ENTER:
		case KeyEvent.VK_SPACE:
			//fire
			currentBullet++;
			if(currentBullet>=BULLETS) {
				currentBullet = 0;
			}
			bullets[currentBullet].setAlive(true);
			
			//point bullet in the direction the ship is facing
			bullets[currentBullet].setx(ship.getx());
			bullets[currentBullet].sety(ship.gety());
			bullets[currentBullet].setMoveAngle(ship.getFaceAngle()-90);
			
			//fire bullet at angle of the ship
			double angle = bullets[currentBullet].getMoveAngle();
			bullets[currentBullet].setvx(ship.getvx() + this.calcAngleMoveX(angle) * 2);
			bullets[currentBullet].setvy(ship.getvy() + this.calcAngleMoveY(angle) * 2);
			break;
		}	
	}
}
