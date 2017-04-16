package graphics.gui;

import java.util.ArrayList;

public class GUI {
    private ArrayList<GUIComponent> components;

    public GUI (){
        components = new ArrayList<GUIComponent>();
    }

    public void addComponent(GUIComponent c){
        for (int i = 0; i<components.size(); i++) {
            if (components.get(i).getZ() > c.getZ()) {
                components.add(i, c);
            }
            else {
                components.add(c);
            }
        }
        if (components.size() == 0){
            components.add(c);
        }
    }

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
