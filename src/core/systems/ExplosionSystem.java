package core.systems;

import core.Event;
import core.model.Explosion;
import core.model.Projectile;
import gamelauncher.Game;
import particles.ExplosionEmitter;
import physics.RigidBody;
import physics.Vector2D;
import utility.Pair;

import java.util.ArrayList;
import java.util.Random;

public class ExplosionSystem extends SubSystem{
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (engine.getProjectiles().size() == 0)
            return;
        if (event.type.equals("COLLISION")){
            Pair<Pair<RigidBody, RigidBody>, Double> collisionData = (Pair<Pair<RigidBody, RigidBody>, Double>) event.data;
            Pair<RigidBody, RigidBody> bodies = collisionData.getLeft();
            RigidBody projectile = null, other = null;
            ArrayList<RigidBody> projectiles = new ArrayList<>();
            engine.getProjectiles().forEach(p -> projectiles.add(p.getRigidBody()));
            for (RigidBody p : projectiles) {
                if (p == bodies.getLeft()){
                    projectile = p;
                    other = bodies.getRight();
                    break;
                }else if (p == bodies.getRight()){
                    projectile = p;
                    other = bodies.getLeft();
                    break;
                }
            }
            if (projectile == null)
                return;
            Projectile pr = null;
            for (Projectile p : engine.getProjectiles()) {
                if (p.getRigidBody() == projectile)
                    pr = p;
            }
            if (pr == null)
                return;
            if (pr.getType().getExplosionRadius() > 0){
                int[] range = pr.getOrigin().getDamageRange();
                int damage = new Random().nextInt(range[1] - range[0]) + range[0] + pr.getType().getDamageBonus();
                engine.throwEvent(new Event("EXPLOSION", new Explosion(new Vector2D(pr.getRigidBody().getPosition()), pr.getType().getExplosionRadius(), damage)));
                engine.getProjectiles().remove(pr);
                engine.getPhysicsEngine().removeBody(projectile);
                Game.getParticleSystem().addEmitter(new ExplosionEmitter(
                        new Vector2D(pr.getRigidBody().getPosition()), pr.getType().getExplosionRadius()*15));
            }
        }
    }
}
