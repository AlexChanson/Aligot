package fsm.GUIStates;

import core.model.Player;
import fsm.State;
import gamelauncher.Game;
import graphics.Window;
import graphics.gui.*;
import son.SoundPlayer;

public class MultiWinState extends State {
    private GUIButtonListener backButtonListener = new GUIButtonListener();
    private Player player;
    private GUI gui;
    private int width, height;

    public MultiWinState(Player player) {
        width = Window.getWidth();
        height = Window.getHeight();
        this.player = player;
        gui = new GUI();

        Button back = new Button("button_back.png","", (int)(width*0.03), height - (int)(height*0.12), (int)(width*0.08),(int)(height*0.07), "back");
        back.addListener(backButtonListener);
        gui.addComponent(back);

        Label victory = new Label(player.getName() + " Wins !", width/2-400, height/2-350, 1000, 500, "victory");
        gui.addComponent(victory);

        Image victory_bg = new Image("menu_bg.png");
        gui.addComponent(victory_bg);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void onEnter() {
        Game.changeGUI(gui);
    }

    @Override
    public void onExit() {

    }

    @Override
    public String onUpdate() {
        if (backButtonListener.isClicked()) {
            backButtonListener.setNotClicked();
            SoundPlayer.play("button_back");
            return "multi";
        }
        return "multiWin";
    }

    @Override
    public String getStateName() {
        return "multiWin_" + (player != null ? player.getName() : "null");
    }
}
