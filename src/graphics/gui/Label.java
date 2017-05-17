package graphics.gui;

import graphics.Window;

public class Label extends GUIComponent {
    private String text;
    private String id;

    public Label(String text, int posX, int posY, int width, int height, String id) {
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public void draw() {
        if (text != null)
            Window.drawText(text, posX, posY + this.height / 4, this.height / 2, this.width, Window.TEXT_ALIGN_CENTER, 255, 255, 255, false);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}