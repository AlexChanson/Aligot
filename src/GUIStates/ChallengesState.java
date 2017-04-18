package GUIStates;

import fsm.State;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

/**
 * Created by Christopher on 18/04/2017.
 */
public class ChallengesState extends State{
    private GUI challenges = new GUI();
    private GUIButtonListener challenge1ButtonListener = new GUIButtonListener();
    private GUIButtonListener challenge2ButtonListener = new GUIButtonListener();
    private GUIButtonListener challenge3ButtonListener = new GUIButtonListener();
    private GUIButtonListener challenge4ButtonListener = new GUIButtonListener();

    @Override
    public String onUpdate() {
        if (challenge1ButtonListener.isClicked()) {
            return "challenge1";
        }
        else if (challenge2ButtonListener.isClicked()) {
            return "challenge2";
        }
        else if (challenge3ButtonListener.isClicked()) {
            return "challenge3";
        }
        else if (challenge4ButtonListener.isClicked()) {
            return "challenge4";
        }
        return "challenges";
    }

    @Override
    public String getStateName() {
        return "challenges";
    }
}
