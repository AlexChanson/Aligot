package graphics.gui;

import core.Level;
import graphics.Texture;
import graphics.Window;
import utility.Challenges;
import utility.Loader;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GUI {
    private ArrayList<GUIComponent> components;
    private int buttonWidth = 250;
    private int buttonHeight = 250;

    public GUI() {
        components = new ArrayList<>();
    }

    public void addComponent(GUIComponent c) {
        if (components.isEmpty()) {
            components.add(c);
        } else {
            boolean found = false;
            for (int i = 0; i < components.size() && !found; i++) {
                if (components.get(i).getZ() >= c.getZ()) {
                    components.add(i, c);
                    found = true;
                }
            }
            if (!found) {
                components.add(c);
            }
        }
    }

    public static GUI getStart() {
        GUI GUIStart = new GUI();
        int buttonWidth = 600, buttonHeight = 250;
        Button start = new Button("button_start.png", "", Window.getWidth() / 2 - buttonWidth / 2, Window.getHeight() / 4 - buttonHeight / 2, buttonWidth, buttonHeight,"play");
        GUIStart.addComponent(start);
        Button exit = new Button("button_exit.png", "", Window.getWidth() / 2 - buttonWidth / 2, 3 * (Window.getHeight() / 4) - buttonHeight / 2, buttonWidth, buttonHeight,"exit");
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

        return GUIMulti;
    }

    private static GUI getGUIButtonsPlacementsByDifficulty (GUI GUIChallenges, ArrayList<Level> levels){
        int iterator = 0;
        int buttonWidth = 250, buttonHeight = 250;
        if (levels.size() <= 3) {
            for (int i = 0; i < levels.size(); i++) {
                if (levels.get(i).getChallenge().getDifficulty() == 1)
                    GUIChallenges.addComponent(new Button("C" + i, (2 * i + 1) * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonWidth / 2, buttonWidth, buttonHeight, "easy" + i));
                else if (levels.get(i).getChallenge().getDifficulty() == 2)
                    GUIChallenges.addComponent(new Button("C" + i, (2 * i + 1) * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonWidth / 2, buttonWidth, buttonHeight, "medium" + i));
                else if (levels.get(i).getChallenge().getDifficulty() == 3)
                    GUIChallenges.addComponent(new Button("C" + i, (2 * i + 1) * (Window.getWidth() / 6) - buttonWidth / 2, Window.getHeight() / 2 - buttonWidth / 2, buttonWidth, buttonHeight, "hard" + i));
            }
        } else if (levels.size() > 3) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (iterator < levels.size()) {
                        if (levels.get(i).getChallenge().getDifficulty() == 1)
                            GUIChallenges.addComponent(new Button("C" + iterator, (2 * j + 1) * (Window.getWidth() / 6) - buttonWidth / 2, (2 * i + 1) * Window.getHeight() / 4 - buttonWidth / 2, buttonWidth, buttonHeight, "easy" + iterator));
                        else if (levels.get(i).getChallenge().getDifficulty() == 2)
                            GUIChallenges.addComponent(new Button("C" + iterator, (2 * j + 1) * (Window.getWidth() / 6) - buttonWidth / 2, (2 * i + 1) * Window.getHeight() / 4 - buttonWidth / 2, buttonWidth, buttonHeight, "medium" + iterator));
                        else if (levels.get(i).getChallenge().getDifficulty() == 3)
                            GUIChallenges.addComponent(new Button("C" + iterator, (2 * j + 1) * (Window.getWidth() / 6) - buttonWidth / 2, (2 * i + 1) * Window.getHeight() / 4 - buttonWidth / 2, buttonWidth, buttonHeight, "hard" + iterator));
                        iterator++;
                    } else {
                        return GUIChallenges;
                    }
                }
            }
        }
        return GUIChallenges;
    }
    public void removeComponent(GUIComponent c) {
        components.remove(c);
    }

    public ArrayList<GUIComponent> getComponents() {
        return components;
    }

    public Button getButtonById(String id) {
        for(int i=0;i<components.size();i++) {
            if (components.get(i).getId().equals(id)) {
                return (Button) components.get(i);
            }
        }
        return null;
    }

    public void setComponents(ArrayList<GUIComponent> components) {
        this.components = components;
    }
}