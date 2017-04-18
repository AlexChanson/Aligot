package graphics.gui;

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

    public static GUI GetStart(){
        GUI GUIStart = new GUI ();
        Button start = new Button("gui_main_button_play.png", Window.getWidth()/2 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/2 - Window.getTexture("gui_main_button_play.png").getHeight()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        Button exit = new Button("gui_main_button_quit.png", Window.getWidth()/2 - Window.getTexture("gui_main_button_quit.png").getWidth() /2, Window.getHeight()/2 + Window.getTexture("gui_main_button_quit.png").getHeight()/2 + 27, Window.getTexture("gui_main_button_quit.png").getWidth(), Window.getTexture("gui_main_button_quit.png").getHeight());
        GUIStart.addComponent(start);
        GUIStart.addComponent(exit);
        return GUIStart;
    }

    public static GUI GetGameMods(){
        GUI GUIGameMods = new GUI();
        Button solo = new Button("gui_main_button_play.png", Window.getWidth()/2 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/2 - Window.getTexture("gui_main_button_play.png").getHeight()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        Button multi = new Button("gui_main_button_play.png", Window.getWidth()/2 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/2 + Window.getTexture("gui_main_button_play.png").getHeight()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIGameMods.addComponent(solo);
        GUIGameMods.addComponent(multi);
        return GUIGameMods;
    }

    public static GUI GetChallenge(){
        GUI GUIChallenge = new GUI();
        Button challenge1 = new Button("gui_main_button_play.png","", Window.getWidth()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        Button challenge2 = new Button("gui_main_button_play.png","", Window.getWidth()/4, Window.getHeight() - Window.getTexture("gui_main_button_play.png").getWidth()/4, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        Button challenge3 = new Button("gui_main_button_play.png","", Window.getWidth() - Window.getWidth()/4, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getHeight(), Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        Button challenge4 = new Button("gui_main_button_play.png","", Window.getWidth() - Window.getHeight()/4, Window.getHeight() - Window.getTexture("gui_main_button_play.png").getHeight(), Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
        GUIChallenge.addComponent(challenge1);
        GUIChallenge.addComponent(challenge2);
        GUIChallenge.addComponent(challenge3);
        GUIChallenge.addComponent(challenge4);
        return GUIChallenge;
    }

     public static GUI getMulti(){
         GUI GUIMulti = new GUI();
         Button challenge1 = new Button("gui_main_button_play.png", "", Window.getWidth()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
         GUIMulti.addComponent(challenge1);

         Button challenge2 = new Button("gui_main_button_play.png", "", 3*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getHeight()/4 - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
         GUIMulti.addComponent(challenge2);

         Button challenge3 = new Button("gui_main_button_play.png", "", 3*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
         GUIMulti.addComponent(challenge3);

         Button challenge4 = new Button("gui_main_button_play.png", "", 1*(Window.getWidth()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, 3*(Window.getHeight()/4) - Window.getTexture("gui_main_button_play.png").getWidth()/2, Window.getTexture("gui_main_button_play.png").getWidth(), Window.getTexture("gui_main_button_play.png").getHeight());
         GUIMulti.addComponent(challenge4);
         return GUIMulti;
     }
    /**
    public static GUI GetMulti(){
        GUI GUIMulti = new GUI();
        return GUIMulti;
    }
    */
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
