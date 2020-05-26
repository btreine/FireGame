package skeletonProject;

import static org.lwjgl.opengl.GL11.glClearColor;

import org.lwjgl.opengl.GL11;

import edu.utc.game.Game;
import edu.utc.game.Scene;
import edu.utc.game.SimpleMenu;
import edu.utc.game.Sound;
import edu.utc.game.Text;

public class Victory extends Game implements Scene {

	private FireGame game;
	private MainMenu menu;
	Sound sound;
	
	public Victory (FireGame fireGame) {
		game = fireGame;
		sound = new Sound ("res/sound.wav");
		sound.play();
		//menu = new MainMenu(new FireGame());

	}
	
	// public void onKeyEvent(int key, int scancode, int action, int mods)  {
		// if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER) {
		//	 MainMenu newMenu = new MainMenu(new FireGame());
			// this.setScene(newMenu);
		// }
	// };
	
	
	
	@Override
	public Scene drawFrame(int delta) {       
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER)) {
			 MainMenu newMenu = new MainMenu(new FireGame());
			this.setScene(newMenu);
			return newMenu;
		}
		Text vicText = new Text(50, 100, 30, 30, "YOU WON!");
		Text clickText = new Text(200,50,30,30, "Level: " + game.getLevel());
		Text timeText  = new Text(150, 300, 30, 30, "Time: " + Math.floor(game.time/60));
		vicText.draw();
		clickText.draw();
		timeText.draw();
		
		return this;
	}

	
	//public void update(int delta) {
	//	if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER)) {
		//	this.setScene(menu);
	//	} 
	//}
	
	
}
