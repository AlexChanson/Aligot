package graphics.gui;

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

    public Label getLabelById(String id) {
        for (int i=0; i<components.size(); i++) {
            if(components.get(i).getId().equals(id)) {
                return (Label) components.get(i);
            }
        }
        return null;
    }

    public void setComponents(ArrayList<GUIComponent> components) {
        this.components = components;
    }
}