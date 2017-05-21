package core;

import core.gamestates.EndGameState;
import core.gamestates.PlayerActingState;
import core.gamestates.SimulationState;
import core.model.Level;
import core.model.Planet;
import core.model.Player;
import core.model.Projectile;
import core.solvers.KeyPressSolver;
import fsm.FiniteStateMachine;
import core.solvers.Solver;
import core.systems.SubSystem;
import physics.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * The Game engine, binds the physics with the game mechanics and processes events
 * @author Alexandre 
 */
public class Engine {
    private final static Logger LOGGER = Logger.getLogger(KeyPressSolver.class.getName());
    private FiniteStateMachine gameState;
    private Simulator physicsEngine;
    private ArrayList<Solver> solvers;
    private ArrayList<SubSystem> systems;
    private double timeStep = 1.0/60;
    private int turns = 0;
    private Level level;
    private ArrayList<Player> players;
    private ArrayList<Projectile> projectiles;
    private boolean endGame = false;
    private long lastTime;

    public Engine(Level level, Player... players) {
        this.level = level;
        this.players = new ArrayList<>();
        for (Player p :
                players) {
            if (p != null)
                this.players.add(p);
        }
        initialize();
    }

    /**
     * Handling some of the engine initialization just in case I need two or more constructors
     */
    private void initialize(){
        gameState = new FiniteStateMachine();
        physicsEngine = new Simulator();
        systems = new ArrayList<>();
        projectiles = new ArrayList<>();
        solvers = new ArrayList<>();
        physicsEngine.addSolver(new NewtonGravitationSolver(6.67e-11));

        CollisionSolver collisionSolver = new CollisionSolver();
        physicsEngine.addSolver(collisionSolver);
        physicsEngine.addSolver(new AirDampingSolver(0.005,0.01));
        level.getPlanets().forEach(planet -> physicsEngine.addBody(planet.getRigidBody()));
        players.forEach(player -> physicsEngine.addBody(player.getRigidBody()));
        gameState = new FiniteStateMachine();
        gameState.addStates(new PlayerActingState(this),
                new SimulationState(),
                new EndGameState());

        putPlayersOnSpawns();
        lastTime = Instant.now().toEpochMilli();
    }

    /**
     * This method should be called to process a game tick
     */
    public void update(){

        //Throw an update Event
        systems.forEach(subSystem -> subSystem.handleEvent(new Event("TICK", Instant.now().toEpochMilli() - lastTime)));
        lastTime = Instant.now().toEpochMilli();

        if ( gameState.update() ){
            endGame = true;
        }

        //physics simulation
        physicsEngine.step(timeStep);

        //check for projectiles that are too old
        handleProjectiles();
    }

    /**
     * This method is used to destroy projectiles that have exceeded their maximum lifespan
     */
    private void handleProjectiles() {
        ArrayList<Projectile> toDestroy = new ArrayList<>();
        projectiles.forEach(projectile -> {
            projectile.increaseLifetime(timeStep);
            if (projectile.lifetime > projectile.timeToLive)
                toDestroy.add(projectile);
        });
        toDestroy.forEach(projectile -> {
            projectiles.remove(projectile);
            physicsEngine.removeBody(projectile.getRigidBody());
            if(projectiles.size() == 0)
                throwEvent(new Event("REMOVED_LAST_PROJECTILE"));
        });
    }


    /**
     * Throws an event to all SubSystems of the Engine
     * @param event The event to be forwarded to the subsystems
     */
    public void throwEvent(Event event){
        systems.forEach(solver -> {
            try {
                solver.handleEvent(event);
            }catch (Exception e){
                LOGGER.severe(solver.getClass().getName() + " Has thrown an Exception !");
                e.printStackTrace();
            }
        });
    }

    /**
     * Register subsystems to the engine
     * @param subSystems The SubSystem(s) to be registered
     *                   (order will determine which receives events firs)
     */
    public void registerSubSystems(SubSystem... subSystems){
        for(SubSystem ss : subSystems){
            ss.setEngine(this);
            ss.initialize();
            this.systems.add(ss);
        }
    }

    /**
     * Registers the Input Solvers to the engine
     * @param solvers The Solver(s) to be registered
     */
    public void registerSolvers(Solver... solvers){
        for (Solver solv : solvers){
            solv.setEngine(this);
            this.solvers.add(solv);
            solv.initialize();
        }
    }


    /**
     * This is called at the end of the initialization to place players on the spawns
     */
    public void putPlayersOnSpawns(){
        turns = 0;
        if(level != null && players.size() > 0){
            HashSet<Planet> spawns = new HashSet<>();
            level.getPlanets().forEach(planet -> {
                if(planet.isSpawn())
                    spawns.add(planet);
            });
            int i = 0;
            for(Planet spawn : spawns){
                if(i < players.size()){
                    Player p = players.get(i);
                    RigidBody s = spawn.getRigidBody();
                    p.getRigidBody().setPosition(new Vector2D(s.getPosition().getX(), s.getPosition().getY() - s.getRadius() - p.getRigidBody().getRadius() - 2));
                }
                ++i;
            }
        }
    }

    /**
     * Calculates the active player with the turn count and the total number of players
     * @return Active player
     */
    public Player getActivePlayer(){
        return this.players.get(this.turns%this.players.size());
    }

    /**
     * checks if simulation is going or if player should be able to act
     * @return true if a player has control
     */
    public boolean isPlayerTurn(){
        return gameState.getActualStateName().equals("playerActingState");
    }

    /**
     * Change the time stem for the physics simulation default 1/60 of a second
     * @param timeStep the new time step
     */
    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    public void addProjectile(Projectile projectile){
        this.projectiles.add(projectile);
        this.physicsEngine.addBody(projectile.getRigidBody());
    }

    public FiniteStateMachine getGameState() {
        return gameState;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) { // this shouldn't be used, maybe delete it?
        this.turns = turns;
    }

    public int nextTurn(){
        turns += 1;
        throwEvent(new Event("TURN_CHANGED", turns));
        return turns;
    }

    public Simulator getPhysicsEngine() {
        return physicsEngine;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public Level getLevel() {
        return level;
    }

    public ArrayList<SubSystem> getSystems() {
        return systems;
    }
}
