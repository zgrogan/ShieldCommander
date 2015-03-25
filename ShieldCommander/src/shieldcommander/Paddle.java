package shieldcommander;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.geom.Rectangle;

public class Paddle extends Entity {
	public static int windowX = 720;
	public static int windowY = 600;
	public static int CONTROLLER_ACCELERATION = 10;
	private int width;
	private int height;
	private Controller controller;

	// construct a Paddle at specified location and size
	public Paddle(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		shape = new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	// Paddle construction with controller attached
	public Paddle(float x, float y, int width, int height, Controller controller) {
		this(x, y, width, height);
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
		shape = new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
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
