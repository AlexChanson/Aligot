package core.solvers;

import core.Engine;
import core.Event;

import java.util.ArrayList;

public abstract class Solver {
    protected Engine engine;
    protected ArrayList<Event> pendingEvents = new ArrayList<>();

    public Solver() {
    }

    /**
     * This method should be implemented to choose which event the solver
     * cares about and put them in the pendingEvents collection
     * if you don't care about any event implement as an empty method
     * @param e the event dispatch by Engine
     */
    public abstract void registerEvent(Event e);

    /**
     * This method should process any events in pendingEvents and clear it when done
     * and/or perform several other action not tied to events
     */
    public abstract void resolve();

    public void registerEngine(Engine engine){
        this.engine = engine;
    }
}
