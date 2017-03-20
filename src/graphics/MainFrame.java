package graphics;

import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Christopher on 14/03/2017.
 */
public class MainFrame {
    public static void main(String[] args) {

        if (glfwInit() != true){
            System.exit(1);
        }

        long win = glfwCreateWindow(1000, 1000, "First frame 1.0",0,0);
        glfwShowWindow(win);
        glfwMakeContextCurrent(win);

        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        Texture texture = new Texture ("C:/Users/Christopher/IdeaProjects/jeugraphique/sprites/game_over.png");
        while( glfwWindowShouldClose(win) != true){
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);
            texture.bind();
            glBegin(GL_QUADS);
                glTexCoord2f(0,0);
                glVertex2f(-1f,1f);
                glTexCoord2f(0,1);
                glVertex2f(1f,1f);
                glTexCoord2f(1,1);
                glVertex2f(1f,-1f);
                glTexCoord2f(1,0);
                glVertex2f(-1f,-1f);
            glEnd();
            glfwSwapBuffers(win);
        }

        glfwTerminate();
    }
}
