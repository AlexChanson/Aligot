package GUIStates;

import fsm.State;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;

/**
 * Created by Christopher on 18/04/2017.
 */
public class StartState extends State {
    private GUI start;
    private GUIButtonListener startButtonListener = new GUIButtonListener();
    private GUIButtonListener exitButtonListener = new GUIButtonListener();

    public StartState(GUI start){
        this.start = start;
    }

    public void onEnter(){
        Button play = start.getComponents("play");
        play.addListener(startButtonListener);
        Button exit = start.getComponents("exit");
        exit.addListener(exitButtonListener);
    }

    @Override
    public String onUpdate() {
        if(startButtonListener.isClicked()) {
            System.out.println("mdr");
            return "gameMods";
        }
        else if (exitButtonListener.isClicked()) {
            return "exit";
        }
        return "start";
    }

    @Override
    public String getStateName() {
        return "start";
    }
}
