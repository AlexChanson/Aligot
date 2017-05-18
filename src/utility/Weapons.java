package utility;

import core.model.Item;
import core.model.Player;
import core.model.Weapon;
import java.util.ArrayList;

public class Weapons {
    private static final ArrayList<Weapon> weapons;

    static {
        weapons = Loader.loadAll(Weapon.class, "weapons");
    }

    public static ArrayList<Weapon> get(){
        return new ArrayList<>(weapons);
    }

    public static void equip(Player player){
        boolean done = false;
        for (Weapon weapon : weapons) {
            if (!done) {
                player.setCurrentWeapon(weapon);
                done = true;
            }
            player.getInventory().add((Item) weapon.copy());
        }
    }
}
