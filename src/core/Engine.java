package core;

import FSM.FiniteStateMachine;
import core.solvers.Solver;
import core.systems.CollisionSubSystem;
import core.systems.SubSystem;
import physics.AirDampingSolver;
import physics.CollisionSolver;
import physics.NewtonGravitationSolver;
import physics.Simulator;
import java.util.ArrayList;

/**
 * The Game engine, binds the physics with the game mechanics and processes events
 * @author Alexandre 
 */
public class Engine {
    private static Engine engine;
    private FiniteStateMachine gameState;
    private Simulator physicsEngine;
    private ArrayList<Solver> solvers;
    private ArrayList<SubSystem> systems;
    private double timeStep = 1/60;
    private Level level;
    private Player p1, p2;

    public Engine(Level level, Player p1, Player p2) {
        this.level = level;
        this.p1 = p1;
        this.p2 = p2;
        initialize();
        engine = this;
    }

    private void initialize(){
        gameState = new FiniteStateMachine();
        physicsEngine = new Simulator();
        solvers = new ArrayList<>();
        systems = new ArrayList<>();
        physicsEngine.addSolver(new NewtonGravitationSolver(6.67e-11));
        physicsEngine.addSolver(new AirDampingSolver(0.005,0.01));
        CollisionSolver collisionSolver = new CollisionSolver();
        CollisionSubSystem collisionSystem = new CollisionSubSystem();
        collisionSolver.registerCollisionListener(collisionSystem);
        physicsEngine.addSolver(collisionSolver);
        level.getPlanets().forEach(planet -> physicsEngine.addBody(planet.getRigidBody()));
        physicsEngine.addBody(p1.getRigidBody());
        physicsEngine.addBody(p2.getRigidBody());
        //TODO initialize the finite state machine
    }

    /**
     * This method should be called to process a game tick
     */
    public void update(){

        //Event processing
        solvers.forEach(Solver::resolve);

        //Sub Systems processing
        systems.forEach(SubSystem::update);

        //physics simulation
        physicsEngine.step(timeStep);
    }

    /**
     * Registers an event to the solvers
     * @param event The event to register
     */
    private void registerEvent(Event event){
        solvers.forEach(solver -> solver.registerEvent(event));
    }

    /**
     * This method registers the solver(s) into the engine,
     * if the order in which events are resolved matters to you you should register the solvers
     * in the correct order according to your needs.
     * @param solvers The solver(s) to add the the game engine
     */
    public void registerSolvers(Solver... solvers){
        for(Solver s : solvers){
            s.registerEngine(this);
            this.solvers.add(s);
        }
    }

    public void registerSubSystems(SubSystem... subSystems){
        for(SubSystem ss : subSystems){
            ss.setEngine(this);
            this.systems.add(ss);
        }
    }

    /**
     * Change the time stem for the physics simulation default 1/60 of a second
     * @param timeStep the new time step
     */
    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    public static void notify(Event event){
        if (engine != null)
            engine.registerEvent(event);
    }

    public static Engine getEngine(){
        return engine;
    }
}
