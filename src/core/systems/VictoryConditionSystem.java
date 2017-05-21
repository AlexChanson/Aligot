package core.systems;

import core.Event;
import core.model.Player;
import gamelauncher.Game;

/**
 * @author Alexandre Chanson
 * @handles TICK
 * @emits PLAYER_WINS
 */
public class VictoryConditionSystem extends SubSystem{
    private Player p1, p2;

    @Override
    public void initialize() {
        p1 = Game.getP1();
        p2 = Game.getP2();
    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("TICK")){
            if (p1.getHealth() <= 0)
                engine.throwEvent(new Event("PLAYER_WINS", p2));
            else if (p2.getHealth() <= 0)
                engine.throwEvent(new Event("PLAYER_WINS", p1));
        }
    }
}
