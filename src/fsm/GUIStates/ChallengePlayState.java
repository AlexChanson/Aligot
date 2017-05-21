package fsm.GUIStates;

import core.GraphicsEngine;
import core.systems.ChallengeSubSystem;
import core.systems.SubSystem;
import fsm.State;
import gamelauncher.Game;
import graphics.Window;
import graphics.gui.GUI;
import graphics.gui.Label;
import son.SoundPlayer;

public class ChallengePlayState extends State {
    private GUI challengePlay;
    private GraphicsEngine graphicsEngine;
    private Label targets;
    private Label shots;
    private int width;
    private int height;

    public ChallengePlayState(GraphicsEngine graphicsEngine) {
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize(){
        challengePlay = new GUI();
        width = Window.getWidth();
        height = Window.getHeight();
        int labelWidth = (int)(Window.getWidth()*0.12), labelHeight =(int)(Window.getHeight()*0.05);
        targets = new Label("Targets: ", 10, 10, labelWidth, labelHeight, "targets");
        challengePlay.addComponent(targets);
        shots = new Label("Shots: ", 10, 50, labelWidth, labelHeight, "shots");
        challengePlay.addComponent(shots);
    }
    public void onEnter(){
        graphicsEngine.setGUI(challengePlay);
        SoundPlayer.stopLoop("loop_1");
        SoundPlayer.playLoop("loop_3");
    }

    @Override
    public void onExit() {
        SoundPlayer.stopLoop("loop_3");
        if (Game.getEngine() != null && Game.getCurrentLevel() != null && Game.getCurrentLevel().getChallenge() != null) {
            ChallengeSubSystem challengeSubSystem = null;
            for (SubSystem subSystem : Game.getEngine().getSystems()) {
                if (subSystem instanceof ChallengeSubSystem)
                    challengeSubSystem = (ChallengeSubSystem) subSystem;
            }
            if (challengeSubSystem != null) {
                Game.getProgression().addScore(Game.getP1(), Game.getCurrentLevel(), challengeSubSystem.getScore());
            }
        }
    }

    @Override
    public String onUpdate() {
        if (width != Window.getWidth() || height != Window.getHeight()){
            updateGUISize();
            graphicsEngine.setGUI(challengePlay);
        }
        targets = challengePlay.getLabelById("targets");
        if (Game.getCurrentLevel().getChallenge().getTargets().size() != 0) {
            targets.setText("Targets: " + Game.getCurrentLevel().getChallenge().getTargets().size());
        }
        else {
            targets.setText("Targets: 0");
        }

        shots = challengePlay.getLabelById("shots");
        if (Game.getCurrentLevel().getChallenge().getShots() != 0) {
            shots.setText("Shots: " + Game.getCurrentLevel().getChallenge().getShots());
        }
        else {
            shots.setText("Shots: 0");
        }
        return "challengePlay";
    }

    private void updateGUISize () {
        initialize();
    }

    @Override
    public String getStateName() {
        return "challengePlay";
    }
}
