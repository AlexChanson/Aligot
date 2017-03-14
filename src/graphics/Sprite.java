package graphics;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Christopher on 13/03/2017.
 */
public class Sprite extends VisualObject {
    private Texture texture;
    private int height;
    private int width;

    public void drawSprite(int posX, int posY){
        glPushMatrix();
        texture.bind();
        glTranslatef(posX, posY, 0);
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);
            glTexCoord2f(0, texture.getHeight());
            glVertex2f(0, height);
            glTexCoord2f(texture.getWidth(), texture.getHeight());
            glVertex2f(width, height);
            glTexCoord2f(texture.getWidth(), 0);
            glVertex2f(width, 0);
        }
        glEnd();
        glPopMatrix();
        }

    public Texture getTexture() {
        return texture;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
