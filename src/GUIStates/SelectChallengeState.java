package GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

/**
 * Created by Christopher on 18/04/2017.
 */
public class SelectChallengeState extends State{
    private GUI challenges;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener easyChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener mediumChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener hardChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();

    public SelectChallengeState(GUI challenges, GraphicsEngine graphicsEngine){
        this.challenges = challenges;
        this.graphicsEngine = graphicsEngine;
    }

    public void onEnter(){
        Button easy = challenges.getButtonById("easy");
        easy.addListener(easyChallengeButtonListener);
        Button medium = challenges.getButtonById("medium");
        medium.addListener(mediumChallengeButtonListener);
        Button hard = challenges.getButtonById("hard");
        hard.addListener(hardChallengeButtonListener);
        Button back = challenges.getButtonById("back");
        //back.addListener(backButtonListener);
        easyChallengeButtonListener.setNotClicked();
        mediumChallengeButtonListener.setNotClicked();
        hardChallengeButtonListener.setNotClicked();
        //backButtonListener.setNotClicked();
        graphicsEngine.setGUI(challenges);
        System.out.println("Switching to Select Challenge Screen");
    }

    @Override
    public String onUpdate() {
        if (easyChallengeButtonListener.isClicked()) {
            return "easyChallenge";
        }
        else if (mediumChallengeButtonListener.isClicked()) {
            return "mediumChallenge";
        }
        else if (hardChallengeButtonListener.isClicked()) {
            return "hardChallenge";
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
