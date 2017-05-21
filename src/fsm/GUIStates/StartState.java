package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;
import graphics.gui.Image;
import son.SoundPlayer;

public class StartState extends State {
    private GUI start;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener beginButtonListener = new GUIButtonListener();
    private GUIButtonListener exitButtonListener = new GUIButtonListener();
    private int width;
    private int height;

    public StartState(GraphicsEngine graphicsEngine){
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize(){
        start = new GUI();
        width = Window.getWidth(); height = Window.getHeight();
        int buttonWidth = (int)(Window.getWidth()*0.3), buttonHeight = (int)(Window.getHeight()*0.20);
        Button begin = new Button("button_start.png", "", Window.getWidth() / 2 - buttonWidth / 2, 4 * (Window.getHeight() / 7) - buttonHeight /3, buttonWidth, buttonHeight,"begin");
        begin.addListener(beginButtonListener);
        start.addComponent(begin);
        Button exit = new Button("button_exit.png", "", Window.getWidth() / 2 - buttonWidth / 2, 6 * (Window.getHeight() / 7) - buttonHeight/2 , buttonWidth, buttonHeight,"exit");
        exit.addListener(exitButtonListener);
        start.addComponent(exit);
        Image logo = new Image("logo.png", Window.getWidth() /4 - (Window.getHeight()/2)/12, Window.getHeight()/7 - buttonHeight/2, 3*(Window.getWidth()/5), Window.getHeight()/2);
        logo.setZ(-1);
        start.addComponent(logo);
        Image menu = new Image("menu_bg.png");
        menu.setZ(-2);
        start.addComponent(menu);
    }

    public void onEnter(){
        graphicsEngine.setGUI(start);
        SoundPlayer.playLoop("loop_1");
    }

    @Override
    public String onUpdate() {
        if (width != Window.getWidth() || height != Window.getHeight()){
            updateGUISize();
            graphicsEngine.setGUI(start);
        }
        if(beginButtonListener.isClicked()) {
            beginButtonListener.setNotClicked();
            SoundPlayer.play("button_select01");
            return "gameMods";
        }
        else if (exitButtonListener.isClicked()) {
            exitButtonListener.setNotClicked();
            SoundPlayer.play("button_back");
            return "exit";
        }
        return "start";
    }

     private void updateGUISize () {
         initialize();
     }

    @Override
    public String getStateName() {
        return "start";
    }
}
