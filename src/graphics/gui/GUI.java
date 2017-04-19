package graphics.gui;

import graphics.Window;
import java.util.ArrayList;

public class GUI{
    private ArrayList<GUIComponent> components;
    private Button play;
    private Button exit;
    private Button solo;
    private Button multi;
    private ArrayList<Button> challenges = new ArrayList<Button>();

    public GUI (){
        components = new ArrayList<>();
    }

    public void addComponent(GUIComponent c){
        if (components.isEmpty()){
            components.add(c);
        }
        else{
            boolean found = false;
            for (int i = 0; i<components.size() && !found; i++) {
                if (components.get(i).getZ() >= c.getZ()) {
                    components.add(i, c);
                    found = true;
                }
            }
            if (!found){
                components.add(c);
            }
        }
    }

    public static GUI getStart(){
        GUI GUIStart = new GUI ();
        Button start = new Button("gui_main_button_play.png","", Window.getWidth()/2 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getHeight()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIStart.addComponent(start);
        Button exit = new Button("gui_main_button_quit.png","", Window.getWidth()/2 - Window.getTexture("gui_main_button_quit.png").getWidth() /2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_quit.png").getHeight()/2, Window.getTexture("gui_main_button_quit.png").getWidth(), Window.getTexture("gui_main_button_quit.png").getHeight());
        GUIStart.addComponent(exit);
        return GUIStart;
    }

    public static GUI getGameMods(){
        GUI GUIGameMods = new GUI();
        Button solo = new Button("gui_main_button_play.png","",Window.getWidth()/2 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getHeight()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIGameMods.addComponent(solo);
        Button multi = new Button("gui_main_button_quit.png","", Window.getWidth()/2 - Window.getTexture("gui_main_button_quit.png").getWidth() /2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_quit.png").getHeight()/2, Window.getTexture("gui_main_button_quit.png").getWidth(), Window.getTexture("gui_main_button_quit.png").getHeight());
        GUIGameMods.addComponent(multi);
        return GUIGameMods;
    }

    public static GUI getChallenges(){
        GUI GUIChallenge = new GUI();
        Button challenge1 = new Button("gui_main_button_play.png", "", Window.getWidth()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge1);
        Button challenge2 = new Button("gui_main_button_play.png", "", 3*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge2);
        Button challenge3 = new Button("gui_main_button_play.png", "", 3*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge3);
        Button challenge4 = new Button("gui_main_button_play.png", "", 1*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge4);
        return GUIChallenge;
    }

    /**public static GUI getMulti(){
        // TODO Dynamic GUI
    }*/

    public void removeComponent(GUIComponent c){
        components.remove(c);
    }

    public void affiche(){
        for (GUIComponent c: components){
            System.out.println(c.getZ());
        }
    }

    public ArrayList<GUIComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<GUIComponent> components) {
        this.components = components;
    }

    public Button getPlay() {
        return play;
    }

    public void setPlay(Button play) {
        this.play = play;
    }

    public Button getExit() {
        return exit;
    }

    public void setExit(Button exit) {
        this.exit = exit;
    }

    public Button getSolo() {
        return solo;
    }

    public void setSolo(Button solo) {
        this.solo = solo;
    }

    public Button getMulti() {
        return multi;
    }

    public void setMulti(Button multi) {
        this.multi = multi;
    }

    public void setChallenges(ArrayList<Button> challenges) {
        this.challenges = challenges;
    }
}
