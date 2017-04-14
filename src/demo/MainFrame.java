package demo;

import graphics.Window;
import graphics.gui.Button;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Christopher on 14/03/2017.
 */

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class MainFrame {
    public static void main(String[] args) {
        Window.init("Space Shooter");
        Button button = new Button ("earth.png", "lol", 100, 100, 0.5f);
        int i = 0;

        while (Window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);

            Button.drawButton();
            Window.drawLine(100, 100, 300, 300, 180, 255, 128, 0);
            Window.drawRectangle(100, 200, 500 + (int) (Math.cos(i / 100.0) * 200), 540, 200, 0, 0, 0);
            Window.drawCircle(1000, 200, 100,0, i % 256, 64);
            Window.drawSprite("test_grey.png", 720, 270, i, 0.2f, Color.cyan);
            Window.drawSprite("rubics_cube.jpg", 720, 270, 0, (float) (1.0 / (i % 250)));
            Window.drawSprite("rubics_cube.jpg", 720, 270, 128, 128, 0, 1f, 64, 64, 64, 64);
            Window.drawText("test 1\ntest 2", 128, 128, 16, 0, 255, 255);

            Window.swapBuffers();
            glfwPollEvents();

            i++;
        }
        glfwTerminate();
    }
}