package utility;

import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.Image;
import graphics.gui.Label;

public class GUIBuilder {

    public static GUI getStart() {
        GUI GUIStart = new GUI();
        int buttonWidth = (int)(Window.getWidth()*0.3), buttonHeight = (int)(Window.getHeight()*0.20);
        Button start = new Button("button_start.png", "", Window.getWidth() / 2 - buttonWidth / 2, 4 * (Window.getHeight() / 7) - buttonHeight /3, buttonWidth, buttonHeight,"play");
        GUIStart.addComponent(start);
        Button exit = new Button("button_exit.png", "", Window.getWidth() / 2 - buttonWidth / 2, 6 * (Window.getHeight() / 7) - buttonHeight/2 , buttonWidth, buttonHeight,"exit");
        GUIStart.addComponent(exit);
        Image logo = new Image("logo.png", Window.getWidth() /4 - (Window.getHeight()/2)/12, Window.getHeight()/7 - buttonHeight/2, 3*(Window.getWidth()/5), Window.getHeight()/2);
        logo.setZ(-1);
        GUIStart.addComponent(logo);
        Image menu = new Image("menu_bg.png");
        menu.setZ(-2);
        GUIStart.addComponent(menu);
        return GUIStart;
    }

    public static GUI getGameMods() {
        GUI GUIGameMods = new GUI();
        int buttonWidth =(int) (Window.getWidth()*0.45), buttonHeight = (int)(Window.getHeight()*0.35);
        Image menu = new Image("menu_bg.png");
        menu.setZ(-1);
        GUIGameMods.addComponent(menu);
        Button solo = new Button("button_solo.png", "", Window.getWidth() / 2 - buttonWidth / 2, Window.getHeight() / 4 - buttonHeight / 2, buttonWidth, buttonHeight,"solo");
        GUIGameMods.addComponent(solo);
        Button multi = new Button("button_multi.png", "", Window.getWidth() / 2 - buttonWidth / 2, 3 * (Window.getHeight() / 4) - buttonHeight / 2, buttonWidth, buttonHeight,"multi");
        GUIGameMods.addComponent(multi);
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        GUIGameMods.addComponent(back);
        return GUIGameMods;
    }

