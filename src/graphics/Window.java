package graphics;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

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
        glColor3f(255, 255 ,255);

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

        SpriteTexture texture = new SpriteTexture(path);

        drawTexture(texture, posX, posY, rotate, scale);
    }

    public static void drawLine(int x1, int y1, int x2, int y2, float thickness, int R, int G, int B, float rotate){
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
        glRotatef(rotate, 0, 0f, 1f);
        glColor3f(((float) R) / 255.0f, ((float) G) / 255.0f, ((float) B) / 255.0f);
        glLineWidth((float) thickness);

        glBegin(GL_LINES);
            glVertex2f(x1, y1);
            glVertex2f(x2, y2);
        glEnd();

        glPopMatrix();
    }

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

    public static void drawCircle(int posX, int posY, int radius, int R, int G, int B) {
        double perimeter = Math.PI * 2 * radius;
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

    public static long getWindow() {
        return window;
    }

    public static void exit(){
        glfwDestroyWindow(window);
        glfwTerminate();
    }
}