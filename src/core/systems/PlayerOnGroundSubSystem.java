package core.systems;

import core.Event;

/**
 * Created by ben on 16/05/17.
 */
public class PlayerOnGroundSubSystem extends SubSystem {
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if ( event.type.equals("TICK") ){

        }
    }
}
