package graphics.gui;

/**
 * @author Alexandre
 */
public class CallBackContainer {
    private GUIComponent guiComponent;
    private GUIListener guiListener;

    public CallBackContainer(GUIComponent guiComponent, GUIListener listener) {
        this.guiComponent = guiComponent;
        this.guiListener = listener;
    }

    public GUIComponent getGuiComponent() {
        return guiComponent;
    }

    public void execute() {
        guiListener.execute();
    }
}
