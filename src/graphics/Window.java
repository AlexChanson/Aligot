import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private long window;
    private int width;
    private int height;

    public Window (String title){
        setSize(1920, 1080);

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

        } catch (IllegalStateException e) {
            System.out.println("Failed to create window");
        }
    }

    public void drawSprite(String fileName, int posX, int posY, float rotate, int scale){
    	glPushMatrix();

		glRotatef(0, 1, 1, 0);
        Texture texture = new Texture (System.getProperty("user.dir") + "\\sprite\\" + fileName);
        glTranslatef(2 * posX / this.getWidth(), 2 * posY / this.getHeight(), 0);
        texture.bind();

        glBegin(GL_QUADS);

        glTexCoord2f(0,1);
        glVertex2f(-0.5f,-0.5f);
        glTexCoord2f(0,0);
        glVertex2f(-0.5f,0.5f);
        glTexCoord2f(1,0);
        glVertex2f(0.5f,0.5f);
        glTexCoord2f(1,1);
        glVertex2f(0.5f,-0.5f);
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