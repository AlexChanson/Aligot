package graphics;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private long window;
    private int width;
    private int height;

    public Window (String title){
        setSize(960, 540);

        try {
            if (!glfwInit()) {
                System.exit(1);
            }
            window = glfwCreateWindow(width, height, title, 0, 0);
            glfwShowWindow(window);
            glfwMakeContextCurrent(window);
            glfwSwapInterval(1);
            GL.createCapabilities();
            glEnable(GL_TEXTURE_2D);
            //glScalef((float) 1 / this.getWidth(), (float) 1 / this.getHeight(), 1);

        } catch (IllegalStateException e) {
            System.out.println("Failed to create window");
        }
    }

    private float absToRelHeight(int h) { return h / (float) this.getHeight(); }

    private float absToRelWidth(int w) { return w / (float) this.getWidth(); }

    public void drawSprite(String fileName, int posX, int posY, float rotate, float scale){
        glPushMatrix();

        Texture texture = new Texture (System.getProperty("user.dir") + "/sprite/" + fileName);
        //float relWidth = absToRelWidth(texture.getWidth()), relHeight = absToRelHeight(texture.getHeight());
        float relWidth = texture.getWidth() / 2, relHeight = texture.getHeight() / 2;
        //float relWidth = 1f, relHeight = 1f;

        glTranslatef(posX, posY, 0);
        //glScalef(1f, 1f, 1f);
        texture.bind();

        //glScalef(1f, (float) (this.getWidth() / this.getHeight()), 1f);
        glRotatef(rotate, 0, 0f, 1f);


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

    public boolean shouldClose(){
        return !glfwWindowShouldClose(window);
    }

    public void swapBuffers(){
        glfwSwapBuffers(window);
    }

    public void setSize (int width, int height){
        this.width = width;
        this.height = height;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}