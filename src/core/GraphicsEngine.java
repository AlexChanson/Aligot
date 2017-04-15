package core;

import graphics.gui.GUIComponent;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphicsEngine {
    ArrayList<GUIComponent> guiComponents;

    public GraphicsEngine() {
        guiComponents = new ArrayList<>();
    }

    public void drawLevel(Level level){

    }

    public void drawGui(){
        guiComponents.sort((o1, o2) -> {
            if(o1.getZ() < o2.getZ())
                return -1;
            if(o1.getZ() > o2.getZ())
                return 1;
            return 0;
        });
        guiComponents.forEach(GUIComponent::draw);
    }

    public void registerGUIComponents(GUIComponent... guiComponents){
        this.guiComponents.addAll(Arrays.asList(guiComponents));
    }
}
