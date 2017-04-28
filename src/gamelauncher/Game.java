package gamelauncher;

import core.*;
import core.solvers.PlayerMovementSolver;
import core.systems.DebugSubSystem;
import graphics.Window;
import graphics.gui.GUI;
import physics.RigidBody;
import physics.Vector2D;
import utility.Loader;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwTerminate;


public class Game implements GameStart {
    private static Engine engine;
    private static GraphicsEngine graphicsEngine;
    private static Level currentLevel;
    private static Player p1, p2;

    @Override
    public void start(int screenHeight, int screenWidth, boolean fullscreen, String firstPlayerName, String secondPlayerName) {
        //Load Assets and set resources folder path
        Loader.decompileAssets();
        Window.setRessourcesFolderPath(Loader.getSpriteFolderPath());
        System.out.println("Set assets folder to : " + Loader.getSpriteFolderPath());

        Window.init("Aligot", fullscreen);
        System.out.println("Fullscreen: "+fullscreen+", width: "+screenWidth+", height: "+screenHeight);


        Window.setHeight(screenHeight);
        Window.setWidth(screenWidth);

        //Temporary Init level from procedural generator
        int[] mapSize = {screenWidth, screenHeight};
        LevelGen gen = new LevelGen(new Random().nextLong(), mapSize);
        currentLevel = gen.getLevel();

        p1 = new Player(new RigidBody(new Vector2D(100,100),2, 70), "doomguy.png", firstPlayerName, 100);
        p2 = new Player(new RigidBody(new Vector2D(screenWidth-100,screenHeight-100),2, 70), "doomguy.png", secondPlayerName, 100);

        //Engine init
        initEngine();
        engine.registerSubSystems(new DebugSubSystem());
        engine.registerSolvers(new PlayerMovementSolver());


        //Graphics Engine init
        graphicsEngine = new GraphicsEngine();
        //graphicsEngine.initGUI();

        //Main Game Loop
        while (Window.shouldClose()) {
            Window.loopStart();

            if(engine != null)
                engine.update();
            if(currentLevel != null) {
                graphicsEngine.drawLevel(currentLevel);
                graphicsEngine.drawPlayers(p1, p2);
            }
            graphicsEngine.drawGui();

            Window.loopEnd();
        }

        glfwTerminate();
    }


    public static void changeGUI(GUI gui){
        if (graphicsEngine != null)
            graphicsEngine.setGUI(gui);
    }

    public static void setLevel(Level level){
        currentLevel = level;
        //TODO reset players properly
        p1.setHealth(p1.getMaxHealth());
        p2.setHealth(p2.getMaxHealth());
        engine = null;
        initEngine();
    }

    private static void initEngine(){
        if(engine == null){
            try {
                engine = new Engine(currentLevel, p1, p2);
            }catch (NullPointerException e){
                System.out.println("Engine Init Failed");
            }
        }
    }
}
