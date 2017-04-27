package GUIStates;

import core.GraphicsEngine;
import core.Level;
import fsm.State;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;
import utility.Loader;

import java.util.ArrayList;

/**
 * Created by Christopher on 18/04/2017.
 */
public class SelectChallengeState extends State{
    private GUI selectChallenge;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener easyChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener mediumChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener hardChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();

    public SelectChallengeState(GUI selectChallenge, GraphicsEngine graphicsEngine){
        this.selectChallenge = selectChallenge;
        this.graphicsEngine = graphicsEngine;
    }

    public void onEnter(){
        Button easy = selectChallenge.getButtonById("easy");
        easy.addListener(easyChallengeButtonListener);
        Button medium = selectChallenge.getButtonById("medium");
        medium.addListener(mediumChallengeButtonListener);
        Button hard = selectChallenge.getButtonById("hard");
        hard.addListener(hardChallengeButtonListener);
        Button back = selectChallenge.getButtonById("back");
        //back.addListener(backButtonListener);
        easyChallengeButtonListener.setNotClicked();
        mediumChallengeButtonListener.setNotClicked();
        hardChallengeButtonListener.setNotClicked();
        //backButtonListener.setNotClicked();
        graphicsEngine.setGUI(selectChallenge);
        System.out.println("Switching to Select Challenge Screen");
    }

    @Override
    public String onUpdate() {
        if (easyChallengeButtonListener.isClicked()){
            return "easyChallenges";
        }
        if (mediumChallengeButtonListener.isClicked()){
            return "mediumChallenges";
        }
        if (hardChallengeButtonListener.isClicked()) {
            return "hardChallenges";
        }

        //else if (backButtonListener.isClicked()) {
        //    return "gameMods";
        //}
        return "selectChallenge";
    }

    @Override
    public String getStateName() {
        return "selectChallenge";
    }
}
