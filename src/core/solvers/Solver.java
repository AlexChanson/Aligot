package core.solvers;

import core.Engine;

public abstract class Solver {
    protected Engine engine;
    protected boolean suspended = false;

    public Solver() {}

    public abstract void initialize();

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public void suspend(){
        suspended = true;
    }

    public void resume(){
        suspended = false;
    }

}
