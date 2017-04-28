package core.systems;


import core.Engine;
import core.Event;
import physics.CollisionListener;

public abstract class SubSystem implements CollisionListener {
    protected Engine engine;

    public SubSystem(){

    }

    public abstract void initialize();
    public abstract void handleEvent(Event event);

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
