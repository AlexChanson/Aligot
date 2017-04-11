package core;

public abstract class Solver {
    private Engine engine;

    public Solver() {
    }

    public abstract void resolve();

    public void registerEngine(Engine engine){
        this.engine = engine;
    }
}
