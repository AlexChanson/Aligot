package graphics.gui;

import fsm.State;
import graphics.Window;
import java.util.ArrayList;

public class GUI{
    private ArrayList<GUIComponent> components;

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
        GUIButtonListener startButtonListener = new GUIButtonListener();
        Button start = new Button("gui_main_button_play.png","", Window.getWidth()/2 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getHeight()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        start.addListener(startButtonListener);
        GUIStart.addComponent(start);

        GUIButtonListener exitButtonListener = new GUIButtonListener();
        Button exit = new Button("gui_main_button_quit.png","", Window.getWidth()/2 - Window.getTexture("gui_main_button_quit.png").getWidth() /2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_quit.png").getHeight()/2, Window.getTexture("gui_main_button_quit.png").getWidth(), Window.getTexture("gui_main_button_quit.png").getHeight());
        exit.addListener(exitButtonListener);
        GUIStart.addComponent(exit);

        State stateStart = new State() {
            @Override
            public String onUpdate() {
                if(startButtonListener.isClicked()) {
                    return "gameMods";
                }
                else if (exitButtonListener.isClicked()) {
                }
                return "start";
            }

            @Override
            public String getStateName() {
                return "start";
            }
        };
        return GUIStart;
    }

    public static GUI getGameMods(){
        GUI GUIGameMods = new GUI();

        GUIButtonListener soloButtonListener = new GUIButtonListener();
        Button solo = new Button("gui_main_button_play.png","",Window.getWidth()/2 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getHeight()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        solo.addListener(soloButtonListener);
        GUIGameMods.addComponent(solo);

        GUIButtonListener multiButtonListener = new GUIButtonListener();
        Button multi = new Button("gui_main_button_quit.png","", Window.getWidth()/2 - Window.getTexture("gui_main_button_quit.png").getWidth() /2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_quit.png").getHeight()/2, Window.getTexture("gui_main_button_quit.png").getWidth(), Window.getTexture("gui_main_button_quit.png").getHeight());
        multi.addListener(multiButtonListener);
        GUIGameMods.addComponent(multi);

        State stateGameMods = new State() {
            @Override
            public String onUpdate() {
                if (soloButtonListener.isClicked()) {
                    return "challenges";
                }
                else if (multiButtonListener.isClicked()) {
                    return "multi";
                }
                return "gameMods";
            }

            @Override
            public String getStateName() {
                return "gameMods";
            }
        };
        return GUIGameMods;
    }

    public static GUI getChallenges(){
        GUI GUIChallenge = new GUI();

        GUIButtonListener challenge1ButtonListener = new GUIButtonListener();
        Button challenge1 = new Button("gui_main_button_play.png", "", Window.getWidth()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        challenge1.addListener(challenge1ButtonListener);
        GUIChallenge.addComponent(challenge1);

        GUIButtonListener challenge2ButtonListener = new GUIButtonListener();
        Button challenge2 = new Button("gui_main_button_play.png", "", 3*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        challenge2.addListener(challenge2ButtonListener);
        GUIChallenge.addComponent(challenge2);

        GUIButtonListener challenge3ButtonListener = new GUIButtonListener();
        Button challenge3 = new Button("gui_main_button_play.png", "", 3*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        challenge3.addListener(challenge3ButtonListener);
        GUIChallenge.addComponent(challenge3);

        GUIButtonListener challenge4ButtonListener = new GUIButtonListener();
        Button challenge4 = new Button("gui_main_button_play.png", "", 1*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        challenge4.addListener(challenge4ButtonListener);
        GUIChallenge.addComponent(challenge4);

        State stateChallenges = new State() {
            @Override
            public String onUpdate() {
                if (challenge1ButtonListener.isClicked()) {
                    return "challenge1";
                }
                else if (challenge2ButtonListener.isClicked()) {
                    return "challenge2";
                }
                else if (challenge3ButtonListener.isClicked()) {
                    return "challenge3";
                }
                else if (challenge4ButtonListener.isClicked()) {
                    return "challenge4";
                }
                return "challenges";
            }

            @Override
            public String getStateName() {
                return "challenges";
            }
        };

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

}
