package shieldcommander;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class ShieldCommanderGame extends StateBasedGame {
	
	private GameContainer gc;

	public ShieldCommanderGame(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		try {
			AppGameContainer app = new AppGameContainer(new ShieldCommanderGame(
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
	
	public void reset() {
		try {
			initStatesList(gc);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.gc = gc;
		ShieldCommander sc = ShieldCommander.getSheildCommander();
		addState(new Loading(sc));
		addState(sc);
		this.enterState(0);
		
	}

}
