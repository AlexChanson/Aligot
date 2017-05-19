package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIButtonListener;
import graphics.gui.Image;

public class GameModsState extends State {
    private GUI gameMods;
    private GraphicsEngine graphicsEngine;
    private GUIButtonListener soloButtonListener = new GUIButtonListener();
    private GUIButtonListener multiButtonListener = new GUIButtonListener();
    private GUIButtonListener backButtonListener = new GUIButtonListener();
    private int width;
    private int height;

    public GameModsState(GraphicsEngine graphicsEngine){
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize(){
        gameMods = new GUI();
        width = Window.getWidth();
        height = Window.getHeight();
        int buttonWidth =(int) (Window.getWidth()*0.45), buttonHeight = (int)(Window.getHeight()*0.35);
        Image menu = new Image("menu_bg.png");
        menu.setZ(-1);
        gameMods.addComponent(menu);
        Button solo = new Button("button_solo.png", "", Window.getWidth() / 2 - buttonWidth / 2, Window.getHeight() / 4 - buttonHeight / 2, buttonWidth, buttonHeight,"solo");
        solo.addListener(soloButtonListener);
        gameMods.addComponent(solo);
        Button multi = new Button("button_multi.png", "", Window.getWidth() / 2 - buttonWidth / 2, 3 * (Window.getHeight() / 4) - buttonHeight / 2, buttonWidth, buttonHeight,"multi");
        multi.addListener(multiButtonListener);
        gameMods.addComponent(multi);
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        back.addListener(backButtonListener);
        gameMods.addComponent(back);
    }

    public void onEnter(){
        graphicsEngine.setGUI(gameMods);
    }

    @Override
    public String onUpdate() {
        if (width != Window.getWidth() || height != Window.getHeight()){
            updateGUISize();
            graphicsEngine.setGUI(gameMods);
        }
        if (soloButtonListener.isClicked()) {
            soloButtonListener.setNotClicked();
            return "challengeDifficulty";
        }
        else if (multiButtonListener.isClicked()) {
            multiButtonListener.setNotClicked();
            return "multi";
        }
        else if (backButtonListener.isClicked()) {
            backButtonListener.setNotClicked();
            return "start";
        }
        return "gameMods";
    }

    private void updateGUISize () {
        initialize();
    }

    @Override
    public String getStateName() {
        return "gameMods";
    }
}