package core;

import FSM.FiniteStateMachine;
import core.solvers.ImpactSolver;
import core.solvers.Solver;
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
    private FiniteStateMachine gameState;
    private Simulator physicsEngine;
    private ArrayList<Solver> solvers;
    private double timeStep = 1/60;
    private Level level;
    private Player p1, p2;

    public Engine(Level level, Player p1, Player p2) {
        this.level = level;
        this.p1 = p1;
        this.p2 = p2;
        initialize();
    }

    private void initialize(){
        gameState = new FiniteStateMachine();
        physicsEngine = new Simulator();
        solvers = new ArrayList<>();
        physicsEngine.addSolver(new NewtonGravitationSolver(6.67e-11));
        physicsEngine.addSolver(new AirDampingSolver(0.005,0.01));
        CollisionSolver collisionSolver = new CollisionSolver();
        ImpactSolver impactSolver = new ImpactSolver();
        collisionSolver.registerCollisionListener(impactSolver);
        physicsEngine.addSolver(collisionSolver);
        registerSolvers(impactSolver);
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

        //physics simulation
        physicsEngine.step(timeStep);
    }

    /**
     * Registers an event to the solvers
     * @param event The event to register
     */
    public void registerEvent(Event event){
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

    /**
     * Change the time stem for the physics simulation default 1/60 of a second
     * @param timeStep the new time step
     */
    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }
}
