package core.systems;


import core.Engine;
import core.Event;

/**
 * The base class for all sub systems of the Engine
 */
public abstract class SubSystem{
    protected Engine engine;
    /**
     * determines the state of the sub system if set to false it will ignore all events
     */
    protected boolean suspended = false;

    public SubSystem(){

    }

    /**
     * Initialization method works like a constructor will be executed when the
     * sub system is added to the engine
     */
    public abstract void initialize();

    /**
     * This is called by the engine to pass events the sub system
     * @param event the event to be thrown to the sub system
     */
    public final void handleEvent(Event event){
        if (!suspended)
            processEvent(event);
    }

    /**
     * This method must implement the actual event handling of the sub system
     * @param event The event to be handled or ignored
     */
    protected abstract void processEvent(Event event);

    /**
     * This method is called by the engine to register itself to the sub system
     * @param engine the engine registering the sub system
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    /**
     * Used to suspend the sub system
     */
    public void suspend(){
        suspended = true;
    }

    /**
     * Used to resume the sub system after it was suspended
     */
    public void resume(){
        suspended = false;
    }

    /**
     * checks is the sub system is suspended
     * @return true if the sub system is suspended
     */
    public boolean isSuspended(){
        return suspended;
    }
}
