package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.gui.GUI;
import graphics.gui.Label;

/**
 * Created by Christopher on 16/05/2017.
 */
public class MultiPlayState extends State {
    private GUI multiPlay;
    private Label health;
    private Label playerName;
    private Label weaponName;
    private Label weaponDamage;
    private GraphicsEngine graphicsEngine;

    public MultiPlayState(GUI multiPlay, GraphicsEngine graphicsEngine) {
        this.multiPlay = multiPlay;
        this.graphicsEngine = graphicsEngine;
    }

    public void onEnter(){
        graphicsEngine.setGUI(multiPlay);
    }

    @Override
    public String onUpdate() {
        return "multiPlay";
    }

    @Override
    public String getStateName() {
        return "multiPlay";
    }
}
