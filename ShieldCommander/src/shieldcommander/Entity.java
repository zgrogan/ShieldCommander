package shieldcommander;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

public abstract class Entity { 
	
	protected boolean dead = false;
	protected float width; 
	protected float height;
	protected float x;         	// center x
	protected float y;         	// center y
	protected float direction; 	// 0 up 90 right 180 down 270 left
	protected float speed;  	// pixels/second
	protected Shape shape;		// bounds (for collision detection)
	protected int type; 		// identify the type of entity
	protected Image image;     	// image for drawing
	protected boolean collidable = false;
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
	public void explode(){
		dead = true;
	}
	public abstract void update(int delta);
	public abstract void collide(Entity other);
	public int getType() {
		return type;
	};
	public boolean isDead() {
		return dead;
	}
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	public boolean isCollidable() {
		return collidable;
	}
	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}	
}
