package utility;

import core.model.Level;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.Label;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GUIBuilder {

    public static GUI getStart() {
        GUI GUIStart = new GUI();
        int buttonWidth = (int)(Window.getWidth()*0.40), buttonHeight = (int)(Window.getHeight()*0.25);
        Button start = new Button("button_start.png", "", Window.getWidth() / 2 - buttonWidth / 2, 4 * (Window.getHeight() / 7) - buttonHeight /2, buttonWidth, buttonHeight,"play");
        GUIStart.addComponent(start);
        Button exit = new Button("button_exit.png", "", Window.getWidth() / 2 - buttonWidth / 2, 6 * (Window.getHeight() / 7) - buttonHeight/2 , buttonWidth, buttonHeight,"exit");
        GUIStart.addComponent(exit);
        return GUIStart;
    }

    public static GUI getGameMods() {
        GUI GUIGameMods = new GUI();
        int buttonWidth = 600, buttonHeight = 250;
        Button solo = new Button("button_solo.png", "", Window.getWidth() / 2 - buttonWidth / 2, Window.getHeight() / 4 - buttonHeight / 2, buttonWidth, buttonHeight,"solo");
        GUIGameMods.addComponent(solo);
        Button multi = new Button("button_multi.png", "", Window.getWidth() / 2 - buttonWidth / 2, 3 * (Window.getHeight() / 4) - buttonHeight / 2, buttonWidth, buttonHeight,"multi");
        GUIGameMods.addComponent(multi);
        Button back = new Button("button_back.png","", 50, Window.getHeight() - 100, 100, 50, "back");
        GUIGameMods.addComponent(back);
        return GUIGameMods;
    }

    public static GUI getSelectChallenge() {
        GUI GUISelectChallenge = new GUI();
        int buttonWidth = 400, buttonHeight = 200;
        Label description = new Label ("Hit all targets with a minimum\nof bullets to reach the highest score", Window.getWidth()/2 - 425, Window.getHeight()/7 - 75,900,100,"description");
        GUISelectChallenge.addComponent(description);
        Button easyChallenge = new Button("button_easy.png", "", Window.getWidth() / 6 - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "easy");
        GUISelectChallenge.addComponent(easyChallenge);
        Button mediumChallenge = new Button("button_medium.png", "", 3* (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "medium");
        GUISelectChallenge.addComponent(mediumChallenge);
        Button hardChallenge = new Button("button_hard.png", "", 5 * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() /2  - buttonHeight / 2, buttonWidth, buttonHeight, "hard");
        GUISelectChallenge.addComponent(hardChallenge);
        Button back = new Button("button_back.png","", 50, Window.getHeight() - 100, 100, 50, "back");
        GUISelectChallenge.addComponent(back);
        return GUISelectChallenge;
    }

    public static GUI getChallenges(int difficulty) {
        GUI GUIChallenges = new GUI();
        ArrayList<Level> levels = Challenges.get().stream().filter(level -> level.getChallenge().getDifficulty() == difficulty).collect(Collectors.toCollection(ArrayList::new));
        getGUIButtonsPlacementsByDifficulty(GUIChallenges, levels);
        Button back = new Button("button_back.png","", 50, Window.getHeight() - 100, 100, 50, "back");
        GUIChallenges.addComponent(back);
        return GUIChallenges;
    }

    public static GUI getMulti () {
        GUI GUIMulti = new GUI();
        int buttonWidth = 100, buttonHeight = 100;
        Label description = new Label("Select how many celestial bodies to generate", Window.getWidth()/2 - 425, Window.getHeight()/7 - 75, 900,100, "description");
        GUIMulti.addComponent(description);
        Label text = new Label("Number of celestial bodies", 4*(Window.getWidth()/10) - 225, Window.getHeight()/2 - 50, 450, 100, "text");
        GUIMulti.addComponent(text);
        Label count = new Label ("---", 7*(Window.getWidth()/10), Window.getHeight() /2 - 80, 50, 200, "count");
        GUIMulti.addComponent(count);
        Button up = new Button("button_up.png", "", 8*(Window.getWidth()/10) - buttonWidth / 2, Window.getHeight() /2 - buttonHeight /2 + 20, buttonWidth, buttonHeight, "up");
        GUIMulti.addComponent(up);
        Button down = new Button("button_down.png", "", 6*(Window.getWidth()/10) - buttonWidth / 2, Window.getHeight() /2 - buttonHeight/2 + 20, buttonWidth, buttonHeight,"down");
        GUIMulti.addComponent(down);
        Button fight = new Button("button_fight.png", "",Window.getWidth() - 200, Window.getHeight() - 150, 150, 100, "fight");
        GUIMulti.addComponent(fight);
        Button back = new Button("button_back.png","", 50, Window.getHeight() - 100, 100, 50, "back");
        GUIMulti.addComponent(back);
        return GUIMulti;
    }

    public static GUI getMultiPlay() {
        GUI GUIMultiPlay = new GUI();
        int buttonWidth = 150, buttonHeight = 40;
        Label player_1 = new Label ("Player_1", 10, 10, buttonWidth, buttonHeight, "player_1");
        GUIMultiPlay.addComponent(player_1);
        Label health_1 = new Label ("Health: ", 10, 50, buttonWidth, buttonHeight, "health_1");
        GUIMultiPlay.addComponent(health_1);
        Label weapon_1 = new Label ("Weapon: ", 10, 90, buttonWidth, buttonHeight, "weapon_1");
        GUIMultiPlay.addComponent(weapon_1);
        Label damage_1 = new Label ("Damage: ", 10, 130, buttonWidth, buttonHeight, "damage_1");
        GUIMultiPlay.addComponent(damage_1);
        Label player_2 = new Label ("Player_2", Window.getWidth() - buttonWidth - 10, 10, buttonWidth, buttonHeight, "player_2");
        GUIMultiPlay.addComponent(player_2);
        Label health_2 = new Label ("Health: ", Window.getWidth() - buttonWidth - 10, 50, buttonWidth, buttonHeight, "health_2");
        GUIMultiPlay.addComponent(health_2);
        Label weapon_2 = new Label ("Weapon: ", Window.getWidth() - buttonWidth - 10, 90, buttonWidth, buttonHeight, "weapon_2");
        GUIMultiPlay.addComponent(weapon_2);
        Label damage_2 = new Label ("Damage: ", Window.getWidth() - buttonWidth - 10, 130, buttonWidth, buttonHeight, "damage_2");
        GUIMultiPlay.addComponent(damage_2);
        return GUIMultiPlay;
    }

    public static GUI getChallengePlay(){
        GUI GUIChallengePlay = new GUI();
        int buttonWidth = 150, buttonHeight = 40;
        Label targets = new Label("Targets: ", 10, 10, buttonWidth, buttonHeight, "targets");
        GUIChallengePlay.addComponent(targets);
        Label shots = new Label("Shots: ", 10, 50, buttonWidth, buttonHeight, "shots");
        GUIChallengePlay.addComponent(shots);
        return GUIChallengePlay;
    }

    private static void getGUIButtonsPlacementsByDifficulty (GUI GUIChallenges, ArrayList<Level> levels){
        int iterator = 0;
        int buttonWidth = 400, buttonHeight = 200;
        if (levels.size() <= 3) {
            for (int i = 0; i < levels.size(); i++) {
                if (levels.get(i).getChallenge().getDifficulty() == 1)
                    GUIChallenges.addComponent(new Button("button_blank.png", "C-" + i, (2 * i + 1) * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "easy" + i));
                else if (levels.get(i).getChallenge().getDifficulty() == 2)
                    GUIChallenges.addComponent(new Button("button_blank.png", "C" + i, (2 * i + 1) * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "medium" + i));
                else if (levels.get(i).getChallenge().getDifficulty() == 3)
                    GUIChallenges.addComponent(new Button("button_blank.png", "C" + i, (2 * i + 1) * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonHeight / 2, buttonWidth, buttonHeight, "hard" + i));
            }
        } else if (levels.size() > 3) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (iterator < levels.size()) {
                        if (levels.get(i).getChallenge().getDifficulty() == 1)
                            GUIChallenges.addComponent(new Button("button_blank.png", "C" + iterator, (2 * j + 1) * (Window.getWidth() / 6) - buttonWidth / 2, (2 * i + 1) * Window.getHeight() / 4 - buttonHeight / 2, buttonWidth, buttonHeight, "easy" + iterator));
                        else if (levels.get(i).getChallenge().getDifficulty() == 2)
                            GUIChallenges.addComponent(new Button("button_blank.png", "C" + iterator, (2 * j + 1) * (Window.getWidth() / 6) - buttonWidth / 2, (2 * i + 1) * Window.getHeight() / 4 - buttonHeight / 2, buttonWidth, buttonHeight, "medium" + iterator));
                        else if (levels.get(i).getChallenge().getDifficulty() == 3)
                            GUIChallenges.addComponent(new Button("button_blank.png", "C" + iterator, (2 * j + 1) * (Window.getWidth() / 6) - buttonWidth / 2, (2 * i + 1) * Window.getHeight() / 4 - buttonHeight / 2, buttonWidth, buttonHeight, "hard" + iterator));
                        iterator++;
                    } else {
                        return;
                    }
                }
            }
        }
    }
}
