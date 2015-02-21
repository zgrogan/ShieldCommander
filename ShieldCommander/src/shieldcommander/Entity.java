package shieldcommander;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

public abstract class Entity { 
	
	protected float x;         // center x
	protected float y;         // center y
	protected float direction; // 0 up 90 right 180 down 270 left
	protected float speed;  // pixels/second
	protected Shape shape;     // bounds (for collision detection)
	protected int type; // identify the type of entity
	protected Image image;     // image for drawing
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getDirection() {
		return direction;
	}
	public void setDirection(float direction) {
		this.direction = direction;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public void draw(Graphics g){
		g.draw(shape);
	}
	public abstract void update(int delta);
	public abstract void collide(Entity other);
	public abstract int getType();
	
	
}
