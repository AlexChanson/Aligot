package graphics.gui;

import java.util.ArrayList;

public class GUI {
    private ArrayList<GUIComponent> components;

    public void addComponent(GUIComponent c){
        for (int i = 0; i<components.size(); i++) {
            if (components.get(i).getZ() > c.getZ()) {
                components.add(i, c);
            }
        }
    }

    public void removeComponent(GUIComponent c){
        components.remove(c);
    }

    public ArrayList<GUIComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<GUIComponent> components) {
        this.components = components;
    }
}
