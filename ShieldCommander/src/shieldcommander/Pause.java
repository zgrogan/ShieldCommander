package shieldcommander;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Pause extends BasicGameState {
	
	private static final int id = 0;
	private int timer = 0;
	private StateBasedGame game;
	private ShieldCommander sc;

	public Pause(ShieldCommander sc) {
		this.sc = sc;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		this.game = game;
		
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame game) {
		this.timer = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		int redWins = sc.getRedWins();
		int blueWins = sc.getBlueWins();
		g.drawString("Red: " + redWins, 100, 100);
		g.drawString("Blue: " + blueWins, 100, 130);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		this.timer += delta;
		if (timer > 5000) {
			game.enterState(1);
		}
		
	}

	@Override
	public int getID() {
		return this.id;
	}

}
