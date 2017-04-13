package core;


public class Magazine extends Item {
    private Ammunition ammo;
    private int bullets, maxBullets;

    public Magazine(String name, String icon, Ammunition ammo, int capacity) {
        super(name, icon);
        this.ammo = ammo;
        bullets = capacity;
        maxBullets = capacity;
    }

    @Override
    public void use(Player user) {

    }

    public Ammunition getAmmo() {
        return ammo;
    }

    public int getBullets() {
        return bullets;
    }

    public int getMaxBullets() {
        return maxBullets;
    }
}
