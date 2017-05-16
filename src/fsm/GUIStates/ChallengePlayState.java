package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.gui.GUI;

/**
 * Created by Christopher on 16/05/2017.
 */
public class ChallengePlayState extends State {
    private GUI challengePlay;
    private GraphicsEngine graphicsEngine;

    public ChallengePlayState(GUI challengePlay, GraphicsEngine graphicsEngine) {
        this.challengePlay = challengePlay;
        this.graphicsEngine = graphicsEngine;
    }

    public void onEnter(){
        graphicsEngine.setGUI(challengePlay);
    }

    @Override
    public String onUpdate() {
        return "challengePlay";
    }

    @Override
    public String getStateName() {
        return "challengePlay";
    }
}
