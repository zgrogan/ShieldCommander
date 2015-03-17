package shieldcommander;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ShieldCommander extends BasicGame {
	
	private Graphics g;

	static ArrayList<Controller> controllers; // detected joysticks

	// shot counters
	static int redFighterCounter = 0;
	static int blueFighterCounter = 0;
	static int redMotherCounter = 0;
	static int blueMotherCounter = 0;

	// time between shots
	private int motherShotTime = 5000;
	private int fighterShotTime = 2000;
	
	// entities
	private ArrayList<Entity> entities;
	private Paddle blueShield;
	private Paddle redShield;
	private Mother blueMother;
	private Mother redMother;
	private ArrayList<Fighter> blueFighters;
	private int numBlueFighters = 5;
	private ArrayList<Fighter> redFighters;
	private int numRedFighters = 5;
	private Random rand; // random number generator

	public ShieldCommander(String title) {
		super(title);
	}

	public void init(GameContainer container) throws SlickException {
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// start random number generator
		rand = new Random(new Date().getTime());

		// set up Game Controllers
		Controllers.poll();
		controllers = new ArrayList<Controller>();
		for (int i = 0; i < Controllers.getControllerCount(); i++) {
			Controller c = Controllers.getController(i);
			for (int j = 0; j < c.getAxisCount(); j++) {
				c.setDeadZone(j, 0.2f);
			}
			// ignore generic USB devices
			if(!(c.getName().contains("USB")))
				controllers.add(Controllers.getController(i));
		}

		// add entities to game board
		entities = new ArrayList<Entity>();

		// add shields
		redShield = controllers.size() > 0 ? new Paddle(50, 100, 100, 15,
				controllers.get(0)) : new Paddle(50, 100, 100, 15);
		redShield.setType(EntityType.redShield);

		blueShield = controllers.size() > 1 ? new Paddle(100, 500, 100, 15,
				controllers.get(1)) : new Paddle(50, 500, 100, 15);
		blueShield.setType(EntityType.blueShield);
		entities.add(blueShield);
		entities.add(redShield);

		// add fighters
		blueFighterCounter = rand.nextInt(fighterShotTime);
		redFighterCounter = rand.nextInt(fighterShotTime);
		blueFighters = new ArrayList<Fighter>();
		redFighters = new ArrayList<Fighter>();
		for (int i = 0; i < 5; i++) {
			Fighter blue = new Fighter(50 + 150 * i, 80);
			Fighter red = new Fighter(50 + 150 * i, 550);
			blue.setType(EntityType.blueFighter);
			red.setType(EntityType.redFighter);
			blueFighters.add(blue);
			redFighters.add(red);
			entities.add(blue);
			entities.add(red);
		}

		// add Motherships
		blueMotherCounter = rand.nextInt(motherShotTime);
		redMotherCounter = rand.nextInt(motherShotTime);
		blueMother = new Mother(360, 30);
		redMother = new Mother(360, 575);
		blueMother.setType(EntityType.blueMotherShip);
		redMother.setType(EntityType.redMotherShip);
		entities.add(blueMother);
		entities.add(redMother);
	}

	public void update(GameContainer container, int delta)
			throws SlickException {
		if (blueMother.isDead() || redMother.isDead())
			container.pause();
		else {
			// remove dead entities
			ArrayList<Entity> toRemove = new ArrayList<Entity>();
			for (Entity entity : entities) {
				if (entity.isDead()) {
					// decrement fighter count
					if (entity.getType() == EntityType.blueFighter)
						numBlueFighters--;
					if (entity.getType() == EntityType.redFighter)
						numRedFighters--;
					// remove the object
					toRemove.add(entity);
				}
			}
			for (int i = 0; i < toRemove.size(); i++)
				entities.remove(toRemove.get(i));

			// update the entities
			for (Entity entity : entities) {
				entity.update(delta);
			}

			// fighters shoot
			blueFighterCounter += delta;
			redFighterCounter += delta;
			if (numBlueFighters > 0)
				if (blueFighterCounter > fighterShotTime) {
					blueFighters.get(rand.nextInt(numBlueFighters)).shoot(
							entities, rand);
					blueFighterCounter = 0;
				}
			if (numRedFighters > 0)
				if (redFighterCounter > fighterShotTime) {
					redFighters.get(rand.nextInt(numRedFighters)).shoot(
							entities, rand);
					redFighterCounter = 0;
				}

			// motherships shoot
			blueMotherCounter += delta;
			redMotherCounter += delta;
			if (blueMotherCounter > motherShotTime) {
				blueMother.shoot(entities, rand);
				blueMotherCounter = 0;
			}
			if (redMotherCounter > motherShotTime) {
				redMother.shoot(entities, rand);
				redMotherCounter = 0;
			}
		}
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
			app.setDisplayMode(720, 600, false);
			app.setAlwaysRender(true);
			app.setVSync(true);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
