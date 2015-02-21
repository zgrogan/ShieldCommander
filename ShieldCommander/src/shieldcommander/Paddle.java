package shieldcommander;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.geom.Rectangle;

public class Paddle extends Entity {
	public static int windowX = 800;
	public static int windowY = 600;
	public static int type = EntityType.redShield;
	public static int CONTROLLER_ACCELERATION = 15;
	private int width;
	private int height;
	private Controller controller;

	public Paddle(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		shape = new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	public Paddle(float x, float y, int width, int height, Controller controller) {
		this(x, y, width, height);
		this.controller = controller;
	}

	public void update(int delta) {
		boolean hasController = controller != null;
		this.x = hasController ? x + CONTROLLER_ACCELERATION * controller.getAxisValue(1) : Mouse.getX()
				- width / 2;
		shape = new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	@Override
	public void collide(Entity other) {
		// TODO Animation

	}

	@Override
	public int getType() {
		return type;
	}

}
