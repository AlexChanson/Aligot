package graphics.gui;

import java.awt.Image;

/**
 * Created by clement on 21/03/17.
 */
public abstract class GUIComponent {
    protected int x, y, height, width;

    protected void setSizing(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void render() throws Exception;
}
