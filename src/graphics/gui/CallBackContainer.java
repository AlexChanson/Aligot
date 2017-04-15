package graphics.gui;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Alexandre
 * A weird thing I made to store the callback code for the interface Buttons
 */
public class CallBackContainer {
    private GUIComponent guiComponent;
    private GUIListener guiListener;
    private Method run;

    public CallBackContainer(GUIComponent guiComponent, GUIListener listener) {
        this.guiComponent = guiComponent;
        this.guiListener = listener;
        try {
            this.run = listener.getClass().getMethod("execute");
            this.run.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public GUIComponent getGuiComponent() {
        return guiComponent;
    }

    public void execute() {
        try {
            run.invoke(guiListener);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
