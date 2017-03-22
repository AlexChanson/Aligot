
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
        	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            window.drawSprite("alpha.png", 0, 0, 0, 0);
            window.drawSprite("rubics_cube.jpg", 360, 0, 0, 0);
            window.swapBuffers();
            glfwPollEvents();
        }
        glfwTerminate();
    }
}