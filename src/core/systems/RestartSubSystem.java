package core.systems;

import core.Event;
import gamelauncher.Game;

/**
 * Created by ben on 18/05/17.
 */
public class RestartSubSystem extends SubSystem {
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (event.type == "RESTART_GAME"){
            engine.putPlayersOnSpawns();
        }
    }
}
