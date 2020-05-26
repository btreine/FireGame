package skeletonProject;

import static org.lwjgl.opengl.GL11.glClearColor;

import edu.utc.game.Scene;
import edu.utc.game.SimpleMenu;

public class Pause implements Scene  {

	MainMenu mainMenu;
	FireGame game;
	SimpleMenu pauseMenu;
	
	public Pause(FireGame fireGame) {
		this.game = fireGame;
		glClearColor(1f, 1f, 1f, 1f);
		pauseMenu = new SimpleMenu();
		
		mainMenu = new MainMenu(new FireGame());
		pauseMenu.addItem(new SimpleMenu.SelectableText(20, 20, 20, 20, "Continue", 1, 0, 0, 1, 1, 1), game);
		pauseMenu.addItem(new SimpleMenu.SelectableText(20, 60, 20, 20, "Main Menu", 1, 0, 0, 1, 1, 1), mainMenu);
		pauseMenu.addItem(new SimpleMenu.SelectableText(20, 100, 20, 20, "Get Outta Here", 1, 0, 0, 1, 1, 1), null);
		pauseMenu.select(0);
		
	}
	
	@Override
	public Scene drawFrame(int delta) {
		// TODO Auto-generated method stub
		return pauseMenu.drawFrame(delta);
	}

}
