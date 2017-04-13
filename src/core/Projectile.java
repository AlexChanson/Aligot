package core;

import physics.RigidBody;

public class Projectile extends GameObject {
    private Ammunition type;
    private Weapon origin;

    public Projectile(RigidBody rigidBody, String texture, Ammunition type, Weapon origin) {
        super(rigidBody, texture);
        this.type = type;
        this.origin = origin;
    }

    public Weapon getOrigin() {
        return origin;
    }

    public Ammunition getType() {
        return type;
    }
}
