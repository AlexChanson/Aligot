package fsm.GUIStates;

import core.GraphicsEngine;
import core.model.Level;
import fsm.State;
import gamelauncher.Game;
import graphics.Window;
import graphics.gui.*;
import son.SoundPlayer;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class ChallengesState extends State {
    private GUI challenges;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener backButtonListener = new GUIButtonListener();
    private GUIButtonListener leftListener = new GUIButtonListener();
    private GUIButtonListener rightListener = new GUIButtonListener();
    private GUIButtonListener playListener = new GUIButtonListener();
    private ArrayList<Level> levels;
    private Level currentLevel;
    private Label name, info;
    private Image firstStar, secondStar, thirdStar;
    private String stateName;
    private int width, height;

    public ChallengesState (GraphicsEngine graphicsEngine, ArrayList<Level> levels, int difficulty){
        this.graphicsEngine = graphicsEngine;
        switch (difficulty){
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
        width = Window.getWidth();
        height = Window.getHeight();
        firstStar = new Image("star_grey.png", (int)(Window.getWidth()*0.39),(int)(Window.getHeight()*0.05),(int)(Window.getWidth()*0.06),(int)(Window.getHeight()*0.1));
        secondStar = new Image("star_grey.png", Window.getWidth()/2 - (int)(Window.getWidth()*0.03),(int)(Window.getHeight()*0.05),(int)(Window.getWidth()*0.06),(int)(Window.getHeight()*0.1));
        thirdStar = new Image("star_grey.png", (int)(Window.getWidth()*0.55),(int)(Window.getHeight()*0.05),(int)(Window.getWidth()*0.06),(int)(Window.getHeight()*0.1));
        name = new Label("---", Window.getWidth()/2 - (int)(Window.getWidth()*0.14), (int)(Window.getHeight()*0.17), (int)(Window.getWidth()*0.31), (int)(Window.getHeight()*0.14), "name");
        info = new Label("---", Window.getWidth()/2 - (int)(Window.getWidth()*0.215), (int)(Window.getHeight()*0.35), (int)(Window.getWidth()*0.46), (int)(Window.getHeight()*0.21), "info");
        Image menu_bg = new Image("menu_bg.png");
        menu_bg.setZ(-2);
        Button left = new Button("button_left.png", "",2*(Window.getWidth()/7) - (int)(Window.getWidth()*0.02), (int)(Window.getHeight()*0.21), (int)(Window.getWidth()*0.04), (int)(Window.getHeight()*0.07), "left");
        Button right = new Button("button_right.png", "", 5*(Window.getWidth()/7) -(int)(Window.getWidth()*0.02),(int)(Window.getHeight()*0.21), (int)(Window.getWidth()*0.04), (int)(Window.getHeight()*0.07), "right");
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        Button play = new Button("button_fight.png", "", Window.getWidth()/2 - (int)(Window.getWidth()*0.10), (int)(Window.getHeight()*0.70), (int)(Window.getWidth()*0.2), (int)(Window.getHeight()*0.21), "play");
        play.addListener(playListener);
        left.addListener(leftListener);
        right.addListener(rightListener);
        challenges = new GUI();
        challenges.addComponents(left, right, back, play, name, info, menu_bg, firstStar, secondStar, thirdStar);
        back.addListener(backButtonListener);
        if(levels.size() != 0){
            currentLevel = levels.get(0);
        }
    }

    public void onEnter(){
        if(levels.size() != 0){
            currentLevel = levels.get(0);
        }
        graphicsEngine.setGUI(challenges);
    }

    @Override
    public String onUpdate() {
        if (width != Window.getWidth() || height != Window.getHeight()){
            updateGUISize();
            graphicsEngine.setGUI(challenges);
        }
        if (currentLevel != null){
            name.setText(currentLevel.getName());
            info.setText(currentLevel.getInfo());
        }
        if(leftListener.isClicked()){
            leftListener.setNotClicked();
            try {
                currentLevel = levels.get(levels.indexOf(currentLevel) - 1);
            }catch (IndexOutOfBoundsException ignored){
                currentLevel = levels.get(levels.size()-1);
            }
            return "challenges";
        }
        if(rightListener.isClicked()){
            rightListener.setNotClicked();
            try {
                currentLevel = levels.get(levels.indexOf(currentLevel) + 1);
            }catch (IndexOutOfBoundsException ignored){
                currentLevel = levels.get(0);
            }
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
    private void updateGUISize() {
        initialize();
    }

    @Override
    public String getStateName() {
        return stateName;
    }
}
