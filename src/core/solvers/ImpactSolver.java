package core.solvers;

import core.Event;
import physics.CollisionListener;
import physics.RigidBody;
import utility.Pair;

public class ImpactSolver extends Solver implements CollisionListener{
    @Override
    public void registerEvent(Event e) {
        //Do Nothing
    }

    @Override
    public void resolve() {
        pendingEvents.forEach(event -> {
            Collision collision = (Collision) event.data;
            //TODO handle impact
        });
        pendingEvents.clear();
    }

    @Override
    public void handleCollision(Pair<RigidBody, RigidBody> pair, double distance) {
        pendingEvents.add(new Event("IMPACT", new Collision(pair, distance)));
    }

    private class Collision{
        Pair<RigidBody, RigidBody> objects;
        double distance;

        Collision(Pair<RigidBody, RigidBody> objects, double distance) {
            this.objects = objects;
            this.distance = distance;
        }
    }
}
