package core.solvers;

import core.Engine;

/**
 * The solvers converts the inputs form the lwjgl to events compatible
 * with the engine
 */
public abstract class Solver {
    /**
     * A reference to the engine
     */
    protected Engine engine;

    public Solver() {}

    /**
     * called by the engine to initialize the solver
     */
    public abstract void initialize();

    /**
     * called by the engine to register itself the the solver
     * @param engine the engine registering this solver
     */
    public void setEngine(Engine engine){
        this.engine = engine;
    }


}
