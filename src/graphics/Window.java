package graphics;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private long window;
    private int width;
    private int height;

    public Window (){
        setSize(1920, 1080);
    }

    public void createWindow(String title) {
        try {
            if (!glfwInit()) {
                System.exit(1);
            }
            window = glfwCreateWindow(width, height, title, 0, 0);
            glfwShowWindow(window);
            glfwMakeContextCurrent(window);

        } catch (IllegalStateException e) {
            System.out.println("Failed to create window");
        }
    }

    public void draw(String fileName, float posX, float posY){
        if (!glfwInit()){
            System.exit(1);
        }
        glfwSwapInterval(1);
        GL.createCapabilities();
        Texture texture = new Texture (System.getProperty("user.dir") + "/ressources/sprites/" + fileName);
        glEnable(GL_TEXTURE_2D);
        glTranslatef(posX, posY, 0);
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            texture.bind();
            glBegin(GL_QUADS);
            glTexCoord2f(0,1);
            glVertex2f(-0.25f,-0.25f);
            glTexCoord2f(0,0);
            glVertex2f(-0.25f,0.25f);
            glTexCoord2f(1,0);
            glVertex2f(0.25f,0.25f);
            glTexCoord2f(1,1);
            glVertex2f(0.25f,-0.25f);
            glEnd();
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
