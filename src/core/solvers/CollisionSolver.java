package core.solvers;

import core.Event;
import physics.CollisionListener;
import physics.PhysicSolver;
import physics.RigidBody;
import utility.Pair;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CollisionSolver extends Solver implements CollisionListener {
    private final static Logger LOGGER = Logger.getLogger(CollisionSolver.class.getName());

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
            LOGGER.log(Level.INFO, "Init OK");
            return;
        }
        LOGGER.log(Level.INFO, "Init Failed, couldn't find a physics.CollisionSolver registered to the Simulator.");
    }
}
