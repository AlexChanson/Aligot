package core.systems;


import core.Event;
import core.model.Item;
import core.model.Player;
import core.model.Weapon;
import org.lwjgl.glfw.GLFW;
import utility.Weapons;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WeaponChangeSystem extends SubSystem{
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("KEY_PRESSED")){
            int key = (int)event.data;
            if (key >= 49 && key <= 57){
                switchWeapon(key - 49);
            }
        }
    }

    private void switchWeapon(int n) {
        Player p = engine.getActivePlayer();
        if (p != null){
            List<Item> inv = p.getInventory();
            ArrayList<Weapon> weapons = new ArrayList<>();
            inv.forEach(item -> {
                if (item instanceof Weapon)
                    weapons.add((Weapon) item);
            });
            try {
                p.setCurrentWeapon(weapons.get(n));
            }catch (IndexOutOfBoundsException ignored){}
        }
    }
}
