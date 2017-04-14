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

    public Button (String path, int posX, int posY, int width, int height){
        this.path = path;
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

    public void drawButton() {
        if (text != null) {
            Window.drawSprite(path, posX, posY, 0, Math.min(width, height) / Window.getHeight());
        }
        else {
            Window.drawSprite(path, posX, posY, 0, Math.min(width, height) / Window.getHeight());
        }
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