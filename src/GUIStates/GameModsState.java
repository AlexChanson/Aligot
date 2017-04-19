package GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

/**
 * Created by Christopher on 18/04/2017.
 */
public class GameModsState extends State {
    private GUI gameMods;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener soloButtonListener = new GUIButtonListener();
    private GUIButtonListener multiButtonListener = new GUIButtonListener();

    public GameModsState(GUI gameMods, GraphicsEngine graphicsEngine){
        this.gameMods = gameMods;
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize(){

    }

    public void onEnter(){
        Button solo = gameMods.getButtonById("solo");
        solo.addListener(soloButtonListener);
        Button multi = gameMods.getButtonById("multi");
        multi.addListener(multiButtonListener);
        graphicsEngine.setGUI(gameMods);
        System.out.println("Switching to Game Mode Screen");
    }

    @Override
    public String onUpdate() {
        if (soloButtonListener.isClicked()) {
            return "challenges";
        }
        else if (multiButtonListener.isClicked()) {
            return "multi";
        }
        return "gameModes";
    }

    @Override
    public String getStateName() {
        return "gameModes";
    }
}
