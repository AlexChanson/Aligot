package core;

import FSM.FiniteStateMachine;
import physics.AirDampingSolver;
import physics.CollisionSolver;
import physics.NewtonGravitationSolver;
import physics.Simulator;
import java.util.ArrayList;


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
        gameState = new FiniteStateMachine();
        physicsEngine = new Simulator();
    }

    public void initialize(){
        physicsEngine.addSolver(new NewtonGravitationSolver(6.67e-11));
        physicsEngine.addSolver(new AirDampingSolver(0.005,0.01));
        physicsEngine.addSolver(new CollisionSolver());
        level.getPlanets().forEach(planet -> physicsEngine.addBody(planet.getRigidBody()));
        physicsEngine.addBody(p1.getRigidBody());
        physicsEngine.addBody(p2.getRigidBody());
        //TODO initialize the finite state machine
    }

    public void update(){

    }

    /**
     * This method registers the solver into the engine
     * @param solvers The solver(s) to add the the game engine
     */
    public void registerSolvers(Solver... solvers){
        for(Solver s : solvers){
            s.registerEngine(this);
            this.solvers.add(s);
        }
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }
}
