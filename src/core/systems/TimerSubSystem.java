package core.systems;

import core.Event;
import utility.GameTimer;
import utility.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ben Crulis
 * @handles TIMER_STARTED, TIMER_SET_ENABLED, TIMER_REMOVE, TIMER_RESTART, TICK
 * @emits TIMER_FINISHED
 */
public class TimerSubSystem extends SubSystem {
    HashMap<Integer, GameTimer> timers;

    @Override
    public void initialize() {
        timers = new HashMap<>();
    }

    @Override
    protected void processEvent(Event event) {
        switch (event.type){
            case "TIMER_STARTED":
                Pair<Integer, GameTimer> pair = (Pair<Integer, GameTimer>)event.data;
                timers.put(pair.getLeft(), pair.getRight());
                break;
            case "TIMER_SET_ENABLED":
                Pair<Integer, Boolean> pairEnabled = (Pair<Integer, Boolean>) event.data;
                timers.get(pairEnabled.getLeft()).enabled = pairEnabled.getRight();
                break;
            case "TIMER_REMOVE":
                Integer intRemove = (Integer) event.data;
                timers.remove(intRemove);
                break;
            case "TIMER_RESTART":
                Integer intRestart = (Integer) event.data;
                timers.get(intRestart).restart();
                break;

            case "TICK":
                double elapsed = Long.valueOf((long)event.data).doubleValue()/1000.0;
                for (Map.Entry<Integer, GameTimer> entry: timers.entrySet()){
                    boolean finished = entry.getValue().increment(elapsed);
                    if (finished){
                        Pair<Integer, GameTimer> finishedPair = new Pair<>(entry.getKey(), entry.getValue());
                        engine.throwEvent(new Event("TIMER_FINISHED", finishedPair));
                    }
                }
        }
    }
}
