package core;

import fsm.FiniteStateMachine;
import fsm.GUIStates.*;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIComponent;
import physics.Vector2D;
import java.util.ArrayList;

public class GraphicsEngine {
    private ArrayList<GUIComponent> guiComponents;
    private FiniteStateMachine guiFSM;

    public GraphicsEngine() {
        guiComponents = new ArrayList<>();
    }

    public void initGUI(){
        guiFSM = new FiniteStateMachine();
        guiFSM.addStates(
                new StartState(GUI.getStart(), this),
                new GameModsState(GUI.getGameMods(), this),
                new SelectChallengeState(GUI.getSelectChallenge(),this),
                new ChallengesState(this,1),
                new ChallengesState(this,2),
                new ChallengesState(this,3),
                new ExitState());
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

            if(level.getChallenge() != null){
                level.getChallenge().getTargets().forEach((double[] target) -> {
                    Window.drawCircle((float)(target[0] * ratio) ,(float)(target[1] * ratio), 25, 255, 0, 0);
                });
            }
        }
    }

    public void drawGui(){
        guiFSM.update();
        guiComponents.forEach(GUIComponent::draw);
    }

    public void setGUI(GUI gui){
        guiComponents.clear();
        guiComponents.addAll(gui.getComponents());
        Window.getCallBackContainers().clear();
        guiComponents.forEach(guiComponent -> {
            if (guiComponent instanceof Button)
                Window.getCallBackContainers().addAll(((Button)guiComponent).getListeners());
        });
    }

    public ArrayList<GUIComponent> getGuiComponents() {
        return guiComponents;
    }
}
