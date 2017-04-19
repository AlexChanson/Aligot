package graphics.gui;

/**
 * Created by Christopher on 18/04/2017.
 */
public class GUIButtonListener implements GUIListener{
    private boolean clicked = false;

    @Override
    public void execute() {
        clicked = true;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setNotClicked(){
        clicked = false;
    }
}
