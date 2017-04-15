package graphics.gui;

import graphics.Window;

/**
 * Created by Christopher on 10/04/2017.
 */
public class Button {
    private String path;
    private String text;
    private int posX;
    private int posY;
    private int width;
    private int height;

    public Button (String text, int posX, int posY, int width, int height){
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public Button (String path, String text, int posX, int posY, int width, int height) {
        this.path = path;
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public void draw() {
        if(path != null)
            Window.drawSprite(path, posX, posY, width, height, 0);
        else
            Window.drawRectangle(posX, posY, width, height, 128, 128, 128, 0f);
        if (text != null)
            Window.drawText(text, posX, posY, height, 16, Window.TEXT_ALIGN_LEFT, 10, 0, 100);
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