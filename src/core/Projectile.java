package core;

import physics.RigidBody;

public class Projectile extends GameObject {
    private Ammunition type;
    private Weapon origin;
    private Player shooter;

    public Projectile(RigidBody rigidBody, String texture, Ammunition type, Weapon origin, Player shooter) {
        super(rigidBody, texture);
        this.type = type;
        this.origin = origin;
        this.shooter = shooter;
    }

    public Weapon getOrigin() {
        return origin;
    }

    public Ammunition getType() {
        return type;
    }

    public Player getShooter() {
        return shooter;
    }
}
