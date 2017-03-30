package physics;

import utility.Pair;

/**
 * Created by ben on 30/03/17.
 */
public interface CollisionListener {
    public void notifyCollision(Pair<RigidBody, RigidBody> pair, double distance);
}
