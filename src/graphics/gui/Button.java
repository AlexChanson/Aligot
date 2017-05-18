package graphics.gui;

import graphics.Window;

import java.util.ArrayList;

public class Button extends GUIComponent{
    private ArrayList<CallBackContainer> listeners;
    private String texture;
    private String text;
    private String id;

    public Button (String text, int posX, int posY, int width, int height, String id){
        listeners = new ArrayList<>();
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public Button (String texture, String text, int posX, int posY, int width, int height, String id) {
        listeners = new ArrayList<>();
        this.texture = texture;
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public void draw() {
        if(texture != null)
            Window.drawSprite(texture, posX, posY, width, height, 0);
        else
            Window.drawRectangle(posX, posY, width, height,128, 128, 128, 0f);
        if (text != null)
            Window.drawText(text, posX, posY + this.height / 4, this.height / 2, this.width, Window.TEXT_ALIGN_CENTER, 255, 255, 255, false);
    }

    public void addListener(GUIListener guiListener){
        listeners.add(new CallBackContainer(this, guiListener));
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<CallBackContainer> getListeners() {
        return listeners;
    }
}