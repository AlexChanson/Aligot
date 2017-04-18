package gamelauncher;

import core.*;
import core.LevelGen;
import fsm.FiniteStateMachine;
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

    @Override
    public void start(int screenHeight, int screenWidth, boolean fullscreen, String firstPlayerName, String secondPlayerName) {
        //Load Assets and set resources folder path
        FiniteStateMachine myMachine = new FiniteStateMachine();
        Loader.decompileAssets();
        Window.setRessourcesFolderPath(Loader.getSpriteFolderPath());

        Window.init("Aligot", fullscreen);
        Window.setHeight(screenHeight);
        Window.setWidth(screenWidth);

        //Temporary Init level from procedural generator
        int[] mapSize = {screenWidth, screenHeight};
        LevelGen gen = new LevelGen(new Random().nextLong(), mapSize);
        currentLevel = gen.getLevel();

        Player p1 = new Player(new RigidBody(new Vector2D(100,100),2, 70), "doomguy.jpg", firstPlayerName, 100);
        Player p2 = new Player(new RigidBody(new Vector2D(screenWidth-100,screenHeight-100),2, 70), "doomguy.jpg", secondPlayerName, 100);

        engine = new Engine(currentLevel, p1, p2);

        graphicsEngine = new GraphicsEngine();
        graphicsEngine.setGUI(GUI.getGameMods());

        //Main Game Loop
        while (Window.shouldClose()) {
            Window.loopStart();

            engine.update();
            if(currentLevel != null)
                graphicsEngine.drawLevel(currentLevel);
            graphicsEngine.drawGui();

            Window.loopEnd();
        }

        glfwTerminate();
    }

    public static void notifyEngine(Event event){
        if (engine != null)
            engine.registerEvent(event);
    }

    public static void changeGUI(GUI gui){
        graphicsEngine.setGUI(gui);
    }

    public static void setLevel(Level level){
        currentLevel = level;
    }
}
