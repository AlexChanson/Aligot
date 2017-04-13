package core;


public class Magazine extends Item {
    private Ammunition ammo;

    public Magazine(String name, String icon, Ammunition ammo) {
        super(name, icon);
        this.ammo = ammo;
    }

    @Override
    public void use(Player user) {

    }
}
