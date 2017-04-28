package core.systems;

import core.Event;
import core.Player;
import physics.Vector2D;

import java.util.logging.Logger;

public class PlayerMovementSystem extends SubSystem {
    private static final Logger LOGGER = Logger.getLogger( PlayerMovementSystem.class.getName() );

    @Override
    public void initialize() {
        LOGGER.info("Init OK");
    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("PLAYER_MOVEMENT")){
            int direction = (int) event.data;
            Player player = engine.getActivePlayer();
            double orientation = player.getRotation();

            if ( direction == 0 ){
                orientation += 90;
            }
            else {
                orientation -= 90;
            }

            player.getRigidBody().applyForce(Vector2D.createFromAngle(100, orientation));
        }
    }
}
