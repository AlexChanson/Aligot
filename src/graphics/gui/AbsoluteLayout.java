package graphics.gui;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Created by clement on 21/03/17.
 */
public class AbsoluteLayout extends Layout<AbsoluteLayoutConstrains> {

    public void addConponent(GUIConponent conponent, AbsoluteLayoutConstrains constrains) {
        this.conponents.put(conponent, constrains);
    }

    public void addConponent(GUIConponent conponent, int x, int y, int width, int height) {
        addConponent(conponent, new AbsoluteLayoutConstrains(x, y, width, height));
    }

    public Image render(int width, int height) {
        BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = ret.getGraphics();

        AbsoluteLayoutConstrains constrains;

        int w, h;
        Image conponentRender;

        for (GUIConponent conponent : conponents.keySet()) {
            constrains = this.conponents.get(conponent);

            w = constrains.getWidth();
            h = constrains.getHeight();

            try {
                conponentRender = conponent.render(w, h);
                g.drawImage(conponentRender, constrains.getX(), constrains.getY(), null);
            } catch (Exception e) {

            }
        }

        return ret;
    }
}
