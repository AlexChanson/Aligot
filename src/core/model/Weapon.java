package core.model;

public class Weapon extends Item{
    private String texture;
    private int[] damageRange;
    private Ammunition ammo;

    public Weapon(String name, String icon, String texture, int[] damageRange, Ammunition ammo) {
        super(name, icon);
        this.texture = texture;
        this.damageRange = damageRange;
        this.ammo = ammo;
    }

    @Override
    public void use(Player user) {

    }

    public String getTexture() {
        return texture;
    }

    public int[] getDamageRange() {
        return damageRange;
    }

    public Ammunition getAmmo() {
        return ammo;
    }

    public void setAmmo(Ammunition ammo) {
        this.ammo = ammo;
    }

    public Object copy() {
        return new Weapon(name, icon, texture, damageRange, ammo);
    }
}
