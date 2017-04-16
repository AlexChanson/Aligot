package graphics.gui;

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
                if (components.get(i).getZ() > c.getZ()) {
                    components.add(i, c);
                    found = true;
                }
            }
            if (!found){
                components.add(0, c);
            }
        }
        System.out.println("liste: "+components.toString());
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
