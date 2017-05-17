package core.systems;

import core.Event;
import core.Player;
import physics.RigidBody;
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
        Player player = engine.getActivePlayer();
        if (event.type.equals("PLAYER_MOVEMENT")){
            int direction = (int) event.data;

            double orientation = player.getRotation();
            if ( direction == 0 ){
                orientation += Math.PI/2;
                player.setLooking_right(true);
            }
            else {
                player.setLooking_right(false);
                orientation -= Math.PI/2;
            }

            RigidBody body = player.getRigidBody();
            if ( player.isOn_ground() ){
                body.setVelocity(Vector2D.createFromAngle(40, orientation));
            }
        }
        if ( event.type.equals("TICK") ){
            player.setOn_ground(false);
        }
    }
}
