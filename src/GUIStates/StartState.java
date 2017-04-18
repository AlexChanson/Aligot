package GUIStates;

import fsm.State;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

/**
 * Created by Christopher on 18/04/2017.
 */
public class StartState extends State {
    private GUI start;
    private GUIButtonListener startButtonListener = new GUIButtonListener();
    private GUIButtonListener exitButtonListener = new GUIButtonListener();

    public StartState(GUI start){
        this.start = start;
    }

    @Override
    public String onUpdate() {
        if(startButtonListener.isClicked()) {
            return "gameMods";
        }
        else if (exitButtonListener.isClicked()) {
        }
        return "start";
    }

    @Override
    public String getStateName() {
        return "start";
    }
}
