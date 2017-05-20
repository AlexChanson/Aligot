package core.model;

public class Ammunition {
    public static int LASER = 2, BULLET = 1, TENTACULE = 3, MISSILE = 4, EFFECT_FIRE = 40, EFFECT_LIFE_STEAL = 41;
    public static float EPLOSION_SMALL = 5.0f, EXPLOSION_MEDIUM = 10.0f;
    private int damageBonus, type, effect;
    private float explosionRadius, massModifier, velocityModifier;
    private String projectileTexture;

    public Ammunition(int damageBonus, int type, int effect, float explosionRadius, float massModifier, float velocityModifier, String projectileTexture) {
        this.damageBonus = damageBonus;
        this.type = type;
        this.effect = effect;
        this.explosionRadius = explosionRadius;
        this.massModifier = massModifier;
        this.velocityModifier = velocityModifier;
        this.projectileTexture = projectileTexture;
    }

    public Ammunition(int damageBonus) {
        this.damageBonus = damageBonus;
        type = 1;
        effect = -1;
        explosionRadius = 0f;
        projectileTexture = "";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Ammunition){
            Ammunition other = (Ammunition) obj;
            if (other.getDamageBonus() == damageBonus && other.getEffect() == effect && other.getType() == type && (other.explosionRadius - explosionRadius) < 0.0001)
                return true;
        }
        return false;
    }


    public Ammunition copy(){
        return new Ammunition(damageBonus,type,effect,explosionRadius, massModifier, velocityModifier, projectileTexture);
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public int getType() {
        return type;
    }

    public int getEffect() {
        return effect;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public float getMassModifier() {
        return massModifier;
    }

    public float getVelocityModifier() {
        return velocityModifier;
    }

    public String getProjectileTexture() {
        return projectileTexture;
    }

    @Override
    public String toString() {
        return "Ammo:\n{Bonus=" + damageBonus + ", Type=" + type + ", Effect=" + effect + ", ExplosionRadius=" + explosionRadius + "}";
    }
}
