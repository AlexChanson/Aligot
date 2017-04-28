package core.systems;


import core.Engine;
import core.Event;
import physics.CollisionListener;

public abstract class SubSystem{
    protected Engine engine;
    protected boolean suspended = false;

    public SubSystem(){

    }

    public abstract void initialize();
    public final void handleEvent(Event event){
        if (!suspended)
            processEvent(event);
    }

    protected abstract void processEvent(Event event);

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void suspend(){
        suspended = true;
    }

    public void resume(){
        suspended = false;
    }

    public boolean isSuspended(){
        return suspended;
    }
}
