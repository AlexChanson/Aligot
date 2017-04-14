package demo;

import graphics.Window;
import graphics.gui.Button;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Christopher on 14/03/2017.
 */

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class MainFrame {
    public static void main(String[] args) {
        Window.init("Space Shooter", false);
        Button button = new Button ("ecranoption_boutonaccueil.png", "MDR",100, 100, Window.getTexture("ecranoption_boutonaccueil.png").getWidth(), Window.getTexture("ecranoption_boutonaccueil.png").getHeight());
        int i = 0;

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        while (Window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);

            glViewport(0, 0, Window.getWidth(), Window.getHeight());
            glMatrixMode(GL_MODELVIEW);
            button.drawButton();

            Window.drawLine(100, 100, 300, 300, 180, 255, 128, 0);
            Window.drawRectangle(100, 200, 500 + (int) (Math.cos(i / 100.0) * 200), 540, 200, 0, 0, 0);
            Window.drawCircle(1000, 200, 100,0, i % 256, 64);
            Window.drawSprite("rubics_cube.jpg", 720, 270, 0, (float) (1.0 / (i % 250)));
            Window.drawSprite("rubics_cube.jpg", 720, 270, 128, 128, 0, 1f, 64, 64, 64, 64);
            Window.drawText("test 1\ntest 2 ut ornare urna ullamcorper nec. Donec in massa suscipit, ullamcorper orci eu, facilisis ante. Fusce eleifend eget neque ac blandit", 128, 128, 16, 10, 0, 255, 255);

            Window.swapBuffers();
            glfwPollEvents();

            i++;
        }
        glfwTerminate();
    }
}