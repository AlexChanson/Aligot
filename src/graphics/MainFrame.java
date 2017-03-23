package graphics;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Christopher on 14/03/2017.
 */

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class MainFrame {
    public static void main(String[] args) {
        Window window = new Window("Space Shooter");

        while (window.shouldClose()) {
            //window.drawSprite("alpha.png", 0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT);


            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, window.getWidth(), window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);

            window.drawSprite("rubics_cube.jpg", 480, 270, 45, 0);
            window.swapBuffers();

            glfwPollEvents();
        }
        glfwTerminate();
    }
}