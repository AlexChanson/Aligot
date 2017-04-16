package graphics.gui;

import graphics.Window;

import java.util.ArrayList;

public class GUI {
    private ArrayList<GUIComponent> components;

    public GUI (){
        components = new ArrayList<GUIComponent>();
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
        Button start = new Button ("gui_options_button_titles.png", Window.getWidth()/2, Window.getHeight()/4, Window.getTexture("gui_options_button_titles.png").getWidth(), Window.getTexture("gui_options_button_titles.png").getHeight());
        Button exit = new Button("gui_options_button_titles.png", Window.getWidth()/2, Window.getHeight()/2, Window.getTexture("gui_options_button_titles.png").getWidth(), Window.getTexture("gui_options_button_titles.png").getHeight());
        GUIStart.addComponent(start);
        GUIStart.addComponent(exit);
        return GUIStart;
    }

    /**public static GUI GetGameMods(){
        GUI GUIGameMods = new GUI();
        Button solo = new Button("", "SOLO");
        Button multi = new Button("", "MULTI");
        GUIGameMods.addComponent(solo);
        GUIGameMods.addComponent(multi);
        return GUIGameMods;
    }

    public static GUI GetChallenge(){
        GUI GUIChallenge = new GUI();
        Button challenge1 = new Button("", "CHALLENGE-1");
        Button challenge2 = new Button("", "CHALLENGE-2");
        Button challenge3 = new Button("", "CHALLENGE-3");
        Button challenge4 = new Button("", "CHALLENGE-4");
        GUIChallenge.addComponent(challenge1);
        GUIChallenge.addComponent(challenge2);
        GUIChallenge.addComponent(challenge3);
        GUIChallenge.addComponent(challenge4);
        return GUIChallenge;
    }

    public static GUI GetMulti(){
        GUI GUIMulti = new GUI();
        return GUIMulti;
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
