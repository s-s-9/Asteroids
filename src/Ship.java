import java.awt.Polygon;
import java.awt.Rectangle;

public class Ship extends BaseVectorShape{
	//polygon
	private int[] shipx = {-6, -3, 0, 3, 6, 0};
	private int[] shipy = {6, 7, 7, 7, 6, -7};
	
	//constructor
	Ship(){
		this.setShape(new Polygon(shipx, shipy, shipx.length));
		this.setAlive(true);
	}
	
	//bounding rectangle
	public Rectangle getBounds() {
		return new Rectangle((int)this.getx()-6, (int)this.gety()-6, 12, 12);
	}
}
