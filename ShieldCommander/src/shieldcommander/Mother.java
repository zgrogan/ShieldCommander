package shieldcommander;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

public class Mother extends Ship {

	public static int SHOT_SIZE = 15;
	public static int SHOT_SPEED = 6;
	public static Sound explodeSound;
	
	public Mother(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 720;
		this.height = 40;
		
		this.shape = new Rectangle(x-width/2, y-height/2, x + width/2, y-height/2);
		this.hitPoints = 500;
	}

	public void shoot(ArrayList<Entity> entities, Random rand) {
		int direction = rand.nextInt(90) - 45;
		boolean weAreRed = false;
		if (this.type == EntityType.redMotherShip)
			weAreRed = true;
		Ball ball = new Ball(this.x, this.y, SHOT_SIZE,
				weAreRed ? 180 - direction : direction, SHOT_SPEED);
		if (type == EntityType.blueMotherShip) {
			ball.setType(EntityType.largeBlueShot);
		} else {
			ball.setType(EntityType.largeRedShot);
		}
		ball.setImage(shotImage);
		entities.add(ball);
		// a hack.  get the sounds close to same volume.
		if (this.type == EntityType.blueMotherShip)
			shotSound.play(1.0f, 0.6f);
		else
			shotSound.play();

	}

	public void collide(Entity other) {
		super.collide(other);
	}

	public static void setExplodeSound(Sound sound) {
		explodeSound = sound;
		
	}
	
	
	public void explode() {
		explodeSound.play(1.0f, 0.7f);
		super.explode();
	}
}
