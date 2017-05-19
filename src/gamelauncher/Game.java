package gamelauncher;

import core.*;
import core.model.Level;
import core.model.Player;
import core.solvers.CollisionSolver;
import core.solvers.PlayerMovementSolver;
import core.solvers.RestartKeySolver;
import core.systems.*;
import graphics.Window;
import graphics.gui.GUI;
import physics.RigidBody;
import physics.Vector2D;
import utility.Loader;
import utility.Weapons;

import java.util.Arrays;
import java.util.logging.*;

import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwTerminate;


public class Game implements GameStart {
    private final static Logger LOGGER = Logger.getLogger(Game.class.getName());
    private static Engine engine;
    private static GraphicsEngine graphicsEngine;
    private static Level currentLevel;
    private static Player p1, p2;

    @Override
    public void start(int screenHeight, int screenWidth, boolean fullscreen, String firstPlayerName, String secondPlayerName) {
        //Load Assets and set resources folder path
        Loader.decompileAssets();
        Window.setRessourcesFolderPath(Loader.getSpriteFolderPath());
        LOGGER.log(java.util.logging.Level.CONFIG, "Set assets folder to : " + Loader.getSpriteFolderPath());

        Window.init("Space Warz - Gravity Fall",screenWidth, screenHeight, fullscreen);
        LOGGER.log(java.util.logging.Level.CONFIG, "Fullscreen: "+fullscreen+", width: "+screenWidth+", height: "+screenHeight);

        // Setting up Two players
        RigidBody bodyPlayer1 = new RigidBody(new Vector2D(100,100),10, 70);
        RigidBody bodyPlayer2 = new RigidBody(new Vector2D(screenWidth-100,screenHeight-100),10, 70);
        bodyPlayer1.setFriction(0.3);
        bodyPlayer2.setFriction(0.3);
        p1 = new Player(bodyPlayer1, "guy_ref.png", firstPlayerName, 100);
        p2 = new Player(bodyPlayer2, "chick_ref.png", secondPlayerName, 100);



        //Graphics Engine init
        graphicsEngine = new GraphicsEngine();
        graphicsEngine.initGUI();

        //Main Game Loop
        while (Window.shouldClose()) {
            Window.loopStart();

            if(engine != null && currentLevel != null) {
                engine.update();
                graphicsEngine.drawLevel(currentLevel);
                graphicsEngine.drawPlayers(currentLevel, engine.getActivePlayer(), p1, p2);
                graphicsEngine.drawProjectiles(currentLevel, engine.getProjectiles());
            }

            graphicsEngine.drawGui();

            Window.loopEnd();
        }

        LOGGER.info("Running on Game Stop now ...");
        onGameStop();

        LOGGER.info("Terminating GLFW window.");

        Window.exit();
    }


    public static void changeGUI(GUI gui){
        if (graphicsEngine != null)
            graphicsEngine.setGUI(gui);
    }

    public static void setLevel(Level level){
        currentLevel = level;
        if(level != null && level.getChallenge() != null){
            p1.setHealth(p1.getMaxHealth());
            p2 = null;
            p1.getInventory().clear();
            //TODO Give Player challenge weapon
            //p1.setCurrentWeapon();
            engine = null;
            initEngine();
        } else if (level != null && level.getChallenge() == null){
            p1.setHealth(p1.getMaxHealth());
            p2.setHealth(p2.getMaxHealth());
            p1.getInventory().clear();
            Weapons.equip(p1);
            p2.getInventory().clear();
            Weapons.equip(p2);
            engine = null;
            initEngine();
        } else {
            engine = null;
        }
    }

    public static Player getP1() {
        return p1;
    }

    public static Player getP2() {
        return p2;
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }

    private static void initEngine(){
        if(engine == null){
            try {
                engine = new Engine(currentLevel, p1, p2);
            }catch (NullPointerException e){
                LOGGER.log(java.util.logging.Level.WARNING, "Engine Init Failed");
            }
            engine.registerSubSystems(new DebugSubSystem().ignore("TICK",
                    "COLLISION",
                    "KEY_PRESSED",
                    "KEY_RELEASED",
                    "KEY_PRESSED_CONTINUOUS"),
                    new PlayerOrientationSystem(),
                    new PlayerMovementSystem(),
                    new FireSubSystem(),
                    new DebugCommandsSubSystem(),
                    new PlayerAimingSubSystem(),
                    new ContinuousKeyPress());
            engine.registerSolvers(new PlayerMovementSolver(),
                    new CollisionSolver(),
                    new RestartKeySolver());
        }
    }

    private static void initSoloEngine(){
        //TODO init the engine for solo play
    }

    /**
     * This method will be called when thee game loop is exit before the glfwTerminate() call
     */
    private static void onGameStop(){


    }

    public static void halt(){
        LOGGER.info("Game will Halt !");
        System.out.println("--- --- Reporting CallStack --- ---");
        Arrays.asList(Thread.currentThread().getStackTrace()).forEach(System.out::println);
        System.out.println("--- --- CallStack Ends --- ---");
        glfwSetWindowShouldClose(Window.getWindow(), true);
    }
}
