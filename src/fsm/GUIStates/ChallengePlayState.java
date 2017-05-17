package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import gamelauncher.Game;
import graphics.gui.GUI;
import graphics.gui.Label;

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
        Label targets = challengePlay.getLabelById("targets");
        if (Game.getCurrentLevel().getChallenge().getTargets().size() != 0) {
            targets.setText("Targets: " + Game.getCurrentLevel().getChallenge().getTargets().size());
        }
        else {
            targets.setText("Targets: 0");
        }

        Label shots = challengePlay.getLabelById("shots");
        if (Game.getCurrentLevel().getChallenge().getShots() != 0) {
            shots.setText("Shots: " + Game.getCurrentLevel().getChallenge().getShots());
        }
        else {
            shots.setText("Shots: 0");
        }
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
