package GUIStates;

import fsm.State;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

/**
 * Created by Christopher on 18/04/2017.
 */
public class GameModsState extends State {
    private GUI gameMods = new GUI();
    private GUIButtonListener soloButtonListener = new GUIButtonListener();
    private GUIButtonListener multiButtonListener = new GUIButtonListener();

    @Override
    public String onUpdate() {
        if (soloButtonListener.isClicked()) {
            return "challenges";
        }
        else if (multiButtonListener.isClicked()) {
            return "multi";
        }
        return "gameMods";
    }

    @Override
    public String getStateName() {
        return "gameMods";
    }
}
