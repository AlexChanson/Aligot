package gamelauncher;

import core.*;
import core.LevelGen;
import graphics.Window;
import physics.RigidBody;
import physics.Vector2D;
import utility.Loader;

import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwTerminate;


public class Game implements GameStart {
    private static Engine engine;
    private static GraphicsEngine graphicsEngine;
    private Level currentLevel;

    @Override
    public void start(int screenHeight, int screenWidth, boolean fullscreen, String firstPlayerName, String secondPlayerName) {
        //Load Assets and set resources folder path
        Loader.decompileAssets();
        Window.setRessourcesFolderPath(Loader.getSpriteFolderPath());

        Window.init("Aligot", fullscreen);
        Window.setHeight(screenHeight);
        Window.setWidth(screenWidth);

        int[] mapSize = {screenWidth, screenHeight};
        LevelGen gen = new LevelGen(new Random().nextLong(), mapSize);
        currentLevel = gen.getLevel();
        Player p1 = new Player(new RigidBody(new Vector2D(100,100),2, 70), "doomguy.jpg", firstPlayerName, 100);
        Player p2 = new Player(new RigidBody(new Vector2D(screenWidth-100,screenHeight-100),2, 70), "doomguy.jpg", secondPlayerName, 100);
        engine = new Engine(currentLevel, p1, p2);

        graphicsEngine = new GraphicsEngine();

        while (Window.shouldClose()) {
            Window.loopStart();
            engine.update();
            graphicsEngine.drawLevel(currentLevel);
            Window.loopEnd();
        }
        glfwTerminate();
    }

    public static void notifyEngine(Event event){
        if (engine != null)
            engine.registerEvent(event);
    }
}
