package shieldcommander;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;

public class Mother extends Ship {

	public static int SHOT_SIZE = 10;
	public static int SHOT_SPEED = 7;
	
	public Mother(int x, int y) {
		this.x = x;
		this.y = y;
		this.shape = new Rectangle(0, y - 10, 720, 20);
		this.hitPoints = 500;
	}

	public void shoot(ArrayList<Entity> entities, Random rand) {
		int direction = rand.nextInt(90) - 45;
		if (this.type == EntityType.redMotherShip)
			direction = (int) (180 - direction);
		Ball ball = new Ball(this.x, this.y, SHOT_SIZE, direction, SHOT_SPEED);
		if (type == EntityType.blueMotherShip)
			ball.setType(EntityType.largeBlueShot);
		else
			ball.setType(EntityType.largeRedShot);
		entities.add(ball);
	}
	
	public void collide(Entity other) {
		super.collide(other);
		if (this.type == EntityType.redMotherShip)
			System.out.print("Red Mother ");
		else System.out.print("Blue Mother ");
		System.out.println(this.hitPoints + " HP remaining.");
	}
}
