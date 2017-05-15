package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

public class GameModsState extends State {
    private GUI gameMods;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener soloButtonListener = new GUIButtonListener();
    private GUIButtonListener multiButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();

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
        Button back = gameMods.getButtonById("back");
        back.addListener(backButtonListener);
        soloButtonListener.setNotClicked();
        multiButtonListener.setNotClicked();
        backButtonListener.setNotClicked();
        graphicsEngine.setGUI(gameMods);
        System.out.println("Switching to Game Mods Screen");
    }

    @Override
    public String onUpdate() {
        if (soloButtonListener.isClicked()) {
            return "challengeDifficulty";
        }
        else if (multiButtonListener.isClicked()) {
            return "multi";
        }
        else if (backButtonListener.isClicked()) {
            return "start";
        }
        return "gameMods";
    }

    @Override
    public String getStateName() {
        return "gameMods";
    }
}
