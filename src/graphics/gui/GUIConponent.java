package graphics.gui;

import java.awt.Image;

/**
 * Created by clement on 21/03/17.
 */
public abstract class GUIConponent {
    public abstract Image render(int width, int height) throws Exception;
}
