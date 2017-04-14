package graphics.gui;

import graphics.Texture;
import graphics.Window;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Christopher on 10/04/2017.
 */
public class Button {
    private String path;
    private String text;
    private int posX;
    private int posY;
    private float scale;

    public Button (String path, String text, int posX, int posY, float scale) {
        this.path = path;
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.scale = scale;
    }

    public void drawTexture(Texture texture){
        float relWidth = texture.getWidth() / 2, relHeight = texture.getHeight() / 2;

        glEnable(GL_TEXTURE_2D);
        glPushMatrix();
        glTranslatef(posX, posY, 0);
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

    public void drawButton() {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + getPath();
        Texture texture = new Texture(path);
        drawTexture(texture);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}