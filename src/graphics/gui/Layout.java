package graphics.gui;

import java.util.HashMap;

/**
 * Created by clement on 21/03/17.
 */
public abstract class Layout<T extends LayoutConstrains> extends GUIConponent {
    protected HashMap<GUIConponent, T> conponents = new HashMap();
    public abstract void addConponent(GUIConponent conponent, T constrains) throws LayoutException;
}
