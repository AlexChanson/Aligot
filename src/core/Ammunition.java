package core;

public class Ammunition {
    public static int LASER = 2, BULLET = 1, EFFECT_FIRE = 40;
    private int damageBonus, type, effect;

    public Ammunition(int damageBonus, int type, int effect) {
        this.damageBonus = damageBonus;
        this.type = type;
        this.effect = effect;
    }

    public Ammunition(int damageBonus) {
        this.damageBonus = damageBonus;
        type = 1;
        effect = -1;
    }
}