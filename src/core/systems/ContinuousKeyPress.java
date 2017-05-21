package core.systems;

import core.Event;
import utility.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @handles KEY_PRESSED, KEY_RELEASED, TICK
 * @emits KEY_PRESSED_CONTINUOUS
 */
public class ContinuousKeyPress extends SubSystem {
    private static HashMap<Integer, Integer> keyStates;
    @Override
    public void initialize() {
        keyStates = new HashMap<>();
    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("KEY_PRESSED")){
            keyStates.put((int)event.data, 1);
        }
        else if (event.type.equals("KEY_RELEASED")){
            keyStates.put((int)event.data, 0);
        }
        else if (event.type.equals("TICK")){
            for (Map.Entry<Integer, Integer> pair: keyStates.entrySet()){
                if (pair.getValue() == 1){
                    engine.throwEvent(new Event("KEY_PRESSED_CONTINUOUS", pair.getKey()));
                }
            }
        }

    }
}
