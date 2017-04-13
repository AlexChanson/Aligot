package demo;

import graphics.Window;
import graphics.gui.Button;
import graphics.gui.KeyBoardHandler;
import graphics.gui.MouseHandler;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Christopher on 14/03/2017.
 */

public class MainFrame {
    private static GLFWMouseButtonCallback mouseCallback;
    public static void main(String[] args) {
        Window.init("Space Shooter");
        Button button = new Button();
        int i = 0;
        glfwSetMouseButtonCallback(Window.getWindow(), mouseCallback = new MouseHandler());

        while (Window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            Button.drawButton("ecranoption_boutonquitter.png", 100, 100, 0.5f);

            Window.swapBuffers();
            glfwPollEvents();
            i++;
        }
        glfwTerminate();
    }
}