package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.gui.GUI;

/**
 * Created by Christopher on 16/05/2017.
 */
public class MultiPlayState extends State {
    private GUI multiPlay;
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
