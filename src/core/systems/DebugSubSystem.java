package core.systems;

import core.Event;

public class DebugSubSystem  extends SubSystem{
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (!event.type.equals("TICK"))
            System.out.printf("ENGINE EVENT { type='%s', data='%s' }%n", event.type, String.valueOf(event.data));
    }
}
