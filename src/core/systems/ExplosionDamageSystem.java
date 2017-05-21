package core.systems;

import core.Event;
import core.model.Explosion;

/**
 * @author Alexandre Chanson
 * @handles EXPLOSION
 */
public class ExplosionDamageSystem extends SubSystem{
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("EXPLOSION")){
            Explosion explosion = (Explosion) event.data;
            engine.getPlayers().forEach(player -> {
                if (player.getRigidBody().getPosition().distanceTo(explosion.getPosition()) < explosion.getRadius()){
                    player.setHealth(player.getHealth() - explosion.getDamage());
                }
            });
        }
    }
}
