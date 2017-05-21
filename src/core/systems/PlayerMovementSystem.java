package core.systems;

import core.Event;
import core.model.Player;
import physics.RigidBody;
import physics.Vector2D;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Ben Crulis
 * @handles PLAYER_MOVEMENT_STARTED, PLAYER_MOVEMENT_ENDED, PLAYER_CHANGED, TICK
 */
public class PlayerMovementSystem extends SubSystem {
    private static final Logger LOGGER = Logger.getLogger( PlayerMovementSystem.class.getName() );
    private HashMap<Player, Integer> playerMovements;

    @Override
    public void initialize() {
        LOGGER.info("Init OK");
        playerMovements = new HashMap<>();
    }

    @Override
    protected void processEvent(Event event) {
        Player player = engine.getActivePlayer();
        switch (event.type) {
            case "PLAYER_MOVEMENT_STARTED": {
                int direction = (int) event.data;
                playerMovements.put(player, direction);
                break;
            }
            case "PLAYER_MOVEMENT_ENDED": {
                playerMovements.put(player, -1);
                break;
            }
            case "PLAYER_CHANGED":
                break;
            case "TICK":
                if (playerMovements.containsKey(player) ) {
                    int direction = playerMovements.get(player);
                    double orientation = player.getRotation();
                    if (direction == 0) {
                        orientation += 140;
                        player.setLooking_right(false);
                    } else if (direction == 1) {
                        player.setLooking_right(true);
                        orientation -= 140;
                    }
                    RigidBody body = player.getRigidBody();
                    if ( direction != -1 ){
                        if (player.isOn_ground()) {
                            body.setVelocity(Vector2D.createFromAngleDegree(40, orientation));
                        }
                    }
                }
                player.setOn_ground(false);
                break;
        }
    }
}
