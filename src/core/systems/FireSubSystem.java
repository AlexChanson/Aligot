package core.systems;

import core.Event;
import core.model.Ammunition;
import core.model.Player;
import core.model.Projectile;
import core.solvers.PlayerMovementSolver;
import physics.RigidBody;
import physics.Vector2D;

import java.util.logging.Logger;

public class FireSubSystem extends SubSystem{
    private final static Logger LOGGER = Logger.getLogger(PlayerMovementSolver.class.getName());

    @Override
    public void initialize() {
        LOGGER.info("Init OK");
    }

    @Override
    protected void processEvent(Event event) {
        if(event.type.equals("FIRE")){
            Player shooter = engine.getActivePlayer();
            Ammunition ammo = shooter.getCurrentWeapon().getAmmo();
            if(shooter.getCurrentWeapon() != null){
                Vector2D firingAngle = Vector2D.createFromAngleDegree(1, shooter.getGlobalWeaponOrientation() - 90);
                Vector2D projectileOrigin = new Vector2D(shooter.getRigidBody().getPosition());
                projectileOrigin = projectileOrigin.add(firingAngle.multiply(15.5));
                RigidBody b = new RigidBody(projectileOrigin, 5, 50*ammo.getMassModifier());
                b.setStaticObject(false);
                b.setAttractive(true);
                b.setVelocity(firingAngle.multiply(200*ammo.getVelocityModifier()));
                Projectile projectile = new Projectile(b, "star.png", ammo, shooter.getCurrentWeapon(), shooter);
                engine.getProjectiles().add(projectile);
                engine.getPhysicsEngine().addBody(b);
            }
        }
    }
}
