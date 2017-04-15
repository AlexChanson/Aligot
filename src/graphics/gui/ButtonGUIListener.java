package graphics.gui;

public abstract class ButtonGUIListener implements GUIListener{
    public int x1, x2, y1, y2;

    public ButtonGUIListener(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
}
