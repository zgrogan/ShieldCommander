package shieldcommander;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ShieldCommander extends BasicGameState {

	// game and container
	private static final int id = 1;
	private StateBasedGame game;
	private GameContainer gc;
	private boolean gameEnding = false;
	private int gameEndTimer = 0;

	// images
	private Image smallBlueShotImage;
	private Image smallRedShotImage;
	private Image largeRedShotImage;
	private Image largeBlueShotImage;
	private Image blueFighterImage;
	private Image redFighterImage;
	private Image blueMotherImage;
	private Image redMotherImage;
	private Image redShieldImage;
	private Image blueShieldImage;
	private Image backgroundImage;
	private int bgOffsetX = 0;
	private int bgOffsetY = 0;
	private SpriteSheet sploshun;

	// Time before firing
	private int deadTime = 3000;

	// Win Counts
	private int redWins = 0;
	private int blueWins = 0;

	// detected joysticks
	static ArrayList<Controller> controllers;

	// sounds
	private HashMap<String, Sound> sounds;

	// shot counters
	static int redFighterCounter = 0;
	static int blueFighterCounter = 0;
	static int redMotherCounter = 0;
	static int blueMotherCounter = 0;

	// time between shots
	private int motherShotTime = 5000;
	private int fighterShotTime = 10000;

	// game end time
	private static int gameEndTime = 2000;

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
	private static ShieldCommander myShieldCommander; // singleton object

	public static ShieldCommander getSheildCommander() {
		if (myShieldCommander == null)
			myShieldCommander = new ShieldCommander();
		return myShieldCommander;
	}

	// singleton constructor
	private ShieldCommander() {
		sounds = new HashMap<String, Sound>();
	}

	public int getID() {
		return id;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame game) {
		gameEnding = false;
		gameEndTimer = 0;
		bgOffsetX = (backgroundImage.getWidth() - gc.getWidth());
		bgOffsetY = (backgroundImage.getHeight() - gc.getHeight());
		try {
			init(this.gc, this.game);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		this.gc = container;
		this.game = game;
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// images
		backgroundImage = new Image("data/img/backgrounds/hdspace2.png");
		blueFighterImage = new Image("data/img/spaceships/blue_fighter.png");
		redFighterImage = new Image("data/img/spaceships/red_fighter.png");
		blueShieldImage = blueFighterImage;
		blueMotherImage = new Image("data/img/spaceships/blue_mother.png");
		redShieldImage = redFighterImage;
		redMotherImage = new Image("data/img/spaceships/red_mother.png");
		smallBlueShotImage = new Image("data/img/shots/blueShot.png");
		largeBlueShotImage = smallBlueShotImage;
		smallRedShotImage = new Image("data/img/shots/redShot.png");
		largeRedShotImage = smallRedShotImage;
		sploshun = new SpriteSheet("data/img/sploshuns/sploshun.png", 100, 100);

		// initialize sounds
		sounds.put("ballExplode", new Sound("sounds/Boom/Hit.ogg"));
		Ball.setExplodeSound(sounds.get("ballExplode"));
		sounds.put("fighterExplode",
				new Sound("sounds/Boom/FighterExplode.ogg"));
		Fighter.setExplodeSound(sounds.get("fighterExplode"));
		sounds.put("motherExplode", new Sound(
				"sounds/Boom/MothershipExplode.ogg"));
		Mother.setExplodeSound(sounds.get("motherExplode"));
		sounds.put("redFighterShoot", new Sound("sounds/Pew/FighterFire1.wav"));
		sounds.put("blueFighterShoot", new Sound("sounds/Pew/FighterFire2.wav"));
		sounds.put("redMotherShoot",
				new Sound("sounds/Pew/MothershipFire1.wav"));
		sounds.put("blueMotherShoot", new Sound(
				"sounds/Pew/MothershipFire2.wav"));

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
			if (!(c.getName().contains("USB")))
				controllers.add(Controllers.getController(i));
		}

		// add entities to game board
		entities = new ArrayList<Entity>();

		// add shields
		blueShield = controllers.size() > 0 ? new Paddle(50, 110, 100, 15,
				EntityType.blueShield, controllers.get(0)) : new Paddle(50,
				110, 100, 30, EntityType.blueShield);
		blueShield.setImage(blueShieldImage);

		redShield = controllers.size() > 1 ? new Paddle(100, 510, 100, 15,
				EntityType.redShield, controllers.get(1)) : new Paddle(50, 510,
				100, 30, EntityType.redShield);
		redShield.setImage(redShieldImage);
		entities.add(blueShield);
		entities.add(redShield);

		// add fighters
		fighterShotTime = 10000;
		numBlueFighters = 5;
		numRedFighters = 5;
		blueFighterCounter = rand.nextInt(fighterShotTime / 5) - deadTime;
		redFighterCounter = rand.nextInt(fighterShotTime / 5) - deadTime;
		blueFighters = new ArrayList<Fighter>();
		redFighters = new ArrayList<Fighter>();
		for (int i = 0; i < 5; i++) {
			Fighter blue = new Fighter(50 + 150 * i, 80);
			Fighter red = new Fighter(50 + 150 * i, 550);
			blue.setType(EntityType.blueFighter);
			blue.setShotSound(sounds.get("blueFighterShoot"));
			blue.setShotImage(smallBlueShotImage);
			blue.setImage(blueFighterImage);
			red.setType(EntityType.redFighter);
			red.setShotSound(sounds.get("redFighterShoot"));
			red.setShotImage(smallRedShotImage);
			red.setImage(redFighterImage);
			blueFighters.add(blue);
			redFighters.add(red);
			entities.add(blue);
			entities.add(red);
		}

		// add Motherships
		blueMotherCounter = rand.nextInt(motherShotTime / 2) - deadTime;
		redMotherCounter = rand.nextInt(motherShotTime / 2) - deadTime;
		blueMother = new Mother(360, 30);
		redMother = new Mother(360, 575);
		blueMother.setType(EntityType.blueMotherShip);
		blueMother.setShotImage(largeBlueShotImage);
		blueMother.setImage(blueMotherImage);
		redMother.setType(EntityType.redMotherShip);
		blueMother.setShotSound(sounds.get("blueMotherShoot"));
		redMother.setShotSound(sounds.get("redMotherShoot"));
		redMother.setShotImage(largeRedShotImage);
		redMother.setImage(redMotherImage);
		entities.add(blueMother);
		entities.add(redMother);
	}

	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// game end conditions
		if ((blueMother.isDead() || redMother.isDead()) && !gameEnding) {
			this.gameEnding = true;
			if (blueMother.isDead()) {
				redWins++;
			}
			if (redMother.isDead()) {
				blueWins++;
			}
		}
		
		if (gameEnding) {
			gameEndTimer += delta;
			if (gameEndTimer > gameEndTime)
				game.enterState(0);
		}
		
		// remove dead entities
		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		ArrayList<Entity> toAdd = new ArrayList<Entity>();
		for (Entity entity : entities) {
			if (entity.isDead()) {
				// decrement fighter count
				if (entity.getType() == EntityType.blueFighter) {
					numBlueFighters--;
					blueFighters.remove(entity);
					this.fighterShotTime -= 1000;
				}
				if (entity.getType() == EntityType.redFighter) {
					numRedFighters--;
					redFighters.remove(entity);
					this.fighterShotTime -= 1000;
				}
				// add sploshuns
				switch (entity.getType()) {
				// sploshuns don't splode
				case EntityType.sploshun:
					break;
				// shots splode fast
				case EntityType.smallBlueShot:
				case EntityType.smallRedShot:
				case EntityType.largeBlueShot:
				case EntityType.largeRedShot:
					toAdd.add(new Sploshun(entity, 250, sploshun));
					break;
				// ships take longer to splode
				case EntityType.redMotherShip:
				case EntityType.blueMotherShip:
					toAdd.add(new Sploshun(entity, 2000, sploshun));
					break;
				default:
					toAdd.add(new Sploshun(entity, sploshun));
				}
				// remove the object
				toRemove.add(entity);
			}
		}
		for (int i = 0; i < toRemove.size(); i++)
			entities.remove(toRemove.get(i));
		entities.addAll(toAdd);

		// update the entities
		for (Entity entity : entities) {
			entity.update(delta);
		}

		// fighters shoot
		blueFighterCounter += delta;
		redFighterCounter += delta;
		if (numBlueFighters > 0)
			if (blueFighterCounter > fighterShotTime / numBlueFighters) {
				blueFighters.get(rand.nextInt(numBlueFighters)).shoot(entities,
						rand);
				blueFighterCounter = 0;
			}
		if (numRedFighters > 0)
			if (redFighterCounter > fighterShotTime / numRedFighters) {
				redFighters.get(rand.nextInt(numRedFighters)).shoot(entities,
						rand);
				redFighterCounter = 0;
			}

		// motherships shoot
		blueMotherCounter += delta;
		redMotherCounter += delta;
		if (blueMotherCounter > motherShotTime && !blueMother.isDead()) {
			blueMother.shoot(entities, rand);
			blueMotherCounter = 0;
		}
		if (redMotherCounter > motherShotTime && !redMother.isDead()) {
			redMother.shoot(entities, rand);
			redMotherCounter = 0;
		}

		// collision detection
		for (Entity entity : entities) {
			for (Entity other : entities) {
				if (other != entity)
					if (other.getShape().intersects(entity.getShape())) {
						entity.collide(other);
					}
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
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		backgroundImage.draw(0, 0, bgOffsetX, bgOffsetY,
				bgOffsetX + gc.getWidth() * 8, bgOffsetY + gc.getHeight() * 5);
		for (Entity entity : entities) {
			Image image = entity.getImage();
			if (entity.getImage() != null) {
				float x = entity.getX();
				float y = entity.getY();
				float width = entity.getWidth();
				float height = entity.getHeight();
				image.draw(x - width / 2, y - height / 2, width * 1.1f,
						height * 0.7f);
			} else
				g.fill(entity.shape);
		}

	}

	public int getRedWins() {
		return this.redWins;
	}

	public int getBlueWins() {
		return this.blueWins;
	}
}
