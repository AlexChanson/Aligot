package core.solvers;

import core.Event;

/**
 * A quick Solver example that prints to the console all DEBUG events
 * @author Alexandre
 */
public class DebugSolver extends Solver{

    @Override
    public void registerEvent(Event e) {
        if(e.type.equals("DEBUG"))
            pendingEvents.add(e);
    }

    @Override
    public void resolve() {
        pendingEvents.forEach(event -> System.out.println("@DebugSolver: " + event.data.toString()));
        pendingEvents.clear();
    }
}
