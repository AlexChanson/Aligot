package graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Window in which the game will be played
 * Using lwjgl as a wrapper around opengl
 * The window contains all game elements render using opengl
 * @author Christopher VALLOT
 */
public class Window {
    private static long window;
    private static int monitor = 0, width = 1024, height = 640;
    private static boolean fullscreenEnabled;
    private static HashMap<Character, Integer> charWidth;

    /**
     * Initializes the window
     * @param title the window title (not visible in fullscreen)
     */
    public static void init(String title, boolean fullscreen) {
        fullscreenEnabled = fullscreen;

        try {
            if (!glfwInit()) {
                System.exit(1);
            }

            window = glfwCreateWindow(getWidth(), getHeight(), title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);

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
     * @param width
     * @param height
     * @param rotate the degree of rotation
     * @param scale the scale parameter
     * @param textureX
     * @param textureY
     * @param textureWidth
     * @param textureHeight
     */
    public static void drawTexture(Texture texture, float posX, float posY, float width, float height, float rotate, float scale, float textureX, float textureY, float textureWidth, float textureHeight, int r, int g, int b) {
        glEnable(GL_TEXTURE_2D);
        glPushMatrix();

        glTranslatef(posX, posY, 0);
        glRotatef(rotate, 0, 0f, 1f);
        glScalef(scale, scale, 1);
        glColor3f(((float) r) / 255.0f, ((float) g) / 255.0f, ((float) b) / 255.0f);

        glBindTexture(GL_TEXTURE_2D, texture.getId());

        glBegin(GL_QUADS);
        glTexCoord2f(textureX / texture.getWidth(), textureY / texture.getHeight());
        glVertex2f(-0.5f * width, -0.5f * height);
        glTexCoord2f((textureX + textureWidth) / texture.getWidth(), textureY / texture.getHeight());
        glVertex2f(0.5f * width, -0.5f * height);
        glTexCoord2f((textureX + textureWidth) / texture.getWidth(), (textureY + textureHeight) / texture.getHeight());
        glVertex2f(0.5f * width, 0.5f * height);
        glTexCoord2f( textureX / texture.getWidth(), (textureY + textureHeight) / texture.getHeight());
        glVertex2f(-0.5f * width, 0.5f * height);
        glEnd();

        glPopMatrix();
        glDisable(GL_TEXTURE_2D);
    }

    /**
     * Draws a sprite in the window
     * @param fileName the name of the file
     * @param posX the position on the x axis
     * @param posY the position on the y axis
     * @param width
     * @param height
     * @param rotate the degree of rotation
     * @param scale the scale parameter
     * @param textureX
     * @param textureY
     * @param textureWidth
     * @param textureHeight
     */
    public static void drawSprite(String fileName, float posX, float posY, float width, float height, float rotate, float scale, float textureX, float textureY, float textureWidth, float textureHeight) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, width, height, rotate, scale, textureX, textureY, textureWidth, textureHeight, 255, 255, 255);
    }

