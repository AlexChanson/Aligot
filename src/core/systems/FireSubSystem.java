package core.systems;

import core.Event;
import core.Player;
import core.Projectile;
import core.Weapon;
import core.solvers.PlayerMovementSolver;
import physics.RigidBody;
import physics.Vector2D;

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
