package GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

import java.util.ArrayList;

/**
 * Created by Christopher on 25/04/2017.
 */
public class ChallengesState extends State {
    private GUI challenges;
    private GraphicsEngine graphicsEngine;
    private ArrayList<GUIButtonListener> guiButtonListeners = new ArrayList<GUIButtonListener>();

    public ChallengesState (GUI challenges, GraphicsEngine graphicsEngine){
        this.challenges = challenges;
        this.graphicsEngine = graphicsEngine;
    }

    public void onEnter(){
        graphicsEngine.setGUI(challenges);
    }

    @Override
    public String onUpdate() {
        return "challenges";
    }

    @Override
    public String getStateName() {
        return "challenges";
    }
}
