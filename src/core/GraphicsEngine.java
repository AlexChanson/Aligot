package core;

import core.model.*;
import fsm.FiniteStateMachine;
import fsm.GUIStates.*;
import graphics.Texture;
import graphics.Window;
import graphics.gui.Button;
import graphics.gui.GUI;
import graphics.gui.GUIComponent;
import physics.RigidBody;
import physics.Vector2D;
import utility.Challenges;

import java.util.ArrayList;
import java.util.Arrays;

public class GraphicsEngine {
    private ArrayList<GUIComponent> guiComponents;
    private FiniteStateMachine guiFSM;
    public static boolean debugDisplay = false;
    private static float screenLevelRatio;

    public GraphicsEngine() {
        guiComponents = new ArrayList<>();
    }

    public void initGUI(){
        guiFSM = new FiniteStateMachine();
        guiFSM.addStates(
                new StartState(this),
                new GameModsState(this),
                new MultiState(this),
                new MultiPlayState(this),
                new ChallengePlayState(this),
                new ChallengeDifficultyState(this),
                new ChallengesState(this, Challenges.get(), 1),
                new ChallengesState(this,Challenges.get(), 2),
                new ChallengesState(this,Challenges.get(), 3),
                new ExitState());
        guiFSM.setInitialState("start");
    }

    public void drawLevel(Level level){
        if(level != null) {
            float screenWidth = Window.getWidth(), screenHeight = Window.getHeight();
            screenLevelRatio = Math.max(screenHeight / level.getMapSize()[1], screenWidth / level.getMapSize()[0]);

            Window.drawSprite(level.getBgTexture(), 0, 0, screenWidth, screenHeight, 0f);

            level.getPlanets().forEach(planet -> {
                Vector2D planetPos = planet.getRigidBody().getPosition();
                float planetSize = (float) (planet.getRigidBody().getRadius() * 2 * screenLevelRatio);
                float posX = (float) (planetPos.getX() * screenLevelRatio) - (planetSize / 2);
                float posY = (float) (planetPos.getY() * screenLevelRatio) - (planetSize / 2);



                Window.drawSprite(planet.getTexture(),
                        posX,
                        posY,
                        planetSize, planetSize, 0f);

                if( planet.getTexture().equals("placeholder.png") ){
                    Window.drawCircle((float) (planetPos.getX() * screenLevelRatio),
                            (float) (planetPos.getY() * screenLevelRatio),
                            planetSize/2f,
                            100,100,0);
                }




            });

            if(level.getChallenge() != null){
                float size = (float) Challenge.TARGET_SIZE;
                level.getChallenge().getTargets().forEach((double[] target) -> {
                    float x = (float) (target[0]*screenLevelRatio - size/2);
                    float y = (float) (target[1]*screenLevelRatio - size/2);
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

    public void drawPlayers(Level level, Player actualPlayer ,Player... players) {
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

            if ( !player.isLooking_right() ){
                sizeX *= -1;
            }

            Window.drawTexture(playerTexture,
                    posX, posY,
                    sizeX*2, sizeY*2,
                    (float)((player.getRotation()-90)),
                    1f,
                    0, 0,
                    playerTexture.getWidth(), playerTexture.getHeight(),
                    255,255,255);

            RigidBody body = player.getRigidBody();
            Vector2D pos = body.getPosition();
            Vector2D velOffset = pos.add(body.getVelocity());
            Vector2D accOffset = pos.add(body.getAcceleration().multiply(0.05));
            float playerPosX = (float) (pos.getX()*ratio);
            float playerPosY = (float)(pos.getY()*ratio);
            if (debugDisplay){
                Window.drawLine(playerPosX,
                        playerPosY,
                        (float) (velOffset.getX()*ratio),
                        (float) (velOffset.getY()*ratio), 1f, 0, 255, 0);
                Window.drawLine((float) (pos.getX()*ratio),
                        (float)(pos.getY()*ratio),
                        (float) (accOffset.getX()*ratio),
                        (float) (accOffset.getY()*ratio), 1f, 0, 0, 255);
                Window.drawText("r:"+Double.toString(player.getRotation()),
                        playerPosX+20f,
                        playerPosY+20f,
                        20f, 200f, 0, 0,255,255, true);
            }

            Texture viseurTexture = Window.getTexture("viseur-01.png");
            float scale = 0.1f;
            float ciblePosX = playerPosX - scale*viseurTexture.getWidth()/2;
            float ciblePosY = playerPosY - scale*viseurTexture.getHeight()/2;
            if (player == actualPlayer){
                Window.drawSpriteRotate("viseur-01.png",
                        ciblePosX,
                        ciblePosY,
                        (float)(60f),
                        0f, (float)(player.getGlobalWeaponOrientation()-90),1f,1f, scale );
            }
            //TODO Weapon rendering
            scale = 0.07f;
            Weapon weapon = player.getCurrentWeapon();
            float scaleX =  player.isLooking_right() ? 1 : -1;
            Texture weaponTexture = Window.getTexture(weapon.getTexture());
            float weaponPosX = playerPosX - scale*weaponTexture.getWidth()/2;
            float weaponPosY = playerPosY - scale*weaponTexture.getHeight()/2;
            if (weapon != null){
                Window.drawSpriteRotate(weapon.getTexture(),
                        weaponPosX,
                        weaponPosY,
                        10f,
                        0f, (float)(player.getGlobalWeaponOrientation()-90), 1f, scaleX, scale );
            }


        });
    }



    public void drawProjectiles(Level level, ArrayList<Projectile> projectiles) {
        float screenWidth = Window.getWidth(), screenHeight = Window.getHeight();
        double ratio = Math.max(screenHeight / level.getMapSize()[1], screenWidth / level.getMapSize()[0]);
        projectiles.forEach(projectile -> {
            Vector2D position = projectile.getRigidBody().getPosition();
            Window.drawCircle((float)(position.getX()*ratio),
                    (float)(position.getY()*ratio),
                    (float) (projectile.getRigidBody().getRadius()*ratio),
                    128,128,64);
        });
    }
}
