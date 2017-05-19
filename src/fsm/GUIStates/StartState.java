package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;
import utility.GUIBuilder;

public class StartState extends State {
    private GUI start;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener startButtonListener = new GUIButtonListener();
    private GUIButtonListener exitButtonListener = new GUIButtonListener();
    private int height, width;

    public StartState(GUI start, GraphicsEngine graphicsEngine){
        this.start = start;
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize(){
        height = Window.getHeight();
        width = Window.getWidth();
        Button play = start.getButtonById("play");
        play.addListener(startButtonListener);
        Button exit = start.getButtonById("exit");
        exit.addListener(exitButtonListener);
    }

    public void onEnter(){
        graphicsEngine.setGUI(start);
    }

    @Override
    public String onUpdate() {
        if (width != Window.getWidth() || height != Window.getHeight()){
            updateGUISize();
        }
        if(startButtonListener.isClicked()) {
            startButtonListener.setNotClicked();
            return "gameMods";
        }
        else if (exitButtonListener.isClicked()) {
            exitButtonListener.setNotClicked();
            return "exit";
        }
        return "start";
    }

    private void updateGUISize() {
        height = Window.getHeight();
        width = Window.getWidth();
        start = GUIBuilder.getStart();
        Button play = start.getButtonById("play");
        play.addListener(startButtonListener);
        Button exit = start.getButtonById("exit");
        exit.addListener(exitButtonListener);
        graphicsEngine.setGUI(start);
    }

    @Override
    public String getStateName() {
        return "start";
    }
}
