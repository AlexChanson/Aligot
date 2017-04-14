package core;

public class Weapon extends Item{
    private String texture;
    private int[] damageRange;
    private Magazine magazine = null;
    /**
     * Set to null by default the gun can shoot any ammo
     */
    private Ammunition[] compatible = null;

    public Weapon(String name, String icon, String texture, int[] damageRange, Ammunition[] compatible) {
        super(name, icon);
        this.texture = texture;
        this.damageRange = damageRange;
        this.compatible = compatible;
    }

    public Weapon(String name, String icon, String texture, int[] damageRange) {
        super(name, icon);
        this.texture = texture;
        this.damageRange = damageRange;
    }

    @Override
    public void use(Player user) {
        this.fire(user);
    }

    private void fire(Player shooter) {

    }

    public boolean reload(Magazine m){
        if(compatible == null){
            this.magazine = m;
            return true;
        }
        boolean isCompatible = false;
        for (Ammunition aCompatible : compatible) {
            if (aCompatible.equals(m.getAmmo()))
                isCompatible = true;
        }
        if(isCompatible){
            this.magazine = m;
            return true;
        }
        return false;
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

    public Ammunition[] getCompatible() {
        return compatible;
    }

    public void setCompatible(Ammunition[] compatible) {
        this.compatible = compatible;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Ammunition[] temp = new Ammunition[compatible.length];
        for (int i = 0; i < compatible.length; i++)
            temp[i] = (Ammunition) compatible[i].clone();
        return new Weapon(name, icon, texture, damageRange, temp);
    }
}
