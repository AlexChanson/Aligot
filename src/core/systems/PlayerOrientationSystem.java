package core.systems;

import core.Event;
import core.Player;
import physics.RigidBody;
import physics.Vector2D;
import utility.Pair;

/**
 * Created by ben on 28/04/17.
 */
public class PlayerOrientationSystem extends SubSystem {
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("COLLISION")){
            Pair<Pair<RigidBody, RigidBody>, Double> pair = (Pair<Pair<RigidBody, RigidBody>, Double>) event.data;

            RigidBody body1 = pair.getLeft().getLeft();
            RigidBody body2 = pair.getLeft().getRight();

            RigidBody playerBody = null;
            RigidBody otherBody = null;
            Player playerMatch = null;

            for ( Player player : engine.getPlayers() ){
                if ( player.getRigidBody() == body1 ){
                    playerBody = body1;
                    otherBody = body2;
                }
                else if ( player.getRigidBody() == body2 ){
                    playerBody = body2;
                    otherBody = body1;
                }
                else {
                    continue;
                }
                playerMatch = player;
                break;
            }
            if (playerBody != null && otherBody != null){
                Vector2D unit = otherBody.getPosition().minus(playerBody.getPosition());
                playerMatch.setRotation(unit.angle());
            }
        }
    }
}
