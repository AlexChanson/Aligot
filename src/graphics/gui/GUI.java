package graphics.gui;

import GUIStates.ChallengesState;
import core.Level;
import graphics.Window;
import sun.plugin.javascript.navig.Array;
import utility.Loader;

import java.util.ArrayList;

public class GUI {
    private ArrayList<GUIComponent> components;

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
        Button start = new Button("gui_main_button_play.png", "", Window.getWidth() / 2 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() / 4 - Window.getTexture("gui_main_button_play.png").getHeight() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight(),"play");
        GUIStart.addComponent(start);
        Button exit = new Button("gui_main_button_quit.png", "", Window.getWidth() / 2 - Window.getTexture("gui_main_button_quit.png").getWidth() / 2, 3 * (Window.getHeight() / 4) - Window.getTexture("gui_main_button_quit.png").getHeight() / 2, Window.getTexture("gui_main_button_quit.png").getWidth(), Window.getTexture("gui_main_button_quit.png").getHeight(),"exit");
        GUIStart.addComponent(exit);
        return GUIStart;
    }

    public static GUI getGameMods() {
        GUI GUIGameMods = new GUI();
        Button solo = new Button("", "SOLO", Window.getWidth() / 2 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() / 4 - Window.getTexture("gui_main_button_play.png").getHeight() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight(),"solo");
        GUIGameMods.addComponent(solo);
        Button multi = new Button("", "MULTI", Window.getWidth() / 2 - Window.getTexture("gui_main_button_quit.png").getWidth() / 2, 3 * (Window.getHeight() / 4) - Window.getTexture("gui_main_button_quit.png").getHeight() / 2, Window.getTexture("gui_main_button_quit.png").getWidth(), Window.getTexture("gui_main_button_quit.png").getHeight(),"multi");
        GUIGameMods.addComponent(multi);
        return GUIGameMods;
    }

    public static GUI getSelectChallenge() {
        GUI GUISelectChallenge = new GUI();
        Button easyChallenge = new Button("", "EASY", Window.getWidth() / 6 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() / 2 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight(), "easy");
        GUISelectChallenge.addComponent(easyChallenge);
        Button mediumChallenge = new Button("", "MEDIUM", 3* (Window.getWidth() / 6) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() / 2 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight(), "medium");
        GUISelectChallenge.addComponent(mediumChallenge);
        Button hardChallenge = new Button("", "HARD", 5 * (Window.getWidth() / 6) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() /2  - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight(), "hard");
        GUISelectChallenge.addComponent(hardChallenge);
        return GUISelectChallenge;
    }

    public static GUI getChallenges() {
        GUI GUIChallenges = new GUI();
        ArrayList<Level> levels = Loader.loadAll(Level.class, "challenges");
        ArrayList<Button> buttons = new ArrayList<>();
        GUIChallenges.removeChallengesByDifficulty(, levels);
        GUIChallenges.getGUIButtonPlacements(buttons, GUIChallenges, levels);
        return GUIChallenges;
    }

    /**
     * public static GUI getMulti(){
     * // TODO Dynamic GUI
     * }
     */
    private void removeChallengesByDifficulty (int difficulty, ArrayList<Level> levels){
        if (difficulty == 1) {
            levels.removeIf(level -> level.getChallenge().getDifficulty() > 1);
        }
        if (difficulty == 2) {
            levels.removeIf(level -> level.getChallenge().getDifficulty() == 3 || level.getChallenge().getDifficulty() == 1);
        }
        if (difficulty == 3) {
            levels.removeIf(level ->  level.getChallenge().getDifficulty() < 3);
        }
    }
    private GUI getGUIButtonPlacements (ArrayList<Button> buttons, GUI GUIChallenges, ArrayList<Level> levels){
        int iterator = 0;
        if (levels.size() <= 3) {
            for (int i = 0; i < levels.size(); i++) {
                buttons.add(new Button("C" + i, (2 * i + 1) * (Window.getWidth() / 6) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() / 2 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight(), "easy"));
                GUIChallenges.addComponent(buttons.get(i));
            }
        } else if (levels.size() > 3) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (iterator < levels.size()) {
                        buttons.add(new Button("C" + iterator, (2 * j + 1) * (Window.getWidth() / 6) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, (2 * i + 1) * Window.getHeight() / 4 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight(), "easy"));
                        GUIChallenges.addComponent(buttons.get(iterator));
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
            if (components.get(i).getId() == id) {
                Button button = (Button) components.get(i);
                return button;
            }
        }
        return null;
    }

    public void setComponents(ArrayList<GUIComponent> components) {
        this.components = components;
    }
}
