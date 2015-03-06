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

	// handle collisions (hit by projectile)
	public void collide(Entity other) {
		if (other.isCollidable()) {
			boolean isRed = (this.type == EntityType.redMotherShip 
					|| this.type == EntityType.redFighter);
			int lost = 0;
			switch (other.getType()) {
			case EntityType.smallRedShot:
				lost = isRed ? 10 : 30;
				break;
			case EntityType.smallBlueShot:
				lost = isRed ? 30 : 10;
				break;
			case EntityType.largeRedShot:
				lost = isRed ? 30 : 90;
				break;
			case EntityType.largeBlueShot:
				lost = isRed ? 90 : 30;
				break;
			}
			this.hitPoints -= lost;
		}
		if (hitPoints < 1)
			explode();
	}
}
