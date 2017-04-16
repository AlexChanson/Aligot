package core;

import graphics.Texture;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUIComponent;
import physics.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphicsEngine {
    private ArrayList<GUIComponent> guiComponents;

    public GraphicsEngine() {
        guiComponents = new ArrayList<>();
    }

    public void drawLevel(Level level){
        double screenWidth = Window.getWidth(), screenHeight = Window.getHeight();
        double ratio = Math.max(screenHeight/level.getMapSize()[1], screenWidth/level.getMapSize()[0]);

        level.getPlanets().forEach(planet -> {
            Vector2D planetPos = planet.getRigidBody().getPosition();
            double planetSize = planet.getRigidBody().getRadius()*2;
            Window.drawSprite(planet.getTexture(), (float)(planetPos.getX()*ratio), (float)(planetPos.getY()*ratio), (float)planetSize, (float)planetSize, 0f);
        });

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
