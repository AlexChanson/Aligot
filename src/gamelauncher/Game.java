package gamelauncher;

import core.Engine;
import core.GraphicsEngine;
import core.model.Level;
import core.model.Player;
import core.model.Progression;
import core.model.Weapon;
import core.solvers.*;
import core.systems.*;
import graphics.Animation;
import graphics.Window;
import graphics.gui.GUI;
import particles.ParticleSystem;
import particles.ProjectileTrailEmitter;
import physics.RigidBody;
import physics.Vector2D;
import son.SoundPlayer;
import utility.Loader;
import utility.Weapons;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.*;


public class Game implements GameStart {
    private final static Logger LOGGER = Logger.getLogger(Game.class.getName());
    private static final double targetFps = 60;
    private static Engine engine;
    private static GraphicsEngine graphicsEngine;
    private static Level currentLevel;
    private static Player p1, p2;
    private static ParticleSystem particleSystem;
    private static Progression progression;

    public static double getTargetFps(){
        return targetFps;
    }

    public static void changeGUI(GUI gui){
        if (graphicsEngine != null)
            graphicsEngine.setGUI(gui);
    }

    public static void setLevel(Level level){
        currentLevel = level;
        if(level != null && level.getChallenge() != null){
            p1.setHealth(p1.getMaxHealth());
            p1.getInventory().clear();
            Weapons.equip(p1);
            engine = null;
            initSoloEngine();
        } else if (level != null && level.getChallenge() == null){
            p1.setHealth(p1.getMaxHealth());
            p2.setHealth(p2.getMaxHealth());
            p1.getInventory().clear();
            Weapons.equip(p1);
            p2.getInventory().clear();
            Weapons.equip(p2);
            engine = null;
            initMultiEngine();
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

    public static Progression getProgression() {
        return progression;
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }

    private static void initMultiEngine(){
        if(engine == null){
            try {
                engine = new Engine(currentLevel, p1, p2);
            }catch (NullPointerException e){
                LOGGER.log(java.util.logging.Level.WARNING, "Engine Init Failed");
            }
            engine.registerSubSystems(
                    new DebugSubSystem().ignore(
                        "TICK",
                        "COLLISION",
                        "KEY_PRESSED",
                        "KEY_RELEASED",
                        "KEY_PRESSED_CONTINUOUS"),
                    new PlayerOrientationSystem(),
                    new PlayerMovementSystem(),
                    new FireSubSystem(),
                    new DebugCommandsSubSystem(),
                    new PlayerAimingSubSystem(),
                    new ContinuousKeyPress(),
                    new ChargingWeaponSubSystem(),
                    new WeaponChangeSystem(),
                    new ExplosionSystem(),
                    new TimerSubSystem(),
                    new SoundSystem(),
                    new ExplosionDamageSystem(),
                    new ExitSubSystem(graphicsEngine),
                    new TurnSubSystem());

            engine.registerSolvers(
                    new KeyPressSolver(),
                    new CollisionSolver(),
                    new RestartKeySolver(),
                    new FiringSolver(),
                    new WeaponChangeSolver());

            particleSystem = new ParticleSystem();
            particleSystem.addEmitter(new ProjectileTrailEmitter(engine, graphicsEngine));
        }
    }

    public static ParticleSystem getParticleSystem() {
        return particleSystem;
    }

    public static Engine getEngine() {
        return engine;
    }

    public static GraphicsEngine getGraphicsEngine() {
        return graphicsEngine;
    }

    private static void initSoloEngine(){
        if(engine == null){
            try {
                engine = new Engine(currentLevel, p1);
            }catch (NullPointerException e){
                LOGGER.log(java.util.logging.Level.WARNING, "Engine Init Failed");
            }
            engine.registerSubSystems(
                    new ChallengeSubSystem(),
                    new PlayerOrientationSystem(),
                    new PlayerMovementSystem(),
                    new FireSubSystem(),
                    new DebugCommandsSubSystem(),
                    new PlayerAimingSubSystem(),
                    new ContinuousKeyPress(),
                    new ChargingWeaponSubSystem(),
                    new WeaponChangeSystem(),
                    new ExplosionSystem(),
                    new TimerSubSystem(),
                    new SoundSystem(),
                    new ExplosionDamageSystem(),
                    new ExitSubSystem(graphicsEngine));

            engine.registerSolvers(
                    new KeyPressSolver(),
                    new CollisionSolver(),
                    new RestartKeySolver(),
                    new FiringSolver(),
                    new WeaponChangeSolver());

            particleSystem = new ParticleSystem();
            particleSystem.addEmitter(new ProjectileTrailEmitter(engine, graphicsEngine));
        }
    }

    /**
     * This method will be called when thee game loop is exit before the glfwTerminate() call
     */
    private static void onGameStop(){
        SoundPlayer.killAll();
        Progression.save(progression);
    }

    public static void halt(){
        LOGGER.info("Game will Halt !");
        System.out.println("--- --- Reporting CallStack --- ---");
        Arrays.asList(Thread.currentThread().getStackTrace()).forEach(System.out::println);
        System.out.println("--- --- CallStack Ends --- ---");
        glfwSetWindowShouldClose(Window.getWindow(), true);
    }

    @Override
    public void start(int screenHeight, int screenWidth, boolean fullscreen, String firstPlayerName, String secondPlayerName) {
        //Load Assets and set resources folder path
        Loader.decompileAssets();
        Window.setRessourcesFolderPath(Loader.getSpriteFolderPath());
        LOGGER.log(java.util.logging.Level.CONFIG, "Set assets folder to : " + Loader.getSpriteFolderPath());

        //Load progression data
        progression = Progression.load();

        Window.init("Space Warz - Gravity Fall",screenWidth, screenHeight, fullscreen);
        LOGGER.log(java.util.logging.Level.CONFIG, "Fullscreen: "+fullscreen+", width: "+screenWidth+", height: "+screenHeight);

        //Mute sound Key
        Window.getKeyboardListeners().add((window, key, scancode, action, mods) -> {
            if (action == GLFW_PRESS && key == GLFW_KEY_P){
                //System.out.println("test");
                SoundPlayer.mute(!SoundPlayer.isMuted());
            }
        });

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
        boolean quit = false;
        while (Window.shouldClose() && !quit) {
            Window.loopStart();

            if(engine != null && currentLevel != null) {
                double dt = 1.0/getTargetFps();
                engine.setTimeStep(dt);
                engine.update();
                // I don't thrust my devs...
                // it's totally useless...
                try {
                    particleSystem.update(dt);
                    Animation.passTimeForAll(dt);
                    graphicsEngine.drawLevel(currentLevel);
                    graphicsEngine.drawPlayers(currentLevel, engine.getActivePlayer(), p1, p2);
                    particleSystem.draw();
                    graphicsEngine.drawProjectiles(currentLevel, engine.getProjectiles());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }


            graphicsEngine.drawGui();

            quit = graphicsEngine.shouldQuit();
            Window.loopEnd();
        }

        LOGGER.info("Running on Game Stop now ...");
        onGameStop();

        LOGGER.info("Terminating GLFW window.");

        Window.exit();
    }
}
