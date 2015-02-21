package shieldcommander;

import org.newdawn.slick.geom.Rectangle;

public class Block extends Entity {
	public static int windowX = 800;
	public static int windowY = 600;
	public static double PI = Math.PI;
	public static int type = EntityType.largeBlueShot;
	private int width;
	private int height;

	public Block(float x, float y, int width, int height, float dir, float speed) {
		if (width / 2 > x)
			this.x = width / 2 + 1;
		else
			this.x = x;
		if (height / 2 > y)
			this.y = height / 2 + 1;
		else
			this.y = y;
		this.direction = (float) ((dir / 180) * PI);
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.shape = new Rectangle(x - (width / 2), y - (height / 2), width,
				height);
	}

	public void update(int delta) {
		float newx = x + getdx();
		float newy = y + getdy();
		if (newx > windowX - (width / 2) || newx < width / 2) {
			this.direction = -direction;
		}
		if (newy > windowY - (height / 2) || newy < height / 2) {
			this.direction = (float) (PI - direction);
		}
		x += getdx();
		y += getdy();
		this.shape = new Rectangle(x - (width / 2), y - (height / 2), width,
				height);

	}

	private float getdy() {
		return (float) (Math.cos(direction) * speed);
	}

	private float getdx() {
		return (float) (Math.sin(direction) * speed);
	}

	public void collide(Entity other) {
		if (other.getType() == EntityType.redShield
				|| other.getType() == EntityType.blueShield) {
			System.out.print("Collision with shield: ");
			System.out.print("Old direction: " + direction);
			this.direction = (float) (PI - direction);
			System.out.println(" New direction: " + direction);
		}

	}

	@Override
	public int getType() {
		return type;
	}
}
