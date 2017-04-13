package core;

public class Weapon extends Item{
    private String texture;
    private int[] damageRange;
    private Magazine magazine = null;

    public Weapon(String name, String icon, String texture, int[] damageRange) {
        super(name, icon);
        this.texture = texture;
        this.damageRange = damageRange;
    }

    @Override
    public void use(Player user) {
        this.fire();
    }

    private void fire() {

    }

    private void reload(Magazine m){
        this.magazine = m;
    }

    public String getTexture() {
        return texture;
    }

    public int[] getDamageRange() {
        return damageRange;
    }

    public Magazine getMagazine() {
        return magazine;
    }
}
