import java.awt.Rectangle;
import java.awt.Polygon;

public class Asteroid extends BaseVectorShape {
	//polygon
	int[] astx = {-20, -13, 0, 20, 22, 20, 12, 2, -10, -22, -16};
	int[] asty = {20, 23, 17, 20, 16, -20, -22, -14, -17, -20, -5};
	
	//asteroids will rotate, define a rotate velocity and getter/setter for it
	private double rotateVel;
	
	//getter/setter
	public double getRotateVel() {
		return this.rotateVel;
	}
	public void setRotateVel(double rotateVel) {
		this.rotateVel = rotateVel;
	}
	
	//constructor
	Asteroid(){
		this.setShape(new Polygon(astx, asty, astx.length));
		this.setAlive(true);
		this.setRotateVel(0.0);
	}
	
	//bounding rectangle
	public Rectangle getBounds() {
		return new Rectangle((int)this.getx()-20, (int)this.gety()-20, 40, 40);
	}
}
