package core.systems;


import core.Event;
import physics.CollisionListener;
import physics.RigidBody;
import utility.Pair;

public class CollisionSubSystem extends SubSystem implements CollisionListener{

    @Override
    public void handleEvent(Event event) {

    }


    @Override
    public void handleCollision(Pair<RigidBody, RigidBody> pair, double distance) {

    }
}