    public static GUI getSelectChallenge() {
        GUI GUISelectChallenge = new GUI();
        int buttonWidth = (int)(Window.getWidth()*0.31), buttonHeight = (int)(Window.getHeight()*0.3);
        Image menu = new Image("menu_bg.png");
        menu.setZ(-1);
        GUISelectChallenge.addComponent(menu);
        Label description = new Label ("Hit all targets with a minimum of bullets to reach the highest score", Window.getWidth()/2 - (int)(Window.getWidth()*0.33), Window.getHeight()/7 - (int)(Window.getHeight()*0.05),(int)(Window.getWidth()*0.70),(int)(Window.getHeight()*0.13),"description");
        GUISelectChallenge.addComponent(description);
        Button easyChallenge = new Button("button_easy.png", "", Window.getWidth() / 6 - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "easy");
        GUISelectChallenge.addComponent(easyChallenge);
        Button mediumChallenge = new Button("button_medium.png", "", 3* (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "medium");
        GUISelectChallenge.addComponent(mediumChallenge);
        Button hardChallenge = new Button("button_hard.png", "", 5 * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() /2  - buttonHeight / 2, buttonWidth, buttonHeight, "hard");
        GUISelectChallenge.addComponent(hardChallenge);
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        GUISelectChallenge.addComponent(back);
        return GUISelectChallenge;
    }

    public static GUI getMulti () {
        GUI GUIMulti = new GUI();
        int buttonWidth = (int)(Window.getWidth()*0.08), buttonHeight = (int)(Window.getHeight()*0.14);
        Image menu = new Image("menu_bg.png");
        menu.setZ(-1);
        GUIMulti.addComponent(menu);
        Label description = new Label("Select how many celestial bodies to generate", Window.getWidth()/2 - (int)(Window.getWidth()*0.33), Window.getHeight()/7, (int)(Window.getWidth()*0.7),(int)(Window.getHeight()*0.16), "description");
        GUIMulti.addComponent(description);
        Label text = new Label("Number of celestial bodies", 4*(Window.getWidth()/10) - (int)(Window.getWidth()*0.17), Window.getHeight()/2 - (int)(Window.getHeight()*0.07), (int)(Window.getWidth()*0.35), (int)(Window.getHeight()*0.14), "text");
        GUIMulti.addComponent(text);
        Label count = new Label ("---", 7*(Window.getWidth()/10) -5, Window.getHeight() /2 - (int)(Window.getHeight()*0.08), (int)(Window.getWidth()*0.039), (int)(Window.getHeight()*0.27), "count");
        GUIMulti.addComponent(count);
        Button up = new Button("button_right.png", "", 8*(Window.getWidth()/10) - buttonWidth / 2, Window.getHeight() /2 - buttonHeight /2 + (int)(Window.getHeight()*0.06), buttonWidth - 15, buttonHeight - 20, "up");
        GUIMulti.addComponent(up);
        Button down = new Button("button_left.png", "", 6*(Window.getWidth()/10) - buttonWidth / 2, Window.getHeight() /2 - buttonHeight/2 + (int)(Window.getHeight()*0.06), buttonWidth - 15, buttonHeight - 20,"down");
        GUIMulti.addComponent(down);
        Button fight = new Button("button_fight.png", "",Window.getWidth() - (int)(Window.getWidth()*0.15), Window.getHeight() - (int)(Window.getHeight()*0.17), (int)(Window.getWidth()*0.11), (int)(Window.getHeight()*0.1), "fight");
        GUIMulti.addComponent(fight);
        Button back = new Button("button_back.png","", (int)(Window.getWidth()*0.03), Window.getHeight() - (int)(Window.getHeight()*0.12), (int)(Window.getWidth()*0.08),(int)(Window.getHeight()*0.07), "back");
        GUIMulti.addComponent(back);
        return GUIMulti;
    }

    public static GUI getMultiPlay() {
        //TODO do something that works with engine
        GUI GUIMultiPlay = new GUI();
        int labelWidth = (int)(Window.getWidth()*0.12), labelHeight =(int)(Window.getHeight()*0.05);
        Label player_1 = new Label ("Player_1", 10, 10, labelWidth, labelHeight, "player_1");
        Label health_1 = new Label ("Health: ", 10, 50, labelWidth, labelHeight, "health_1");
        Label weapon_1 = new Label ("Weapon: ", 10, 90, labelWidth, labelHeight, "weapon_1");
        Label damage_1 = new Label ("Damage: ", 10, 130, labelWidth, labelHeight, "damage_1");
        Label player_2 = new Label ("Player_2", Window.getWidth() - labelWidth - 10, 10, labelWidth, labelHeight, "player_2");
        Label health_2 = new Label ("Health: ", Window.getWidth() - labelWidth - 10, 50, labelWidth, labelHeight, "health_2");
        Label weapon_2 = new Label ("Weapon: ", Window.getWidth() - labelWidth - 10, 90, labelWidth, labelHeight, "weapon_2");
        Label damage_2 = new Label ("Damage: ", Window.getWidth() - labelWidth - 10, 130, labelWidth, labelHeight, "damage_2");
        GUIMultiPlay.addComponents(player_1, player_2, health_1, health_2, damage_1, damage_2, weapon_1, weapon_2);
        return GUIMultiPlay;
    }

    public static GUI getChallengePlay(){
        GUI GUIChallengePlay = new GUI();
        int labelWidth = (int)(Window.getWidth()*0.12), labelHeight =(int)(Window.getHeight()*0.05);
        Label targets = new Label("Targets: ", 10, 10, labelWidth, labelHeight, "targets");
        GUIChallengePlay.addComponent(targets);
        Label shots = new Label("Shots: ", 10, 50, labelWidth, labelHeight, "shots");
        GUIChallengePlay.addComponent(shots);
        return GUIChallengePlay;
    }

}
