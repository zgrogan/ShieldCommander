package shieldcommander;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

public class Fighter extends Ship {

	private static int SHOT_SIZE = 8;
	private static int SHOT_SPEED = 7;
	private static Sound explodeSound;
	
	public Fighter() {
	}

	public Fighter(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 140;
		this.height = 50;
		this.shape = new Rectangle(x - width/2, y - 25, width, 20 );
		this.hitPoints = 100;
	}

	public void shoot(ArrayList<Entity> entities, Random rand) {
		
		// choose shot direction and type
		int direction = rand.nextInt(90) - 45;
		int ballType = EntityType.smallBlueShot;
		if (this.type == EntityType.redFighter) {
			direction = (int) (180 - direction);
			ballType = EntityType.smallRedShot;			
		}
		
		// create the ball and put into play
		Ball ball = new Ball(this.x, this.y, SHOT_SIZE, direction, SHOT_SPEED);
		ball.setType(ballType);
		ball.setImage(shotImage);
		entities.add(ball);
		shotSound.play();
	}

	@Override
	public void update(int delta) {

	}
	
	public static void setExplodeSound(Sound sound) {
		explodeSound = sound;
	}
	
	public void explode() {
		this.dead = true;
		explodeSound.play();
		super.explode();
	}
}
