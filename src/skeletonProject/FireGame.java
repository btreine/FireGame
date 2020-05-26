package skeletonProject;

import static org.lwjgl.opengl.GL11.glClearColor;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.lang.reflect.Array;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11.*;


import edu.utc.game.*;
import skeletonProject.FireGame.Bullet;

public class FireGame extends Game implements Scene  {
	private static java.util.Random rand=new java.util.Random();
	
	public Sound music;
	public int time;
	public int clicks;
	MainMenu menu;
	Pause pauseMenu;
	private boolean win;
	Player p;
	Background bg;
	Ground ground;
	private List<Bullet> bullets;
	Tree tree;
	private List<Tree> trees;
	private int level;
	int burningTrees;
	List<Lightning> lightning;
	
	public Sound water;
	
	public FireGame() {
		
		
		
		glClearColor(1f, 1f, 1f, 0f);
		
		registerGlobalCallbacks();
		p = new Player();
		bg = new Background();
		
		ground = new Ground();
		time = 0;
		clicks = 0;
		menu = new MainMenu(this);
		win = false;
		bullets = new java.util.LinkedList<Bullet>();
		trees = new java.util.LinkedList<Tree>();
		lightning = new java.util.LinkedList<Lightning>();
		setLevel(0);
		burningTrees = 0;
		water = new Sound("res/water.wav");
		music = new Sound("res/music.wav");
		music.play();
	}
	
	public int countBurningTrees() {
		int count = 0;
		for (Tree t : trees) {
			if (t.burning) {
				count++;
			}
		}
		
		
		return count;
	}
	
	
	
	public void onMouseEvent(int button, int action, int mods) {
		if (button == 0) {water.play();
		////	clicks++;
			
			//if (clicks > 20) {
			//	Victory vicScene = new Victory(this);
			//	this.setScene(vicScene);
			//}
			
			
		}
		if (button == 1) {
			System.out.println("WE ARE PAUSED");
			this.setScene(new Pause(this));
			
		}
	}
	
	
	@Override
	public Scene drawFrame(int delta) {
	//	music.play();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		if (countBurningTrees() == 0) {
			
			setLevel(getLevel() + 1);
			startNewLevel(trees, getLevel());
		}
		
		if ((countBurningTrees() / trees.size() )> .75) {
			System.out.println("Game Over");
			Victory vicScene = new Victory(this);
			this.setScene(vicScene);
			return vicScene;
		}
	
		
		
		 XYPair<Integer> coords = Game.ui.getMouseLocation();
		 if (Game.ui.mouseButtonIsPressed(0)) {
	        	int hitboxX = (int) p.getHitbox().getCenterX();
	        	int hitboxY = (int) p.getHitbox().getCenterY()-10;
	        	double d =  (int) Math.sqrt((coords.x - hitboxX)*(coords.x - hitboxX) + (coords.y - hitboxY)*(coords.y - hitboxY));
	        	Bullet nb = new Bullet(hitboxX, hitboxY,(int)((coords.x - hitboxX)/(d/10)),(int)((coords.y - hitboxY)/(d/10)), 30); 
				bullets.add(nb);
	        }
		

		 
		 
		 
		ground.draw();
		 
		for (Bullet b: bullets) {
			b.update(delta);
			b.draw();
			
		}
		
		Iterator<Bullet> bu = bullets.iterator();
		while(bu.hasNext()) {
			Bullet b = bu.next();
			if (! b.isActive()) {
				bu.remove();
			}
		}
		
		
		
		p.update(delta);
		p.draw();
		
		
		for (Tree t: trees) {
			t.update(delta);
			t.draw();
		}
		
		for (Lightning l : lightning) {
			l.update(delta);
			l.draw();
		}
		
		Iterator<Lightning> li = lightning.iterator();
		while(li.hasNext()) {
			Lightning al = li.next();
			if( ! al.isActive()) {
				li.remove();
			}
		}
		
