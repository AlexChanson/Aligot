package fsm.GUIStates;

import core.GraphicsEngine;
import fsm.State;
import gamelauncher.Game;
import graphics.Window;
import graphics.gui.GUI;
import graphics.gui.Label;
import son.SoundPlayer;

/**
 * Created by Christopher on 16/05/2017.
 */
public class MultiPlayState extends State {
    private GUI multiPlay;
    private GraphicsEngine graphicsEngine;
    private Label health_1;
    private Label weapon_1;
    private Label damage_1;
    private Label health_2;
    private Label weapon_2;
    private Label damage_2;
    private int width;
    private int height;

    public MultiPlayState(GraphicsEngine graphicsEngine) {
        this.graphicsEngine = graphicsEngine;
    }

    public void initialize() {
        multiPlay = new GUI();
        width = Window.getWidth();
        height = Window.getHeight();
        int labelWidth = (int)(Window.getWidth()*0.12), labelHeight =(int)(Window.getHeight()*0.05);
        Label player_1 = new Label ("Player_1", 10, 10, labelWidth, labelHeight, "player_1");
        health_1 = new Label ("Health: ", 10, 50, labelWidth, labelHeight, "health_1");
        weapon_1 = new Label ("Weapon: ", 10, 90, labelWidth, labelHeight, "weapon_1");
        damage_1 = new Label ("Damage: ", 10, 130, labelWidth, labelHeight, "damage_1");
        Label player_2 = new Label ("Player_2", Window.getWidth() - labelWidth - 10, 10, labelWidth, labelHeight, "player_2");
        health_2 = new Label ("Health: ", Window.getWidth() - labelWidth - 10, 50, labelWidth, labelHeight, "health_2");
        weapon_2 = new Label ("Weapon: ", Window.getWidth() - labelWidth - 10, 90, labelWidth, labelHeight, "weapon_2");
        damage_2 = new Label ("Damage: ", Window.getWidth() - labelWidth - 10, 130, labelWidth, labelHeight, "damage_2");
        multiPlay.addComponents(player_1, player_2, health_1, health_2, damage_1, damage_2, weapon_1, weapon_2);
    }
    public void onEnter(){
        SoundPlayer.playLoop("loop_2");
        graphicsEngine.setGUI(multiPlay);
    }

    @Override
    public void onExit() {
        SoundPlayer.stopLoop("loop_2");
    }

    @Override
    public String onUpdate() {
        if (width != Window.getWidth() || height != Window.getHeight()){
            updateGUISize();
            graphicsEngine.setGUI(multiPlay);
        }
        Label player_1 = multiPlay.getLabelById("player_1");
        health_1 = multiPlay.getLabelById("health_1");
        if (Game.getP1() != null) {
            player_1.setText(Game.getP1().getName());
            health_1.setText("Health: " + Game.getP1().getHealth());
        }
        else {
            player_1.setText("Mort");
            health_1.setText("Health: 0");
        }

        weapon_1 = multiPlay.getLabelById("weapon_1");
        damage_1 = multiPlay.getLabelById("damage_1");
        if (Game.getP1().getCurrentWeapon() != null) {
            weapon_1.setText(Game.getP1().getCurrentWeapon().getName());
            damage_1.setText("Damage: " + Game.getP1().getCurrentWeapon().getDamageRange()[0] + "-" + Game.getP1().getCurrentWeapon().getDamageRange()[1]);
        }
        else {
            weapon_1.setText("Bare Hands");
            damage_1.setText("Damage: 0");
        }


        Label player_2 = multiPlay.getLabelById("player_2");
        health_2 = multiPlay.getLabelById("health_2");
        if(Game.getP2() != null) {
            player_2.setText(Game.getP2().getName());
            health_2.setText("Health: " + Game.getP2().getHealth());
        }
        else {
            player_2.setText("Mort");
            health_2.setText("Health: 0");
        }

        weapon_2 = multiPlay.getLabelById("weapon_2");
        damage_2 = multiPlay.getLabelById("damage_2");
        if (Game.getP2().getCurrentWeapon() != null) {
            weapon_2.setText(Game.getP2().getCurrentWeapon().getName());
            damage_2.setText("Damage: " + Game.getP2().getCurrentWeapon().getDamageRange()[0] + "-" + Game.getP2().getCurrentWeapon().getDamageRange()[1]);
        }
        else {
            weapon_2.setText("Bare Hands");
            damage_2.setText("Damage: 0");
        }
        return "multiPlay";
    }

    private void updateGUISize () {
        initialize();
    }

    @Override
    public String getStateName() {
        return "multiPlay";
    }
}
