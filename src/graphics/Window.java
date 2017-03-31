package graphics;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Window in which the game will be played
 * The library is LWJGL (GLFW)
 * Window is a canvas in which we draw our game
 * @author Christopher VALLOT
 */
public class Window {
    private static long window;

    /**
     * Initializes the window
     * @param title
     */
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

    /**
     * Draws a texture that will contain a sprite
     * @param texture the texture
     * @param posX the position x
     * @param posY the position y
     * @param rotate the degree of rotation
     * @param scale the scale parameter
     */
    public static void drawTexture(Texture texture, int posX, int posY, float rotate, float scale) {
        float relWidth = texture.getWidth() / 2, relHeight = texture.getHeight() / 2;

        glEnable(GL_TEXTURE_2D);
        glPushMatrix();

        glTranslatef(posX, posY, 0);
        glRotatef(rotate, 0, 0f, 1f);
        glScalef(scale, scale, 1);
        glColor3f(1, 1 ,1);

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
        glDisable(GL_TEXTURE_2D);
    }

    /**
     * Draws a sprite in the window
     * @param fileName the name of the file
     * @param posX the position x
     * @param posY the position y
     * @param rotate the degree of rotation
     * @param scale the scale parameter
     */
    public static void drawSprite(String fileName, int posX, int posY, float rotate, float scale) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        SpriteTexture texture = new SpriteTexture(path);

        drawTexture(texture, posX, posY, rotate, scale);
    }

    /**
     * Draws a line in the window
     * @param x1 the first position x
     * @param y1 the first position y
     * @param x2 the second position x
     * @param y2 the second position y
     * @param thickness the thickness of the line
     * @param R the proportion of red
     * @param G the proportion of green
     * @param B the proportion of blue
     * @param rotate the degree of rotation
     */
    public static void drawLine(int x1, int y1, int x2, int y2, float thickness, int R, int G, int B){
        int minX, minY;

        minX = Math.min(x1, x2);
        minY = Math.min(y1, y2);

        x1 -= minX;
        x2 -= minX;
        y1 -= minY;
        y2 -= minY;

        glPushMatrix();

        glBindTexture(0, 0);
        glTranslatef(minX, minY, 0);
        glColor3f(((float) R) / 255.0f, ((float) G) / 255.0f, ((float) B) / 255.0f);
        glLineWidth(thickness);

        glBegin(GL_LINES);
            glVertex2f(x1, y1);
            glVertex2f(x2, y2);
        glEnd();

        glPopMatrix();
    }

    /**
     * Draws a rectangle in the window
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     * @param posX the position x
     * @param posY the position y
     * @param R the proportion of red
     * @param G the proportion of green
     * @param B the proportion of blue
     * @param rotate the degree of rotation
     */
    public static void drawRectangle(int width, int height, int posX, int posY, int R, int G, int B, float rotate){
        glPushMatrix();

        glBindTexture(0, 0);
        glTranslatef(posX, posY, 0);
        glColor3f(((float) R) / 255.0f, ((float) G) / 255.0f, ((float) B) / 255.0f);

        glBegin(GL_QUADS);
            glVertex2f(-width, -height);
            glVertex2f(-width, height);
            glVertex2f(width, height);
            glVertex2f(width, -height);
        glEnd();

        glPopMatrix();
    }

    /**
     * Draws a circle in the window
     * @param posX the position x
     * @param posY the position y
     * @param radius the radius parameter
     * @param R the proportion of red
     * @param G the proportion of green
     * @param B the proportion of blue
     */
    public static void drawCircle(int posX, int posY, int radius, int R, int G, int B) {
        double a = 1.0 / radius;

        glPushMatrix();

        glBindTexture(0, 0);
        glTranslatef(posX, posY, 0);
        glColor3f(((float) R) / 255.0f, ((float) G) / 255.0f, ((float) B) / 255.0f);

        glBegin(GL_POLYGON);

        for (double i = -Math.PI; i < Math.PI; i += a) {
            glVertex2f((float) (Math.cos(i) * radius), (float) (Math.sin(i) * radius));
        }

        glEnd();

        glPopMatrix();
    }

    /**
     * @return true if the window should close or false otherwise
     */
    public static boolean shouldClose() {
        return !glfwWindowShouldClose(window);
    }

    /**
     * swaps the front buffer of the window with the back buffer
     * it is necessary step to make render with OpenGL
     * use this method at the end of the main loop
     */
    public static void swapBuffers(){
        glfwSwapBuffers(window);
    }

    /**
     * @return the width of the window
     */
    public static int getWidth() {
        return videoMode().width();
    }


    /**
     * @return the height of the window
     */
    public static int getHeight() {
        return videoMode().height();
    }

    private static GLFWVidMode videoMode() {
        return glfwGetVideoMode(getMonitor());
    }

    private static long getMonitor() {
        return glfwGetMonitors().get(0);
    }


    /**
     * @return the window id
     */
    public static long getWindow() {
        return window;
    }

    /**
     * close the window
     */
    public static void exit(){
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}