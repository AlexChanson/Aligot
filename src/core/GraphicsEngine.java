package core;

import core.model.Challenge;
import core.model.Level;
import core.model.Player;
import core.model.Projectile;
import fsm.FiniteStateMachine;
import fsm.GUIStates.*;
import graphics.Texture;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIComponent;
import physics.Vector2D;
import utility.GUIBuilder;

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
                new StartState(GUIBuilder.getStart(), this),
                new GameModsState(GUIBuilder.getGameMods(), this),
                new MultiState(GUIBuilder.getMulti(), this),
                new MultiPlayState(GUIBuilder.getMultiPlay(), this),
                new ChallengePlayState(GUIBuilder.getChallengePlay(), this),
                new ChallengeDifficultyState(GUIBuilder.getSelectChallenge(),this),
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
                float posX = (float) (planetPos.getX() * ratio) - (planetSize / 2);
                float posY = (float) (planetPos.getY() * ratio) - (planetSize / 2);



                Window.drawSprite(planet.getTexture(),
                        posX,
                        posY,
                        planetSize, planetSize, 0f);

                if( planet.getTexture().equals("placeholder.png") ){
                    Window.drawCircle((float) (planetPos.getX() * ratio),
                            (float) (planetPos.getY() * ratio),
                            planetSize/2f,
                            255,0,0);
                }




            });

            if(level.getChallenge() != null){
                float size = (float) Challenge.TARGET_SIZE;
                level.getChallenge().getTargets().forEach((double[] target) -> {
                    float x = (float) (target[0]*ratio - size/2);
                    float y = (float) (target[1]*ratio - size/2);
                    Window.drawSprite("target.png", x, y, size, size,0f);
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
            if (player == null)
                return;
            Vector2D position = player.getRigidBody().getPosition();
            Texture playerTexture = Texture.getTexture(player.getTexture());

            float widthHeightRatio = (((float)playerTexture.getHeight())/(float)(playerTexture.getWidth()));

            float sizeX = (float) (player.getRigidBody().getRadius()*ratio/widthHeightRatio);
            float sizeY = (float) (player.getRigidBody().getRadius()*ratio);

            float posX = (float) (position.getX()*ratio - sizeX);
            float posY = (float) (position.getY()*ratio - sizeY);

            if ( player.isLooking_right() ){
                sizeX *= -1;
            }

            Window.drawTexture(playerTexture,
                    posX, posY,
                    sizeX*2, sizeY*2,
                    (float)((player.getRotation()-Math.PI/2)*180/Math.PI),
                    1f,
                    0, 0,
                    playerTexture.getWidth(), playerTexture.getHeight(),
                    255,255,255);

            //TODO Weapon rendering


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
