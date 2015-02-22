package shieldcommander;

import java.util.ArrayList;
import java.util.Random;

public abstract class Ship extends Entity {

	protected int hitPoints;

	public abstract void shoot(ArrayList<Entity> entities, Random rand);

	@Override
	public void update(int delta) {
		if (hitPoints < 0)
			explode();
	}

	public void collide(Entity other) {
		if (other.isCollidable()) {
			boolean weAreRed = (type == EntityType.redMotherShip 
					|| type == EntityType.redFighter);
			switch (other.getType()) {
			case EntityType.smallRedShot:
				hitPoints -= (weAreRed) ? 10 : 30;
				break;
			case EntityType.smallBlueShot:
				hitPoints -= weAreRed ? 30 : 10;
				break;
			case EntityType.largeRedShot:
				hitPoints -= weAreRed ? 30 : 90;
				break;
			case EntityType.largeBlueShot:
				hitPoints -= weAreRed ? 90 : 30;
				break;
			}
		}
		if (hitPoints < 1)
			explode();
	}
}
