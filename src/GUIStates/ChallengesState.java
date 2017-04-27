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
    private int difficulty;
    private GraphicsEngine graphicsEngine;
    private ArrayList<GUIButtonListener> guiButtonListeners = new ArrayList<GUIButtonListener>();

    public ChallengesState (GUI challenges, GraphicsEngine graphicsEngine, int difficulty){
        this.challenges = challenges;
        this.graphicsEngine = graphicsEngine;
        this.difficulty = difficulty;
    }

    public void onEnter(){
        if (getStateName() == "easyChallenges") {
            graphicsEngine.setGUI(challenges);
        }
        else if (getStateName() == "mediumChallenges") {
            graphicsEngine.setGUI(challenges);
        }
        else if (getStateName() == "hardChallenges") {
            graphicsEngine.setGUI(challenges);
        }
        else {
            graphicsEngine.setGUI(challenges);
        }
    }

    @Override
    public String onUpdate() {
        return "challenges";
    }

    @Override
    public String getStateName() {
        if (difficulty == 1) {
            return "easyChallenges";
        }
        else if (difficulty == 2) {
            return "mediumChallenges";
        }
        else if (difficulty == 3) {
            return "hardChallenges";
        }
        else {
            return "challenges";
        }


    }
}
