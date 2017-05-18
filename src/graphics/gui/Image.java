package graphics.gui;

import graphics.Window;

public class Image extends GUIComponent{
    private String textureName;
    private int x, y, width, height;

    public Image(String textureName, int x, int y, int width, int height) {
        this.textureName = textureName;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Image(String textureName) {
        this.textureName = textureName;
        width = -1;
        height = -1;
    }

    @Override
    public void draw() {
        if (width == -1 && height == -1){
            Window.drawSprite(textureName, 0, 0, Window.getWidth(), Window.getHeight(), 0f);
        }else {
            Window.drawSprite(textureName, x, y, width, height, 0f);
        }
    }
}
