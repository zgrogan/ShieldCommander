package shieldcommander;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public class ShieldCommander extends BasicGame {

	static ArrayList<Controller> controllers;
	private ArrayList<Entity> entities;
	private Paddle blueShield;
	private Paddle redShield;

	public ShieldCommander(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	public void init(GameContainer container) throws SlickException {
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		entities = new ArrayList<Entity>();
		Controllers.poll();
		controllers = new ArrayList<Controller>();
		for (int i = 0; i < Controllers.getControllerCount(); i++) {
			Controller c = Controllers.getController(i);
			for (int j = 0; j < c.getAxisCount(); j++) {
				c.setDeadZone(j, 0.2f);
			}
			controllers.add(Controllers.getController(i));
		}
		redShield = controllers.size() > 0 ? new Paddle(50, 50, 100, 15,
				controllers.get(0)) : new Paddle(50, 50, 100, 15);

		blueShield = controllers.size() > 1 ? new Paddle(50, 535, 100, 15,
				controllers.get(1)) : new Paddle(50, 50, 100, 15);

		Block block = new Block(50, 115, 50, 50, 50, 8);
		entities.add(blueShield);
		entities.add(redShield);
		entities.add(block);
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		for (Entity entity : entities)
			entity.update(delta);

	}

	public void controllerButtonPressed(int controller, int button) {
		System.out.println(button + " button pressed on controller "
				+ controller);
	}

	public void keyReleased(int key, char code) {
		System.out.println(key + " and " + code);
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		for (Entity entity : entities) {
			for (Entity other : entities) {
				if (other != entity)
					if (other.getShape().intersects(entity.getShape())) {
						System.out.println("Collision Detected");
						entity.collide(other);
					}
			}
			g.fill(entity.shape);
		}

	}

	public static void main(String[] args) {

		try {
			AppGameContainer app = new AppGameContainer(new ShieldCommander(
					"Shield Commander"));
			app.setDisplayMode(800, 600, false);
			app.setAlwaysRender(true);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