    public static void drawSprite(String fileName, float posX, float posY, float width, float height, float rotate) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, width, height, rotate, 1f, 0, 0, texture.getWidth(), texture.getHeight(), 255, 255, 255);
    }

    public static void drawSprite(String fileName, float posX, float posY, float rotate, float scale) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, texture.getWidth(), texture.getHeight(), rotate, scale, 0, 0, texture.getWidth(), texture.getHeight(), 255, 255, 255);
    }

    public static void drawSprite(String fileName, float posX, float posY, float width, float height, float scale, float textureX, float textureY, float textureWidth, float textureHeight) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path);

        drawTexture(texture, posX, posY, width, height,0, scale, textureX, textureY, textureWidth, textureHeight, 255, 255, 255);
    }

    /*public static void drawSprite(String fileName, int posX, int posY, float rotate, float scale, Color tint) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;

        Texture texture = new Texture(path, tint);

        drawTexture(texture, posX, posY, texture.getWidth(), texture.getHeight(), rotate, scale, 0, 0, texture.getWidth(), texture.getHeight(), 255, 255, 255);
    }*/

    /**
     * Draws a line in the window
     * @param x1 the first position x
     * @param y1 the first position y
     * @param x2 the second position x
     * @param y2 the second position y
     * @param thickness the thickness of the line
     * @param R the proportion of red (0 to 255)
     * @param G the proportion of green (0 to 255)
     * @param B the proportion of blue (0 to 255)
     */
    public static void drawLine(float x1, float y1, float x2, float y2, float thickness, int R, int G, int B){
        float minX, minY;

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
    public static void drawRectangle(float width, float height, float posX, float posY, int R, int G, int B, float rotate){
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
    public static void drawCircle(float posX, float posY, float radius, int R, int G, int B) {
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

    public static void drawText(String text, float x, float y, float size, int maxLineOffset, int r, int g, int b) {
        if (maxLineOffset > 0) {
            String path = System.getProperty("user.dir") + "/ressources/font/verdana.png";

            Texture texture = new Texture(path);

            ArrayList<String> words = new ArrayList<>();
            String word, substr1, substr2;
            boolean charrReturn = false;
            byte code;
            int indexOf, lineOffset = 0, line = 0;

            words.addAll(Arrays.asList(text.split(" ")));

            for (int i = 0; i < words.size(); i++) {
                word = words.get(i);

                if (word.length() + lineOffset >= maxLineOffset && lineOffset > 0 || charrReturn) {
                    line++;
                    lineOffset = 0;
                    charrReturn = false;
                }

                indexOf = word.indexOf("\n");

                if (indexOf != -1 || word.length() >= maxLineOffset) {
                    if (indexOf == -1) {
                        substr1 = word.substring(0, maxLineOffset);
                        substr2 = word.substring(maxLineOffset);
                    }
                    else {
                        substr1 = word.substring(0, indexOf);
                        substr2 = word.substring(indexOf + 1);

                        charrReturn = true;
                    }

                    words.set(i, substr1);
                    words.add(i + 1, substr2);
                    word = substr1;
                }

                for (char c : word.toCharArray()) {
                    code = (byte) c;

                    if (code >= 32 && code < 128 || code >= 160) {
                        drawTexture(texture, x + lineOffset * size, y + line * size, size, size, 0f, 1f, (code % 16) * 256, ((int) (code / 16)) * 256, 256, 256, r, g, b);
                        lineOffset++;
                    }
                }

                lineOffset++;
            }
        }
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
        if (fullscreenEnabled) {
            return videoMode().width();
        }
        else if (window != 0) {
            IntBuffer w = BufferUtils.createIntBuffer(1);

            glfwGetWindowSize(window, w, null);

            return w.get();
        }
        else {
            return width;
        }
    }


    /**
     * @return the height of the window
     */
    public static int getHeight() {
        if (fullscreenEnabled) {
            return videoMode().height();
        }
        else if (window != 0) {
            IntBuffer h = BufferUtils.createIntBuffer(1);

            glfwGetWindowSize(window, null, h);

            return h.get();
        }
        else {
            return height;
        }
    }

    public static void setWidth(int w) {
        width = w;

        if (!fullscreenEnabled) {
            refreshWindowSize();
        }
    }

    public static void setHeight(int h) {
        height = h;

        if (!fullscreenEnabled) {
            refreshWindowSize();
        }
    }

    private static void refreshWindowSize() {
        glfwSetWindowSize(window, width, height);
    }

    private static GLFWVidMode videoMode() {
        return glfwGetVideoMode(getMonitorId());
    }

    private static long getMonitorId() {
        return glfwGetMonitors().get(monitor);
    }

    public static void setMonitor(int m) {
        if (m != monitor && monitorAvailable(m)) {
            monitor = m;

            if (fullscreenEnabled) {
                glfwSetWindowMonitor(window, getMonitorId(), 0, 0, getWidth(), getHeight(), 0);
            }
        }
    }

    public static int getMonitor() {
        return monitor;
    }

    public static int countMonitors() {
        return glfwGetMonitors().limit();
    }

    public static boolean monitorAvailable(int m) {
        return m >= 0 && m < countMonitors();
    }

    public static Texture getTexture(String fileName){
        return Texture.getTexture(System.getProperty("user.dir") + "/ressources/sprites/" + fileName);
    }

    public static void enableFullscreen() {
        fullscreenEnabled = true;

        glfwSetWindowMonitor(window, getMonitorId(), 0, 0, getWidth(), getHeight(), 0);
    }

    public static void disableFullscreen() {
        fullscreenEnabled = false;

        glfwSetWindowMonitor(window, 0, 0, 0, width, height, 0);
    }

    public static boolean fullscreenEnabled() {
        return fullscreenEnabled;
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