package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
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

    public ChallengesState (GraphicsEngine graphicsEngine, int difficulty){
        this.graphicsEngine = graphicsEngine;
        this.difficulty = difficulty;
    }

    public void initialize(){
        if (difficulty == 1) {
            challenges = GUI.getChallenges(1);
        }
        if (difficulty == 2) {
            challenges = GUI.getChallenges(2);
        }
        if (difficulty == 3) {
            challenges = GUI.getChallenges(3);
        }
    }

    public void onEnter(){
        if (getStateName().equals("easyChallenges")) {
            graphicsEngine.setGUI(challenges);
        }
        else if (getStateName().equals("mediumChallenges")) {
            graphicsEngine.setGUI(challenges);
        }
        else if (getStateName().equals("hardChallenges")) {
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