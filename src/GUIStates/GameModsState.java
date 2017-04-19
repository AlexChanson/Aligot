package GUIStates;

import fsm.State;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

/**
 * Created by Christopher on 18/04/2017.
 */
public class GameModsState extends State {
    private GUI gameMods;
    private GUIButtonListener soloButtonListener = new GUIButtonListener();
    private GUIButtonListener multiButtonListener = new GUIButtonListener();

    public GameModsState(GUI gameMods){
        this.gameMods = gameMods;
    }

    public void onEnter(){
        GUI.getGameMods().getSolo().addListener(soloButtonListener);
        GUI.getGameMods().getMulti().addListener(multiButtonListener);
    }

    @Override
    public String onUpdate() {
        if (soloButtonListener.isClicked()) {
            return "challenges";
        }
        else if (multiButtonListener.isClicked()) {
            return "multi";
        }
        return "gameMods";
    }

    @Override
    public String getStateName() {
        return "gameMods";
    }
}
