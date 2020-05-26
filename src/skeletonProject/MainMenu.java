package skeletonProject;

import static org.lwjgl.opengl.GL11.glClearColor;
import  org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;
import edu.utc.game.*;

public class MainMenu implements Scene {

	SimpleMenu menu;
	FireGame game;
	public MainMenu(FireGame fireGame) {
		
		this.game = fireGame;
		glClearColor(1f, 1f, 1f, 1f);
		menu = new SimpleMenu();
		menu.addItem(new SimpleMenu.SelectableText(20, 20, 20, 20, "Start Game", 1, 0, 0, 1, 1, 1), game);
		menu.addItem(new SimpleMenu.SelectableText(20, 100, 20, 20, "Get Outta Here", 1, 0, 0, 1, 1, 1), null);
		menu.select(0);
		
		
		
		
		
	}
	
	@Override
	public Scene drawFrame(int delta) {
		Text text = new Text(200,50,30,30, "Save The Trees");
		text.draw();
		return menu.drawFrame(delta);
	}

	
	
}
