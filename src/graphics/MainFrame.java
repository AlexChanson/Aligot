package graphics;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class MainFrame {
    public static void main(String[] args) {
        Window window = new Window();
        window.createWindow("Space Shooter");
        while (window.shouldClose()) {

            //window.draw("teapot.jpg", -0.5f, 0.5f);
            window.draw("teapot.jpg", 0.001f, 0);
            window.swapBuffers();
            glfwPollEvents();
        }
        glfwTerminate();
    }
}