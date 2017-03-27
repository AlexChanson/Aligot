package graphics;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private static long window;

    public static void init(String title) {
        try {
            if (!glfwInit()) {
                System.exit(1);
            }

            window = glfwCreateWindow(getWidth(), getHeight(), title, glfwGetPrimaryMonitor(), 0);
            glfwShowWindow(window);
            glfwMakeContextCurrent(window);
            glfwSwapInterval(1);
            GL.createCapabilities();
            glEnable(GL_TEXTURE_2D);

        } catch (IllegalStateException e) {
            System.out.println("Failed to create window");
        }
    }

    public static void drawTexture(Texture texture, int posX, int posY, float rotate, float scale) {
        float relWidth = texture.getWidth() / 2, relHeight = texture.getHeight() / 2;

        glPushMatrix();

        glTranslatef(posX, posY, 0);
        glRotatef(rotate, 0, 0f, 1f);
        glScalef(scale, scale, 1);

        glBindTexture(GL_TEXTURE_2D, texture.getId());

        glBegin(GL_QUADS);
            glTexCoord2f(0, -1);
            glVertex2f(-relWidth, -relHeight);
            glTexCoord2f(0, 0);
            glVertex2f(-relWidth, relHeight);
            glTexCoord2f(-1, 0);
            glVertex2f(relWidth, relHeight);
            glTexCoord2f(-1, -1);
            glVertex2f(relWidth, -relHeight);
        glEnd();

        glPopMatrix();
    }

    public static void drawSprite(String fileName, int posX, int posY, float rotate, float scale) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, rotate, scale);
    }

    public static boolean shouldClose() {
        return !glfwWindowShouldClose(window);
    }

    public static void swapBuffers(){
        glfwSwapBuffers(window);
    }

    public static int getWidth() {
        return videoMode().width();
    }

    public static int getHeight() {
        return videoMode().height();
    }

    private static GLFWVidMode videoMode() {
        return glfwGetVideoMode(getMonitor());
    }

    private static long getMonitor() {
        return glfwGetMonitors().get(0);
    }
}