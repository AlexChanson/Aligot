package gamelauncher;

import GUIStates.*;
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

        p1 = new Player(new RigidBody(new Vector2D(100,100),2, 70), "doomguy.jpg", firstPlayerName, 100);
        p2 = new Player(new RigidBody(new Vector2D(screenWidth-100,screenHeight-100),2, 70), "doomguy.jpg", secondPlayerName, 100);

        initEngine();
        GraphicsEngine graphicsEngine = new GraphicsEngine();

        FiniteStateMachine myMachine = new FiniteStateMachine();
        StartState startState = new StartState(GUI.getStart(), graphicsEngine);
        GameModsState gameModsState = new GameModsState(GUI.getGameMods(), graphicsEngine);
        SelectChallengeState selectChallengeState = new SelectChallengeState(GUI.getSelectChallenge(), graphicsEngine);
        ChallengesState challengesEasyState = new ChallengesState(graphicsEngine, 1);
        ChallengesState challengesMediumState = new ChallengesState(graphicsEngine, 2);
        ChallengesState challengesHardState = new ChallengesState(graphicsEngine, 3);
        ExitState exitState = new ExitState();
        myMachine.addState(startState);
        myMachine.addState(gameModsState);
        myMachine.addState(selectChallengeState);
        myMachine.addState(challengesEasyState);
        myMachine.addState(challengesMediumState);
        myMachine.addState(challengesHardState);
        myMachine.addState(exitState);

        //Main Game Loop
        while (Window.shouldClose()) {
            Window.loopStart();
            myMachine.update();

            if(engine != null)
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
        //TODO reset players properly
        p1.setHealth(p1.getMaxHealth());
        p2.setHealth(p2.getMaxHealth());
        engine = null;
        initEngine();
    }

    private static void initEngine(){
        if(engine != null){
            try {
                engine = new Engine(currentLevel, p1, p2);
            }catch (NullPointerException e){
                System.out.println("Engine Init Failed");
            }
        }
    }
}
