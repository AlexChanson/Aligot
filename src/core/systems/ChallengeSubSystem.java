package core.systems;

import core.Challenge;
import core.Event;
import core.Projectile;
import physics.*;
import utility.Pair;

import java.util.ArrayList;

public class ChallengeSubSystem extends SubSystem implements CollisionListener {
    private ArrayList<RigidBody> targets;
    public int score;

    @Override
    public void handleCollision(Pair<RigidBody, RigidBody> pair, double distance) {
        RigidBody cible, other;
        if(targets.contains(pair.getLeft())){
            cible = pair.getLeft();
            other = pair.getRight();
        }
        else if(targets.contains(pair.getRight())){
            cible = pair.getRight();
            other = pair.getLeft();
        }
        else {
            return;
        }
        Projectile found = null;
        for (Projectile projectile : engine.getProjectiles()) {
            if (projectile.getRigidBody().equals(other)) {
                found = projectile;
            }
        }
        if(found != null){
            score += 1;
            Simulator physics = engine.getPhysicsEngine();
            physics.removeBody(cible);
            physics.removeBody(other);
            engine.getProjectiles().remove(found);
        }
    }

    @Override
    public void initialize() {
        targets = new ArrayList<>();
        if(engine.getLevel().getChallenge() != null){
            targets = new ArrayList<>();
            engine.getLevel().getChallenge().getTargets().forEach(target -> {
                RigidBody temp = new RigidBody(new Vector2D(target[0], target[1]), Challenge.TARGET_SIZE, 0);
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
