import edu.utc.game.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class TextDemo extends Game implements Scene {
    public static void main(String[] args)
    {
        // construct a DemoGame object and launch the game loop
        TextDemo game = new TextDemo();
        game.gameLoop();
    }

    ColorChangeText ctext;
    GrowText gtext;
    Text text;

    public TextDemo()
    {
        // inherited from the Game class, this sets up the window and allows us to access
        // Game.ui
        initUI(640, 480, "DemoGame");

        // screen clear is white (this could go in drawFrame if you wanted it to change
        glClearColor(0f, 0f, 0f, 0.0f);

        text = new Text(100,50, 30, 30, "I am normal text!");
        ctext = new ColorChangeText(100,150, 30, 30, "I am ColorChangeText!");
        gtext = new GrowText(100,250, 30, 30, "I am GrowText!");
    }

    private class GrowText extends Text
    {
        private boolean shrinking = false;
        public GrowText(int x, int y, int w, int h, String text){
            super(x,y,w,h, text);
        }

        public void update(int delta){
            float rate = 0.08f;
            if (this.w >= 45){ shrinking = true; }
            if (this.w <= 12){ shrinking = false;}
            if (shrinking)
            {
                this.w -= (int)(delta*rate);
                this.h -= (int)(delta*rate);
            }
            else
            {
                this.w += (int)(delta*rate);
                this.h += (int)(delta*rate);
            }
        }
    }

    private enum colorStates {TO_WHITE, TO_RED, TO_GREEN, TO_BLUE};

    private class ColorChangeText extends Text
    {
        colorStates color = colorStates.TO_RED;
        private boolean reddening = false;
        public ColorChangeText(int x, int y, int w, int h, String text){
            super(x,y,w,h, text);
        }

        public void update(int delta){
            float rate = 0.2f;
            switch (color){
                case TO_WHITE:
                    if (this.r >= 0.9 && this.g >= 0.9 && this.b >= 0.9){
                        this.color = colorStates.TO_RED;
                    }
                    else {
                        this.r += (delta*rate)/255f;
                        this.g += (delta*rate)/255f;
                    }
                    break;
                case TO_RED:
                    if (this.r >= 0.9 && this.g <= 0.1 && this.b <= 0.1){
                        this.color = colorStates.TO_GREEN;
                    }
                    else {
                        this.b -= (delta*rate)/255f;
                        this.g -= (delta*rate)/255f;
                    }
                    break;
                case TO_GREEN:
                    if (this.g >= 0.9 && this.r <= 0.1 && this.b <= 0.1){
                        this.color = colorStates.TO_BLUE;
                    }
                    else {
                        this.r -= (delta*rate)/255f;
                        this.g += (delta*rate)/255f;
                    }
                    break;
                case TO_BLUE:
                    if (this.b >= 0.9 && this.r <= 0.1 && this.g <= 0.1){
                        this.color = colorStates.TO_WHITE;
                    }
                    else {
                        this.b += (delta*rate)/255f;
                        this.g -= (delta*rate)/255f;
                    }
                    break;
                default:
                    color = colorStates.TO_WHITE;
                    break;
            }
        }
    }

    public Scene drawFrame(int delta) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        gtext.update(delta);
        ctext.update(delta);

        gtext.draw();
        ctext.draw();
        text.draw();
        return this;
    }
}