		Text levelText = new Text(50,100,30,30, "Level: " +level);
		Text treesText = new Text(200, 50, 30, 30, "Trees on Fire: " + countBurningTrees() + "/" + trees.size());
		levelText.draw();
		treesText.draw();
		time++;
		
		
		 
		
		
		return this;
	}

	private void startNewLevel(List<Tree> trees, int level) {
		
		Sound s = new Sound("res/newLevel.wav");
		s.play();
	
		for(int i = 0; i < level *3; i++) {
			Tree t = new Tree((int)  ((rand.nextFloat()*(1024-120)) + 20), (int) ((rand.nextFloat()*(1024-120))+20), bullets);
			trees.add(t);
			
		}
		Tree aTree;
		for (int i = 0; i < level; i++) {
		
			
			Random aRand = new Random();
			
			int randInt = aRand.nextInt(trees.size());
			
			
			aTree = trees.get(randInt);
			aTree.burning();
		
			
		}
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	class Player extends GameObject {
		
		Texture t;
		Float angle;
		PointerInfo mouse;
		Point mouseLocation;
		public Player() {
			t = new Texture("res/man.png");
			
			this.hitbox.setBounds(320,240,40,40);
			
	
			
			
			
			
			
		}
		
		public void draw(){


			GL11.glPushMatrix();
			GL11.glLoadIdentity();
		
			GL11.glTranslatef((float)this.hitbox.getX() + 20,(float)this.hitbox.getY() , 0.0f);
			GL11.glRotatef(angle, 0, 0, 1);
			GL11.glTranslatef((float)-(this.hitbox.getX() +20),(float)-(this.hitbox.getY()+20) , 0.0f);
			t.draw(this);
			GL11.glEnd();
			GL11.glPopMatrix();
		
		
		
		}
		public void update(int delta) {
			mouse = MouseInfo.getPointerInfo();
			mouseLocation = mouse.getLocation();
			angle = (float) findAngle();
			
			
		
		

			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT)) {
				this.hitbox.translate(-3,0);
			} 
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT)) {
				this.hitbox.translate(3, 0);
			}
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN)) {
				this.hitbox.translate(0, 3);
			}
			if (Game.ui.keyPressed(org.lwjgl.glfw.GLFW.GLFW_KEY_UP)) {
				this.hitbox.translate(0, -3);
			}
		
		
	}
		
		private  double findAngle() {
			double mouseX = mouseLocation.getX();
			double mouseY =  mouseLocation.getY();
			double hitboxX = this.hitbox.getX() + 20;
			double hitboxY = this.hitbox.getY() + 20;
			
			double angle = Math.atan2(mouseY - hitboxY, mouseX - hitboxX);
			return Math.toDegrees(angle);
		}
		
		
		
}
	
	class Tile extends GameObject {
		Texture tile;
		
		public Tile(int x1, int y1, int x2, int y2) {
			tile = new Texture("res/grass.png");
			this.hitbox.setBounds(x1, y1, x2, y2);
		}
		
		public void draw() {
			tile.draw(this);
		}
		
	}
	
	class Ground extends GameObject {
		Tile[][] tiles;
		
		public Ground() {
			tiles = new Tile[2][2];
			
			for (int i = 0; i < 2; i ++) {
				for (int j = 0; j < 2; j++) {
					tiles[i][j] = new Tile( (512 * i), (512 * j), ((512 *i)+512), ((512*j)+ 512 ));
				}
			}
			
		}
		
		
		public void draw() {
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					tiles[i][j].draw();
				}
			}
		}
	}
	
	
	class Background extends GameObject {
		Texture bg;
		
	
	public Background() {
		bg = new Texture("res/1-1.png");
		this.hitbox.setBounds(0,0,3584,480);
		}
	
	public void draw() {

		bg.draw(this);
	}
	
	}
	
	class Bullet extends GameObject {
		int xVelocity;
		int yVelocity;
		int uptime;
		boolean fireBullet;
		int fireDamage;
		Sound s;
		public Bullet(int x, int y, int xVel, int yVel, int time) {
			this.hitbox.setSize(4,4);
			this.hitbox.setLocation(x, y);
			this.xVelocity = xVel;
			this.yVelocity = yVel;
			this.setColor(0, 0, 1);
			this.uptime = time;
			fireBullet = false;
			fireDamage = -5;
		
			//water.play();
	   
   }
		public void update(int delta) {
			
			uptime--;
			
			if (uptime < 0) {
			
				this.deactivate();}
			if (this.hitbox.getX() < 0 || this.hitbox.getX() < 0 || this.hitbox.getY() > Game.ui.getHeight() || this.hitbox.getX() > Game.ui.getWidth()) {
				this.deactivate();
			} else { 
				this.hitbox.translate(xVelocity, yVelocity);
			}
		}
    
}
	
	class Tree extends GameObject {
		
		Texture tree;
		Texture[] fire;
		Boolean burning;
		int fireLevel;
		String[] textureNames;
		int animationCount;
		int spreadCD;
		private List<Bullet> bullets;
	
		
		
		public Tree(int xCoord, int yCoord, List<Bullet> bList) {
			tree = new Texture("res/TINYTREE.png");
			this.hitbox.setBounds(xCoord, yCoord, 125,125);
			burning = false;
			fireLevel = 0;
			animationCount = 0;
			spreadCD = 60;
			fire = new Texture[40];
			bullets = bList;
			for (int i = 1; i < 39; i++ ) {
				String fileName = String.format("res/fire_1f_40_%d.png", i);
				
				fire[i-1] = new Texture(fileName);
			}
		}
	
	
		public void draw() {
			tree.draw(this);
			if(burning) {
				fire[animationCount].draw(this);
				animationCount = (animationCount+1)%38;
			}
		}
		
		public void burning() {
			burning = true;
			burningTrees++;
		}
		public void stopBurning() {
			burning = false;
			burningTrees--;
			
		}

	
	
	public void update(int delta) {
		if (burning) {
			fireLevel = Math.min(fireLevel + 1, 200);
		
			if (fireLevel > 190 && spreadCD < 0) {
				
				spread();
				spreadCD = 100;	
				}
			spreadCD--;
			}
		
		for (Bullet b : bullets) {
			if(b.intersects(this)) {
				
				
				
				
				if (b instanceof fireBullet) {
				
				fireLevel = fireLevel + 10;
				burning = true;
				burningTrees++;
				
	
			} else {
				fireLevel = fireLevel - 10;
				
			}
			}
			
		}
		if (fireLevel < 0  ) {
			burning = false;
			burningTrees--;
		}	
			
		}
		
		
		
		
	
	

	
	public void spread() {
	
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), 10,10));
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), 0,10));
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), -10,10));
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), -10,0));
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), -10,-10));
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), 0,10));
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), 0,-10));
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), 10,-10));
		bullets.add(new fireBullet((int) this.hitbox.getCenterX(), (int)this.hitbox.getCenterY(), 10,0));
	
	}
	}

	class fireBullet extends Bullet {
		Texture fire;
		Boolean fireBullet;
		int fireDamage;
		Sound s;
		public fireBullet( int x, int y, int xVel, int yVel) {
			super(x, y, xVel, yVel, 30);
			fire = new Texture("res/fire_1f_40_1.png");
			this.hitbox.setSize(100,100);
			fireBullet = true;
			s = new Sound("res/Explosion.wav");
			s.play();
			// TODO Auto-generated constructor stub
		}
		public void draw() {
			fire.draw(this);
		}
		
	}
	

	class Lightning extends GameObject {
		Texture t;
		Tree tree;
		int uptime;
		public Lightning(Tree aTree) {
		
		
			
			t = new Texture("res/bolt.png");
		
			this.tree = aTree;
			this.hitbox.setLocation((int) tree.getHitbox().getCenterX(), (int) tree.getHitbox().getCenterY());
			tree.burning = true;
			burningTrees++;
			uptime = 30;
		}
		
		public void update(int delta) {
			uptime--;
			if (uptime < 0) { 

				this.deactivate();}
		}
		
		public void draw() {

			t.draw(this);
		
		}
		
	}
	
	
	
	
}	
	
	
	
	

