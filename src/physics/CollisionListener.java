package physics;

import utility.Pair;

/**
 * Created by ben on 30/03/17.
 */
public interface CollisionListener {
    public void handleCollision(Pair<RigidBody, RigidBody> pair, double distance);
}
