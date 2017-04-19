package graphics.gui;

import graphics.Window;
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
        Button solo = new Button("gui_main_button_play.png", "", Window.getWidth() / 2 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() / 4 - Window.getTexture("gui_main_button_play.png").getHeight() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight(),"solo");
        GUIGameMods.addComponent(solo);
        Button multi = new Button("gui_main_button_quit.png", "", Window.getWidth() / 2 - Window.getTexture("gui_main_button_quit.png").getWidth() / 2, 3 * (Window.getHeight() / 4) - Window.getTexture("gui_main_button_quit.png").getHeight() / 2, Window.getTexture("gui_main_button_quit.png").getWidth(), Window.getTexture("gui_main_button_quit.png").getHeight(),"multi");
        GUIGameMods.addComponent(multi);
        return GUIGameMods;
    }

    /**public static GUI getChallenges() {
        GUI GUIChallenge = new GUI();
        Button challenge1 = new Button("gui_main_button_play.png", "", Window.getWidth() / 4 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() / 4 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge1);
        Button challenge2 = new Button("gui_main_button_play.png", "", 3 * (Window.getWidth() / 4) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getHeight() / 4 - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge2);
        Button challenge3 = new Button("gui_main_button_play.png", "", 3 * (Window.getWidth() / 4) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, 3 * (Window.getHeight() / 4) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge3);
        Button challenge4 = new Button("gui_main_button_play.png", "", 1 * (Window.getWidth() / 4) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, 3 * (Window.getHeight() / 4) - Window.getTexture("gui_main_button_play.png").getWidth() / 2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge4);
        return GUIChallenge;
    }*/

    /**
     * public static GUI getMulti(){
     * // TODO Dynamic GUI
     * }
     */

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
