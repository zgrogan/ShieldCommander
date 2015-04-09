package shieldcommander;

import org.newdawn.slick.SpriteSheet;

public class Sploshun extends Entity {
	
	private int timeToComplete;
	SpriteSheet sprites;
	private int timePassed = 0;

	public Sploshun(Entity entity,  int time, SpriteSheet sprites) {
		this.setType(EntityType.sploshun);
		this.shape = entity.getShape();
		this.setCollidable(false);
		this.sprites = sprites;
		this.timeToComplete = time;
		this.setX(entity.getX());
		this.setY(entity.getY());
		this.setWidth(entity.getWidth());
		this.setHeight(entity.getHeight());
	}
	
	public Sploshun(Entity entity, SpriteSheet sprites) {
		this(entity, 1000, sprites);
	}
	
	@Override
	public void update(int delta) {
		timePassed+=delta;
		if (timePassed > timeToComplete)
			this.setDead(true);
		int onPart = timePassed * 81 / ((timeToComplete == 0)?1:timeToComplete);
		int spriteX = onPart % 9;
		int spriteY = onPart / 9;
		if (spriteY > 7 && spriteY > 2) {
			spriteY = 7;
			spriteX = 2;
		}
		this.setImage(sprites.getSprite(spriteX, spriteY));
	}

	@Override
	public void collide(Entity other) {
		// TODO Auto-generated method stub

	}

}
