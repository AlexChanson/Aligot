package fsm.GUIStates;

import core.GraphicsEngine;
import core.LevelGen;
import fsm.State;
import gamelauncher.Game;
import graphics.Window;
import graphics.gui.*;
import son.SoundPlayer;

import java.util.Random;

public class MultiState extends State {
    private GUI multi;
    private GraphicsEngine graphicsEngine;
    private int i = 2;
    private Label count;
    private GUIButtonListener rightButtonListener = new GUIButtonListener();
    private GUIButtonListener leftButtonListener = new GUIButtonListener();
    private GUIButtonListener fightButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();
    private int width;
    private int height;

    public MultiState(GraphicsEngine graphicsEngine){
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize(){
        multi = new GUI();
        width = Window.getWidth();
        height = Window.getHeight();
        int buttonWidth = (int)(Window.getWidth()*0.08), buttonHeight = (int)(Window.getHeight()*0.14);
        Image menu = new Image("menu_bg.png");
        menu.setZ(-1);
        multi.addComponent(menu);
        Label description = new Label("Select how many celestial bodies to generate", Window.getWidth()/2 - (int)(Window.getWidth()*0.33), Window.getHeight()/7, (int)(Window.getWidth()*0.7),(int)(Window.getHeight()*0.16), "description");
        multi.addComponent(description);
        Label text = new Label("Number of celestial bodies", 4*(Window.getWidth()/10) - (int)(Window.getWidth()*0.17), Window.getHeight()/2 - (int)(Window.getHeight()*0.07), (int)(Window.getWidth()*0.35), (int)(Window.getHeight()*0.14), "text");
        multi.addComponent(text);
        if (i<9) {
            count = new Label("---", 7 * (Window.getWidth() / 10) - (int) (Window.getWidth() * 0.003), Window.getHeight() / 2 - (int) (Window.getHeight() * 0.08), (int) (Window.getWidth() * 0.039), (int) (Window.getHeight() * 0.27), "count");
        }
        else {
            count = new Label("---",7*(Window.getWidth()/10) - (int)(Window.getWidth()*0.023),Window.getHeight() /2 - (int)(Window.getHeight()*0.08),(int)(Window.getWidth()*0.1), (int)(Window.getHeight()*0.27), "count");
        }
        multi.addComponent(count);
        Button right = new Button("button_right.png", "", 8*(Window.getWidth()/10) - buttonWidth / 2, Window.getHeight() /2 - buttonHeight /2 + (int)(Window.getHeight()*0.06), buttonWidth - (int)(Window.getWidth()*0.01), buttonHeight - (int)(Window.getHeight()*0.028), "right");
        right.addListener(rightButtonListener);
        multi.addComponent(right);
        Button left = new Button("button_left.png", "", 6*(Window.getWidth()/10) - buttonWidth / 2, Window.getHeight() /2 - buttonHeight/2 + (int)(Window.getHeight()*0.06), buttonWidth - (int)(Window.getWidth()*0.01), buttonHeight - (int)(Window.getHeight()*0.028),"left");
        left.addListener(leftButtonListener);
        multi.addComponent(left);
        Button fight = new Button("button_fight.png", "",Window.getWidth() - (int)(Window.getWidth()*0.15), Window.getHeight() - (int)(Window.getHeight()*0.17), (int)(Window.getWidth()*0.11), (int)(Window.getHeight()*0.1), "fight");
        fight.addListener(fightButtonListener);
        multi.addComponent(fight);
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        back.addListener(backButtonListener);
        multi.addComponent(back);
        count = multi.getLabelById("count");
        count.setText(String.valueOf(i));
    }

    public void onEnter() {
        graphicsEngine.setGUI(multi);
    }

    @Override
    public String onUpdate() {
        if (width != Window.getWidth() || height != Window.getHeight()){
            updateGUISize();
            graphicsEngine.setGUI(multi);
        }
        if (rightButtonListener.isClicked()){
            SoundPlayer.play("button_select01");
            rightButtonListener.setNotClicked();
            if (i<9){
                i += 1;
                count.setText(Integer.toString(i));
            }
            if (i>=9 && i<10) {
                i +=1;
                count.setText(Integer.toString(i));
                count.setHeight((int)(Window.getHeight()*0.27));
                count.setWidth((int)(Window.getWidth()*0.1));
                count.setPosX(7*(Window.getWidth()/10) - (int)(Window.getWidth()*0.023));
                count.setPosY(Window.getHeight() /2 - (int)(Window.getHeight()*0.08));
            }
        }
        else if (leftButtonListener.isClicked()){
            leftButtonListener.setNotClicked();
            SoundPlayer.play("button_select01");
            if(i > 2) {
                i -= 1;
                count.setText(Integer.toString(i));
            }
        }
        else if (fightButtonListener.isClicked()){
            fightButtonListener.setNotClicked();
            SoundPlayer.play("button_select01");
            int[] screenSize = {640, 360};
            LevelGen levelGen = new LevelGen(new Random().nextLong(), screenSize);
            levelGen.setPlanetNumber(i);
            Game.setLevel(levelGen.create());
            return "multiPlay";
        }
        else if (backButtonListener.isClicked()){
            backButtonListener.setNotClicked();
            SoundPlayer.play("button_back");
            return "gameMods";
        }
        return "multi";
    }

    private void updateGUISize () {
        initialize();
    }
    @Override
    public String getStateName() {
        return "multi";
    }
}
