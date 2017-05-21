package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.Window;
import graphics.gui.*;
import son.SoundPlayer;

/**
 * Created by Christopher on 18/04/2017.
 */
public class ChallengeDifficultyState extends State{
    private GUI selectChallenge;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener easyChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener mediumChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener hardChallengeButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();
    private int width;
    private int height;

    public ChallengeDifficultyState(GraphicsEngine graphicsEngine){
        this.graphicsEngine = graphicsEngine;
    }
    public void initialize() {
        selectChallenge = new GUI();
        int buttonWidth = (int)(Window.getWidth()*0.31), buttonHeight = (int)(Window.getHeight()*0.3);
        width = Window.getWidth();
        height = Window.getHeight();
        Image menu = new Image("menu_bg.png");
        menu.setZ(-2);
        selectChallenge.addComponent(menu);
        Label description = new Label ("Hit all targets with a minimum of bullets to reach the highest score", Window.getWidth()/2 - (int)(Window.getWidth()*0.33), Window.getHeight()/7 - (int)(Window.getHeight()*0.05),(int)(Window.getWidth()*0.70),(int)(Window.getHeight()*0.13),"description");
        selectChallenge.addComponent(description);
        Button easyChallenge = new Button("button_easy.png", "", Window.getWidth() / 6 - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "easy");
        easyChallenge.addListener(easyChallengeButtonListener);
        selectChallenge.addComponent(easyChallenge);
        Button mediumChallenge = new Button("button_medium.png", "", 3* (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "medium");
        mediumChallenge.addListener(mediumChallengeButtonListener);
        selectChallenge.addComponent(mediumChallenge);
        Button hardChallenge = new Button("button_hard.png", "", 5 * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() /2  - buttonHeight / 2, buttonWidth, buttonHeight, "hard");
        hardChallenge.addListener(hardChallengeButtonListener);
        selectChallenge.addComponent(hardChallenge);
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        back.addListener(backButtonListener);
        selectChallenge.addComponent(back);
    }

    public void onEnter(){
        graphicsEngine.setGUI(selectChallenge);
    }

    @Override
    public String onUpdate() {
        if (width != Window.getWidth() || height != Window.getHeight()){
            updateGUISize();
            graphicsEngine.setGUI(selectChallenge);
        }
        if (easyChallengeButtonListener.isClicked()){
            SoundPlayer.play("button_select01");
            easyChallengeButtonListener.setNotClicked();
            return "easyChallenges";
        }
        else if (mediumChallengeButtonListener.isClicked()){
            SoundPlayer.play("button_select01");
            mediumChallengeButtonListener.setNotClicked();
            return "mediumChallenges";
        }
        else if (hardChallengeButtonListener.isClicked()) {
            SoundPlayer.play("button_select01");
            hardChallengeButtonListener.setNotClicked();
            return "hardChallenges";
        }
        else if (backButtonListener.isClicked()) {
            backButtonListener.setNotClicked();
            SoundPlayer.play("button_back");
            return "gameMods";
        }
        return "challengeDifficulty";
    }

    private void updateGUISize () {
        initialize();
    }

    @Override
    public String getStateName() {
        return "challengeDifficulty";
    }
}
