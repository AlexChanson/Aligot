package graphics.gui;

import graphics.Window;

import java.util.ArrayList;

public class Button extends GUIComponent{
    private ArrayList<ButtonGUIListener> listeners;
    private String texture;
    private String text;

    public Button (String text, int posX, int posY, int width, int height){
        listeners = new ArrayList<>();
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public Button (String texture, String text, int posX, int posY, int width, int height) {
        listeners = new ArrayList<>();
        this.texture = texture;
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public void draw() {
        if(texture != null)
            Window.drawSprite(texture, posX, posY, width, height, 0);
        else
            Window.drawRectangle(posX, posY, width, height,128, 128, 128, 0f);
        if (text != null)
            Window.drawText(text, posX, posY + this.height / 4, this.height / 2, this.width, Window.TEXT_ALIGN_LEFT, 10, 0, 100, true);
    }

    public void addListener(GUIListener guiListener){
        listeners.add(new ButtonGUIListener(this) {
            @Override
            public void execute() {
                guiListener.execute();
            }
        });
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}