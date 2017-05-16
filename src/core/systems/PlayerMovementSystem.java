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
        if (event.type.equals("PLAYER_MOVEMENT")){
            int direction = (int) event.data;
            Player player = engine.getActivePlayer();
            double orientation = player.getRotation();

            if ( direction == 0 ){
                orientation += 90;
                player.setLooking_right(true);
            }
            else {
                player.setLooking_right(false);
                orientation -= 90;
            }

            RigidBody body = player.getRigidBody();
            player.getRigidBody().applyForce(Vector2D.createFromAngle(1000, orientation));
            body.setPosition(body.getPosition().add(Vector2D.createFromAngle(10, orientation)));
        }
    }
}
