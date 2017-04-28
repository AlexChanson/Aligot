package core.solvers;

import core.Engine;

public abstract class Solver {
    protected Engine engine;


    public Solver() {}

    public abstract void initialize();

    public void setEngine(Engine engine){
        this.engine = engine;
    }


}
