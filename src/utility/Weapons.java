package utility;

import core.Weapon;
import java.util.ArrayList;

public class Weapons {
    private static final ArrayList<Weapon> weapons;

    static {
        weapons = Loader.loadAll(Weapon.class, "weapons");
    }

    public static ArrayList<Weapon> get(){
        return new ArrayList<>(weapons);
    }
}
