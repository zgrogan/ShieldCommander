package shieldcommander;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.geom.Polygon;

public class Paddle extends Entity {
	public static int windowX = 720;
	public static int windowY = 600;
	public static int CONTROLLER_ACCELERATION = 10;
	private Controller controller;
	private float offset;
	
	// construct a Paddle at specified location and size
	public Paddle(float x, float y, int width, int height, int type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.type = type;
		offset = (type == EntityType.blueShield) ? height / 2 : -height / 2;

		float[] points = new float[] { x, y + offset, x - width / 2, y - offset,
				x + width / 2, y - offset };
		// make a triangle to closely approximate hit box.
		shape = new Polygon(points);

	}

	// Paddle construction with controller attached
	public Paddle(float x, float y, int width, int height, int type,
			Controller controller) {
		this(x, y, width, height, type);
		this.controller = controller;
	}

	public void update(int delta) {
		boolean hasController = controller != null;
		this.x = hasController ? x + CONTROLLER_ACCELERATION
				* (controller.isButtonPressed(0) ? 2 : 1)
				* controller.getAxisValue(1) : Mouse.getX() - width / 2;
		if (x < width / 2)
			x = width / 2;
		if (x > windowX - width / 2)
			x = (windowX - width / 2);
		float[] points = new float[] { x, y + offset, x - width / 2, y - offset,
				x + width / 2, y - offset};
		// make a triangle to closely approximate hit box.
		shape = new Polygon(points);
	}

	@Override
	public void explode() {
		// empty method. Paddle cannot die
	}

	@Override
	public void collide(Entity other) {
		// TODO: animation when hit
	}
}
