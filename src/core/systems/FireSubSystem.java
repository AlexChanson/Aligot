package core.systems;

import core.Event;
import core.model.Player;
import core.solvers.PlayerMovementSolver;

import java.util.logging.Logger;

public class FireSubSystem extends SubSystem{
    private final static Logger LOGGER = Logger.getLogger(PlayerMovementSolver.class.getName());

    @Override
    public void initialize() {
        LOGGER.info("Init OK");
    }

    @Override
    protected void processEvent(Event event) {
        if(event.type.equals("FIRE")){
            Player shooter = engine.getActivePlayer();
            if(shooter.getCurrentWeapon() != null){
                //TODO Fire a projectile
            }
        }
    }
}
