package core.systems;

import core.Event;
import son.SoundPlayer;

/**
 * @author Alexandre
 * @handles EXPLOSION
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
