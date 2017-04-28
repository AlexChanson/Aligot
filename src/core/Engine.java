package core;

import fsm.FiniteStateMachine;
import core.solvers.Solver;
import core.systems.SubSystem;
import physics.AirDampingSolver;
import physics.CollisionSolver;
import physics.NewtonGravitationSolver;
import physics.Simulator;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Game engine, binds the physics with the game mechanics and processes events
 * @author Alexandre 
 */
public class Engine {
    private FiniteStateMachine gameState;
    private Simulator physicsEngine;
    private ArrayList<Solver> solvers;
    private ArrayList<SubSystem> systems;
    private double timeStep = 1/60;
    private int turns = 0;
    private Level level;
    private ArrayList<Player> players;

    public Engine(Level level, Player... players) {
        this.level = level;
        this.players = new ArrayList<>();
        this.players.addAll(Arrays.asList(players));
        initialize();
    }

    private void initialize(){
        gameState = new FiniteStateMachine();
        physicsEngine = new Simulator();
        systems = new ArrayList<>();
        solvers = new ArrayList<>();
        physicsEngine.addSolver(new NewtonGravitationSolver(6.67e-11));
        physicsEngine.addSolver(new AirDampingSolver(0.005,0.01));
        CollisionSolver collisionSolver = new CollisionSolver();
        physicsEngine.addSolver(collisionSolver);
        level.getPlanets().forEach(planet -> physicsEngine.addBody(planet.getRigidBody()));
        players.forEach(player -> physicsEngine.addBody(player.getRigidBody()));
        //TODO initialize the finite state machine
    }

    /**
     * This method should be called to process a game tick
     */
    public void update(){

        //Throw an update Event
        systems.forEach(subSystem -> subSystem.handleEvent(new Event("TICK", null)));

        //physics simulation
        physicsEngine.step(timeStep);
    }


    public void throwEvent(Event event){
        systems.forEach(solver -> solver.handleEvent(event));
    }

    public void registerSubSystems(SubSystem... subSystems){
        for(SubSystem ss : subSystems){
            ss.setEngine(this);
            ss.initialize();
            this.systems.add(ss);
        }
    }

    public void registerSolvers(Solver... solvers){
        for (Solver solv : solvers){
            solv.setEngine(this);
            this.solvers.add(solv);
            solv.initialize();
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
     * Change the time stem for the physics simulation default 1/60 of a second
     * @param timeStep the new time step
     */
    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
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
}
