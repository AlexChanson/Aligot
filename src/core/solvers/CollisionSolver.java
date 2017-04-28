package core.solvers;

import core.Event;
import physics.CollisionListener;
import physics.PhysicSolver;
import physics.RigidBody;
import utility.Pair;

public class CollisionSolver extends Solver implements CollisionListener {
    @Override
    public void handleCollision(Pair<RigidBody, RigidBody> pair, double distance) {
        engine.throwEvent(new Event("COLLISION", new Pair<>(pair, distance)));
    }

    @Override
    public void initialize() {
        physics.CollisionSolver collisionSolver = null;
        for (PhysicSolver physicSolver : engine.getPhysicsEngine().getPhysicSolvers()) {
            if (physicSolver instanceof physics.CollisionSolver) {
                collisionSolver = (physics.CollisionSolver) physicSolver;
                break;
            }
        }
        if (collisionSolver != null) {
            collisionSolver.registerCollisionListener(this);
            System.out.println("Collision Solver : Init OK");
            return;
        }
        System.out.println("Collision Solver : Init FAILED");
    }
}
