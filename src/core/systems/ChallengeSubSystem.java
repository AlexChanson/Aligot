package core.systems;

import core.Event;
import physics.*;
import utility.Pair;

import java.util.ArrayList;

public class ChallengeSubSystem extends SubSystem implements CollisionListener {
    private ArrayList<RigidBody> targets;
    public int score;

    @Override
    public void handleCollision(Pair<RigidBody, RigidBody> pair, double distance) {
        //TODO check if projectile collided with target
    }

    @Override
    public void initialize() {
        targets = new ArrayList<>();
        if(engine.getLevel().getChallenge() != null){
            targets = new ArrayList<>();
            engine.getLevel().getChallenge().getTargets().forEach(target -> {
                RigidBody temp = new RigidBody(new Vector2D(target[0], target[1]), 50, 0);
                temp.setAttractive(false);
                temp.setStaticObject(true);
                targets.add(temp);
            });
        }
        Simulator physics = engine.getPhysicsEngine();
        targets.forEach(physics::addBody);
    }

    @Override
    protected void processEvent(Event event) {

    }
}
