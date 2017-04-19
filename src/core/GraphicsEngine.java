package core;

import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIComponent;
import physics.Vector2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GraphicsEngine {
    private ArrayList<GUIComponent> guiComponents;

    public GraphicsEngine() {
        guiComponents = new ArrayList<>();
    }

    public void drawLevel(Level level){
        if(level != null) {
            float screenWidth = Window.getWidth(), screenHeight = Window.getHeight();
            double ratio = Math.max(screenHeight / level.getMapSize()[1], screenWidth / level.getMapSize()[0]);

            Window.drawSprite(level.getBgTexture(), 0, 0, screenWidth, screenHeight, 0f);

            level.getPlanets().forEach(planet -> {
                Vector2D planetPos = planet.getRigidBody().getPosition();
                float planetSize = (float) (planet.getRigidBody().getRadius() * 2 * ratio);
                Window.drawSprite(planet.getTexture(), (float) (planetPos.getX() * ratio) - (planetSize / 2), (float) (planetPos.getY() * ratio) - (planetSize / 2), planetSize, planetSize, 0f);
            });
        }
    }

    public void drawGui(){
        guiComponents.forEach(GUIComponent::draw);
    }

    public void setGUI(GUI gui){
        guiComponents.clear();
        guiComponents.addAll(gui.getComponents());
        Window.callBackContainers.clear();
        guiComponents.forEach(guiComponent -> {
            if (guiComponent instanceof Button){
                Window.registerButtonListener(((Button) guiComponent).getListeners());
            }
        });
    }

    public ArrayList<GUIComponent> getGuiComponents() {
        return guiComponents;
    }
}
