package skeletonProject;
import edu.utc.game.*;

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

import java.util.List;

public class SkeletonGame extends Game implements Scene {

	static FireGame fGame;
	public static void main(String[] args) {
		
		SkeletonGame aGame = new SkeletonGame();


		FireGame fGame = new FireGame();

		fGame.registerGlobalCallbacks();
		MainMenu mainMenu = new MainMenu(fGame);
		fGame.setScene(mainMenu);
		fGame.gameLoop();
		
	}
	
	
	public SkeletonGame() {
		initUI(1024,1024, "FireGame");
		
		glClearColor(.0f, .0f, .0f, .0f);
		
		//Put text Object
	}

	
	@Override
	public Scene drawFrame(int delta) {
		
		
		
		return this;
	}

}
