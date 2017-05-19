package fsm.GUIStates;

import core.GraphicsEngine;
import core.model.Level;
import fsm.State;
import gamelauncher.Game;
import graphics.Window;
import graphics.gui.*;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class ChallengesState extends State {
    private GUI gui;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener backButtonListener = new GUIButtonListener();
    private GUIButtonListener leftListener = new GUIButtonListener();
    private GUIButtonListener rightListener = new GUIButtonListener();
    private GUIButtonListener playListener = new GUIButtonListener();
    private ArrayList<Level> levels;
    private Level currentLevel;
    Label name, info;
    private String stateName;

    public ChallengesState (GraphicsEngine graphicsEngine, ArrayList<Level> levels, int diff){
        this.graphicsEngine = graphicsEngine;
        switch (diff){
            case 1 :
                stateName = "easyChallenges";
                this.levels = levels.stream().filter(lvl -> lvl.getChallenge() != null && lvl.getChallenge().getDifficulty() == 1).collect(Collectors.toCollection(ArrayList::new));
                break;
            case 2 :
                stateName = "mediumChallenges";
                this.levels = levels.stream().filter(lvl -> lvl.getChallenge() != null && lvl.getChallenge().getDifficulty() == 2).collect(Collectors.toCollection(ArrayList::new));
                break;
            case 3 :
                stateName = "hardChallenges";
                this.levels = levels.stream().filter(lvl -> lvl.getChallenge() != null && lvl.getChallenge().getDifficulty() == 3).collect(Collectors.toCollection(ArrayList::new));
                break;
            default:
                stateName = "error";
                this.levels = levels;
        }
    }

    public void initialize() {
        name = new Label("---", 250, 300, 200, 50, "name");
        info = new Label("---", 320, 300, 200, 50, "info");
        Button left = new Button("button_left.png", "",150, 150, 50, 50, "left");
        Button right = new Button("button_left.png", "",150, 150, 50, 50, "right");
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        Button play = new Button("button_fight.png", "", 500, 500, 200, 50, "play");
        play.addListener(playListener);
        left.addListener(leftListener);
        right.addListener(rightListener);
        gui = new GUI();
        gui.addComponents(left, right, back, play, name, info);
        back.addListener(backButtonListener);
        if(levels.size() != 0){
            currentLevel = levels.get(0);
        }
    }

    public void onEnter(){
        if(levels.size() != 0){
            currentLevel = levels.get(0);
        }
        graphicsEngine.setGUI(gui);
    }

    @Override
    public String onUpdate() {
        if (currentLevel != null){
            name.setText(currentLevel.getName());
            info.setText(currentLevel.getInfo());
        }
        if(leftListener.isClicked()){
            leftListener.setNotClicked();
            try {
                currentLevel = levels.get(levels.indexOf(currentLevel) - 1);
            }catch (IndexOutOfBoundsException ignored){}
            return "challenges";
        }
        if(rightListener.isClicked()){
            rightListener.setNotClicked();
            try {
                currentLevel = levels.get(levels.indexOf(currentLevel) + 1);
            }catch (IndexOutOfBoundsException ignored){}
            return "challenges";
        }
        if(playListener.isClicked()){
            playListener.setNotClicked();
            Game.setLevel(currentLevel);
            return "challengePlay";
        }
        if (backButtonListener.isClicked()) {
            backButtonListener.setNotClicked();
            return "challengeDifficulty";
        }
        return "challenges";
    }

    @Override
    public String getStateName() {
        return stateName;
    }
}
