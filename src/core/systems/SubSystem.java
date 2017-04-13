package core.systems;


import core.Engine;
import physics.CollisionListener;

public abstract class SubSystem implements CollisionListener {
    protected Engine engine;

    public SubSystem(){

    }

    public abstract void update();

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
