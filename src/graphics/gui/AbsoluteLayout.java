package graphics.gui;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Created by clement on 21/03/17.
 */
public class AbsoluteLayout extends Layout<AbsoluteLayoutConstraints> {

    public void addConponent(GUIComponent conponent, AbsoluteLayoutConstraints constrains) {
        conponent.setSizing(constrains.getX() + this.x, constrains.getY() + this.y, constrains.getWidth(), constrains.getHeight());

        this.conponents.put(conponent, constrains);
    }

    public void addConponent(GUIComponent conponent, int x, int y, int width, int height) {
        addConponent(conponent, new AbsoluteLayoutConstraints(x, y, width, height));
    }

    public void render() {

    }
}
