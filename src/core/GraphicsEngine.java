package core;

import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUIComponent;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphicsEngine {
    private ArrayList<GUIComponent> guiComponents;

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
        Window.callBackContainers.clear();
        guiComponents.forEach(guiComponent -> {
            if (guiComponent instanceof Button)
                Window.callBackContainers.addAll(((Button) guiComponent).getListeners());
            guiComponent.draw();
        });
    }

    public void registerGUIComponents(GUIComponent... guiComponents){
        this.guiComponents.addAll(Arrays.asList(guiComponents));
    }

    public ArrayList<GUIComponent> getGuiComponents() {
        return guiComponents;
    }
}
