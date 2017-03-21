package graphics.gui;

import java.util.HashMap;

/**
 * Created by clement on 21/03/17.
 */
public abstract class Layout<T extends LayoutConstraints> extends GUIComponent {
    protected HashMap<GUIComponent, T> conponents = new HashMap();
    public abstract void addConponent(GUIComponent conponent, T constrains) throws LayoutException;
}
