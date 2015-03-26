package shieldcommander;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Circle;

public class Ball extends Entity {
	public static int windowX = 720;
	public static int windowY = 600;
	public static double PI = Math.PI;
	public static int inactiveTime = 400;
	private int radius;
	private int deltaCounter; // used for timers.
	private static Sound explodeSound;

	public Ball(float x, float y, int radius, float direction, float speed) {

		/*
		 * if (windowX - radius > x) this.x = radius + 1; else
		 */
		this.x = x;

		/*
		 * if (windowY - radius > y) this.y = radius + 1; else
		 */
		this.y = y;
		this.direction = (float) ((direction / 180) * PI);
		this.speed = speed;
		this.radius = radius;
		width = height = radius * 2;
		this.shape = new Circle(x - (radius), y - (radius), radius);
		this.deltaCounter = 0;
	}
	
	public static void setExplodeSound(Sound sound) {
		explodeSound = sound;
	}

	public void update(int delta) {
		// set inactive or active
		if (!collidable) {
			deltaCounter += delta;
			if (deltaCounter > inactiveTime) {
				collidable = true;
				deltaCounter = 0;
			}
		}

		float newx = x + getdx();
		float newy = y + getdy();
		if (newx > windowX - radius || newx < radius) {
			this.direction = -direction;
		}
		if (newy > windowY - radius || newy < radius) {
			this.direction = (float) (PI - direction);
		}
		x += getdx();
		y += getdy();
		this.shape = new Circle(x - radius, y - radius, radius);

	}

	private float getdy() {
		return (float) (Math.cos(direction) * speed);
	}

	private float getdx() {
		return (float) (Math.sin(direction) * speed);
	}

	public void collide(Entity other) {
		if (collidable)
			if (other.getType() == EntityType.blueShield
					|| other.getType() == EntityType.redShield) {
				this.direction = (float) (PI - direction); // reflect
				collidable = false; // prevent collision sticking
				float diffX = this.x - other.getX();
				// give up to 45 degrees of angle based on where ball hits paddle
				float offset = (float) ((PI / 4) * diffX / (other.shape
						.getWidth() / 2));
				direction = (other.getType() == EntityType.blueShield) 
						? direction + offset
						: direction - offset;
			} else {
				int ot = other.getType();
				// so shots don't destory each other
				if (ot != EntityType.smallBlueShot
					&& ot != EntityType.smallRedShot
					&& ot != EntityType.largeBlueShot
					&& ot != EntityType.largeRedShot)
					this.explode(); // explode the ball
			}
	}

	@Override
	public int getType() {
		return type;
	}
	
	@Override
	public void explode() {
		explodeSound.play(1.0f, 0.5f);
		super.explode();
	}
}
