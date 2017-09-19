import java.awt.Rectangle;
import java.awt.Shape;

public abstract class BaseVectorShape {
	//instance variables
	private Shape shape;
	private boolean alive;
	private double x;
	private double y;
	private double vx;
	private double vy;
	private double moveAngle;
	private double faceAngle;
	
	//default constructor
	BaseVectorShape(){
		this.setShape(null);
		this.setAlive(false);
		this.setx(0.0);
		this.sety(0.0);
		this.setvx(0.0);
		this.setvy(0.0);
		this.setMoveAngle(0.0);
		this.setFaceAngle(0.0);
	}
	
	//getters
	public Shape getShape() {
		return this.shape;
	}
	public boolean isAlive() {
		return alive;
	}
	public double getx() {
		return this.x;
	}
	public double gety() {
		return this.y;
	}
	public double getvx() {
		return this.vx;
	}
	public double getvy() {
		return this.vy;
	}
	public double getMoveAngle() {
		return this.moveAngle;
	}
	public double getFaceAngle() {
		return this.faceAngle;
	}
	
	//setters
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public void setx(double x) {
		this.x = x;
	}
	public void sety(double y) {
		this.y = y;
	}
	public void setvx(double vx) {
		this.vx = vx;
	}
	public void setvy(double vy) {
		this.vy = vy;
	}
	public void setMoveAngle(double moveAngle) {
		this.moveAngle = moveAngle;
	}
	public void setFaceAngle(double faceAngle) {
		this.faceAngle = faceAngle;
	}
	
	//helpers
	public void changex(double x) {
		this.x += x;
	}
	public void changey(double y) {
		this.y += y;
	}
	public void changevx(double vx) {
		this.vx += vx;
	}
	public void changevy(double vy) {
		this.vy += vy;
	}
	public void changeMoveAngle(double moveAngle) {
		this.moveAngle += moveAngle;
	}
	public void changeFaceAngle(double faceAngle) {
		this.faceAngle += faceAngle;
	}
	public abstract Rectangle getBounds();
}
