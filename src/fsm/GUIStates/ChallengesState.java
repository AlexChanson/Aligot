package fsm.GUIStates;

import core.GraphicsEngine;
import core.model.Level;
import fsm.State;
import gamelauncher.Game;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;
import utility.Challenges;
import utility.GUIBuilder;

import java.util.ArrayList;


public class ChallengesState extends State {
    private GUI challenges;
    private int difficulty;
    private GraphicsEngine graphicsEngine;
    private ArrayList<GUIButtonListener> guiButtonListeners = new ArrayList<GUIButtonListener>();
    private GUIButtonListener backButtonListener = new GUIButtonListener();
    private ArrayList<Level> levels = Challenges.get();

    public ChallengesState (GraphicsEngine graphicsEngine, int difficulty){
        this.graphicsEngine = graphicsEngine;
        this.difficulty = difficulty;
    }

    public void initialize() {
        levels.removeIf(level -> level.getChallenge().getDifficulty() != difficulty);
        if (difficulty == 1) {
            challenges = GUIBuilder.getChallenges(1);
        }
        if (difficulty == 2) {
            challenges = GUIBuilder.getChallenges(2);
        }
        if (difficulty == 3) {
            challenges = GUIBuilder.getChallenges(3);
        }
        for (int i=0; i<challenges.getComponents().size(); i++){
            guiButtonListeners.add(new GUIButtonListener());
        }
        for (int i=0; i<guiButtonListeners.size();i++) {
            Button button;
            if (difficulty == 1) {
                button = challenges.getButtonById("easy" + i);
                if (button != null) {
                    button.addListener(guiButtonListeners.get(i));
                }
            } else if (difficulty == 2) {
                button = challenges.getButtonById("medium" + i);
                if (button != null) {
                    button.addListener(guiButtonListeners.get(i));
                }
            } else if (difficulty == 3) {
                button = challenges.getButtonById("hard" + i);
                if (button != null) {
                    button.addListener(guiButtonListeners.get(i));
                }
            }
        }
    }

    public void onEnter(){
        Button back = challenges.getButtonById("back");
        back.addListener(backButtonListener);
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
        for (int i = 0; i < guiButtonListeners.size(); i++) {
            if (guiButtonListeners.get(i).isClicked()) {
                Game.setLevel(levels.get(i));
                return "challengePlay";
            }
        }
        if (backButtonListener.isClicked()) {
            backButtonListener.setNotClicked();
            return "challengeDifficulty";
        }
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
