package core.systems;

import core.Engine;
import core.Event;
import core.Player;
import physics.Vector2D;

/**
 * Created by ben on 28/04/17.
 */
public class PlayerMovementSystem extends SubSystem {
    private Engine engine;

    public PlayerMovementSystem(Engine engine){
        this.engine = engine;
    }


    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (event.type == "PLAYER_MOVEMENT"){
            int direction = (int) event.data;
            Player player = engine.getActivePlayer();
            double orientation = player.getRotation();


            if ( direction == 0 ){
                orientation += 90;
            }
            else {
                orientation -= 90;
            }

            player.getRigidBody().setVelocity(Vector2D.createFromAngle( 5,orientation));
        }
    }
}
