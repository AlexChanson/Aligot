package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import gamelauncher.Game;
import graphics.gui.GUI;
import graphics.gui.Label;

/**
 * Created by Christopher on 16/05/2017.
 */
public class MultiPlayState extends State {
    private GUI multiPlay;
    private GraphicsEngine graphicsEngine;

    public MultiPlayState(GUI multiPlay, GraphicsEngine graphicsEngine) {
        this.multiPlay = multiPlay;
        this.graphicsEngine = graphicsEngine;
    }

    public void onEnter(){
        Label player_1 = multiPlay.getLabelById("player_1");
        player_1.setText(Game.getP1().getName());
        Label health_1 = multiPlay.getLabelById("health_1");
        health_1.setText("Health: " + Game.getP1().getHealth());
        Label weapon_1 = multiPlay.getLabelById("weapon_1");
        weapon_1.setText("Weapon: ");
        Label damage_1 = multiPlay.getLabelById("damage_1");
        damage_1.setText("Damage: ");
        Label player_2 = multiPlay.getLabelById("player_2");
        player_2.setText(Game.getP2().getName());
        Label health_2 = multiPlay.getLabelById("health_2");
        health_2.setText("Health: " + Game.getP2().getHealth());
        Label weapon_2 = multiPlay.getLabelById("weapon_2");
        weapon_2.setText("Weapon: ");
        Label damage_2 = multiPlay.getLabelById("damage_2");
        damage_2.setText("Damage: ");
        graphicsEngine.setGUI(multiPlay);
    }

    @Override
    public String onUpdate() {
        return "multiPlay";
    }

    @Override
    public String getStateName() {
        return "multiPlay";
    }
}
