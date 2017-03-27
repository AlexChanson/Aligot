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
        Window.init("Space Shooter");

        while (Window.shouldClose()) {
            //window.drawSprite("alpha.png", 0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT);


            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);

            Window.drawSprite("rubics_cube.jpg", 240, 270, 0, 0.5f);
            Window.drawSprite("rubics_cube.jpg", 720, 270, 30, 1f);
            Window.swapBuffers();

            glfwPollEvents();
        }
        glfwTerminate();
    }
}