package graphics.gui;

import graphics.Texture;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Christopher on 10/04/2017.
 */
public class Button {
    private String path;
    private String text;

    public Button () {
    }

    public Button (String text){
        this.text = text;
    }

    public static void drawTexture(Texture texture, int posX, int posY, float scale){
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

    public static void drawButton(String fileName, int posX, int posY, float scale) {
        String path = System.getProperty("user.dir") + "/ressources/sprites/" + fileName;
        Texture texture = new Texture(path);
        drawTexture(texture, posX, posY, scale);
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
}