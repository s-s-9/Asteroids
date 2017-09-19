import java.awt.Rectangle;

public class Bullet extends BaseVectorShape {
	//constructor
	Bullet(){
		this.setShape(new Rectangle(0, 0, 1, 1));
		this.setAlive(false);
	}
	
	//bounding rectangle
	public Rectangle getBounds() {
		return new Rectangle((int)this.getx(), (int)this.gety(), 1, 1);
	}
}
