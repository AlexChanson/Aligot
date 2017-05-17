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
        Label health_1 = multiPlay.getLabelById("health_1");
        if (Game.getP1() != null) {
            player_1.setText(Game.getP1().getName());
            health_1.setText("Health: " + Game.getP1().getHealth());
        }
        else {
            player_1.setText("Mort");
            health_1.setText("Health: 0");
        }

        Label weapon_1 = multiPlay.getLabelById("weapon_1");
        Label damage_1 = multiPlay.getLabelById("damage_1");
        if (Game.getP1().getCurrentWeapon() != null) {
            weapon_1.setText(Game.getP1().getCurrentWeapon().getName());
            damage_1.setText("Damage: " + Game.getP1().getCurrentWeapon().getDamageRange());
        }
        else {
            weapon_1.setText("Bare Hands");
            damage_1.setText("Damage: 0");
        }


        Label player_2 = multiPlay.getLabelById("player_2");
        Label health_2 = multiPlay.getLabelById("health_2");
        if(Game.getP2() != null) {
            player_2.setText(Game.getP2().getName());
            health_2.setText("Health: " + Game.getP2().getHealth());
        }
        else {
            player_2.setText("Mort");
            health_2.setText("Health: 0");
        }

        Label weapon_2 = multiPlay.getLabelById("weapon_2");
        Label damage_2 = multiPlay.getLabelById("damage_2");
        if (Game.getP2().getCurrentWeapon() != null) {
            weapon_2.setText(Game.getP2().getCurrentWeapon().getName());
            damage_2.setText("Damage: " + Game.getP2().getCurrentWeapon().getDamageRange());
        }
        else {
            weapon_2.setText("Bare Hands");
            damage_2.setText("Damage: 0");
        }
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
