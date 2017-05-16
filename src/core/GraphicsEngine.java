package core;

import fsm.FiniteStateMachine;
import fsm.GUIStates.*;
import graphics.Texture;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIComponent;
import physics.Vector2D;
import utility.Loader;

import java.util.ArrayList;
import java.util.Arrays;

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
                new MultiState(GUI.getMulti(), this),
                new MultiPlayState(GUI.getMultiPlay(), this),
                new ChallengePlayState(GUI.getChallengePlay(), this),
                new ChallengeDifficultyState(GUI.getSelectChallenge(),this),
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
        if (guiFSM != null)
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

    public void drawPlayers(Level level, Player... players) {
        float screenWidth = Window.getWidth(), screenHeight = Window.getHeight();
        double ratio = Math.max(screenHeight / level.getMapSize()[1], screenWidth / level.getMapSize()[0]);
        Arrays.asList(players).forEach(player -> {
            Vector2D position = player.getRigidBody().getPosition();
            Texture joueur = new Texture(Loader.getSpriteFolderPath() + "doomguy.png");
            double width = joueur.getWidth()/10, height = joueur.getWidth()/10;
            float scale = 10f;
            Window.drawTexture(joueur, (float)(position.getX()*ratio - (width/2)),
                    (float)(position.getY()*ratio - (height/2)),
                    (float)width,
                    (float)height,
                    (float)player.getRotation(),
                    scale,
                    0,
                    0,
                    (float)width,
                    (float)height,
                    255,255,255);
            Window.drawCircle((float)(position.getX()*ratio), (float)(position.getY()*ratio), 10, 128,128,64);
        });
    }

    public void drawProjectiles(Level level, ArrayList<Projectile> projectiles) {
        float screenWidth = Window.getWidth(), screenHeight = Window.getHeight();
        double ratio = Math.max(screenHeight / level.getMapSize()[1], screenWidth / level.getMapSize()[0]);
        projectiles.forEach(projectile -> {
            Vector2D position = projectile.getRigidBody().getPosition();
            Window.drawCircle((float)(position.getX()*ratio), (float)(position.getY()*ratio), 10, 128,128,64);
        });
    }
}
