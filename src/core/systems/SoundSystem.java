package core.systems;

import core.Event;
import son.SoundPlayer;

/**
 * Fait par Alexandre le 20/05/2017.
 */
public class SoundSystem extends SubSystem {
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("EXPLOSION"))
            SoundPlayer.play("explosion");
    }
}
