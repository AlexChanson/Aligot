package core.systems;

import core.Event;
import core.model.Ammunition;
import core.model.Player;
import core.model.Projectile;
import core.solvers.KeyPressSolver;
import physics.RigidBody;
import physics.Vector2D;
import son.SoundPlayer;

import java.util.logging.Logger;

/**
 * @author Alexandre Chanson
 * @handles FIRE
 * @emits LAST_PROJECTILE_USED, END_OF_PLAYER_TURN
 */
public class FireSubSystem extends SubSystem{
    private final static Logger LOGGER = Logger.getLogger(KeyPressSolver.class.getName());

    @Override
    public void initialize() {
        LOGGER.info("Init OK");
    }

    @Override
    protected void processEvent(Event event) {
        if(event.type.equals("FIRE")){
            double amount = (double) event.data;
            Player shooter = engine.getActivePlayer();
            Ammunition ammo = shooter.getCurrentWeapon().getAmmo();
            if(shooter.getCurrentWeapon() != null){
                if (engine.getLevel().getChallenge() != null){
                    if (engine.getLevel().getChallenge().getShots() > 0){
                        engine.getLevel().getChallenge().setShots(engine.getLevel().getChallenge().getShots() - 1);
                        if (engine.getLevel().getChallenge().getShots() == 0)
                            engine.throwEvent(new Event("LAST_PROJECTILE_USED"));
                    }else
                        return;
                }
                if (shooter.getCurrentWeapon().getName().equals("Bazoo-K"))
                    SoundPlayer.play("bazooka");
                else
                    SoundPlayer.play("pew");
                Vector2D firingAngle = Vector2D.createFromAngleDegree(1, shooter.getGlobalWeaponOrientation() - 90);
                Vector2D projectileOrigin = new Vector2D(shooter.getRigidBody().getPosition());
                projectileOrigin = projectileOrigin.add(firingAngle.multiply(15.5));
                RigidBody b = new RigidBody(projectileOrigin, 5, 50*ammo.getMassModifier());
                b.setStaticObject(false);
                b.setAttractive(true);
                b.setFriction(0.1);
                b.setVelocity(firingAngle.multiply(amount*ammo.getVelocityModifier()));
                Projectile projectile = new Projectile(b, ammo.getProjectileTexture(), ammo, shooter.getCurrentWeapon(), shooter);
                engine.getProjectiles().add(projectile);
                engine.getPhysicsEngine().addBody(b);
                //engine.addProjectile(projectile);
                engine.throwEvent(new Event("END_OF_PLAYER_TURN"));
            }
        }
    }
}
