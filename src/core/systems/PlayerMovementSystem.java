package core.systems;

import core.Event;
import core.Player;
import physics.RigidBody;
import physics.Vector2D;

import java.util.HashMap;
import java.util.logging.Logger;

public class PlayerMovementSystem extends SubSystem {
    private static final Logger LOGGER = Logger.getLogger( PlayerMovementSystem.class.getName() );
    int[] dir = {0,0};

    @Override
    public void initialize() {
        LOGGER.info("Init OK");
    }

    @Override
    protected void processEvent(Event event) {
        Player player = engine.getActivePlayer();
        switch (event.type) {
            case "PLAYER_MOVEMENT_STARTED": {
                int direction = (int) event.data;
                dir[0] = 1;
                dir[1] = direction;
                break;
            }
            case "PLAYER_MOVEMENT_ENDED": {
                int direction = (int) event.data;
                dir[0] = 0;
                break;
            }
            case "PLAYER_CHANGED":
                dir[0] = 0;
                break;
            case "TICK":
                if (dir[0] == 1) {
                    double orientation = player.getRotation();
                    if (dir[1] == 0) {
                        orientation += Math.PI / 2;
                        player.setLooking_right(true);
                    } else {
                        player.setLooking_right(false);
                        orientation -= Math.PI / 2;
                    }
                    RigidBody body = player.getRigidBody();
                    if (player.isOn_ground()) {
                        body.setVelocity(Vector2D.createFromAngle(40, orientation));
                    }
                }
                player.setOn_ground(false);
                break;
        }
    }
}
