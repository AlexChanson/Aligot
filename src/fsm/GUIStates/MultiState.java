package fsm.GUIStates;

import core.GraphicsEngine;
import core.LevelGen;
import fsm.State;
import gamelauncher.Game;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;
import graphics.gui.Label;

import java.util.Random;

public class MultiState extends State {
    private GUI multi;
    private GraphicsEngine graphicsEngine;
    private int i = 2;
    private Label count;
    private GUIButtonListener upButtonListener = new GUIButtonListener();
    private GUIButtonListener downButtonListener = new GUIButtonListener();
    private GUIButtonListener fightButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();

    public MultiState(GUI multi, GraphicsEngine graphicsEngine){
        this.multi = multi;
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize(){

    }

    public void onEnter() {
        Button up = multi.getButtonById("up");
        up.addListener(upButtonListener);
        Button down = multi.getButtonById("down");
        down.addListener(downButtonListener);
        Button fight = multi.getButtonById("fight");
        fight.addListener(fightButtonListener);
        Button back = multi.getButtonById("back");
        back.addListener(backButtonListener);
        count = multi.getLabelById("count");
        upButtonListener.setNotClicked();
        downButtonListener.setNotClicked();
        fightButtonListener.setNotClicked();
        backButtonListener.setNotClicked();
        graphicsEngine.setGUI(multi);
    }

    @Override
    public String onUpdate() {
        if (upButtonListener.isClicked()){
            i+=1;
            count.setText(Integer.toString(i));
            upButtonListener.setNotClicked();
        }
        else if (downButtonListener.isClicked()){
            i-=1;
            count.setText(Integer.toString(i));
            downButtonListener.setNotClicked();
        }
        else if (fightButtonListener.isClicked()){
            LevelGen levelGen = new LevelGen(new Random().nextLong(), LevelGen.SMALL);
            levelGen.setPlanetNumber(i);
            Game.setLevel(levelGen.create());
            return "multiPlay";
        }
        else if (backButtonListener.isClicked()){
            return "gameMods";
        }
        return "multi";
    }

    @Override
    public String getStateName() {
        return "multi";
    }
}
