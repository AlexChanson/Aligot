package core.systems;

import core.Event;
import core.GraphicsEngine;
import core.model.Player;
import core.model.Projectile;
import org.lwjgl.glfw.GLFW;
import physics.Vector2D;

/**
 * @handles STARTED_CHARGING_WEAPON, FINISHED_CHARGING_WEAPON, TICK
 * @emits FINISHED_CHARGING_WEAPON
 */
public class ChargingWeaponSubSystem extends SubSystem {
    private static boolean charging;
    private static double amount;
    public final static double minimalCharging = 10.0;
    public final static double maximumCharging = 300.0;
    public final static double increment = 5.0;

    public static boolean isCharging(){
        return charging;
    }

    public static double getAmount(){
        return amount;
    }

    public static float getAmountFloat(){
        return (float) amount;
    }

    @Override
    public void initialize() {
        charging = false;
        amount = minimalCharging;
    }

    @Override
    protected void processEvent(Event event) {
        switch (event.type){
            case "STARTED_CHARGING_WEAPON":
                amount = minimalCharging;
                charging = true;
                break;
            case "FINISHED_CHARGING_WEAPON":
                if (charging){
                    charging = false;
                    engine.throwEvent(new Event("FIRE", Math.min(amount, maximumCharging)));
                }
                break;
            case "TICK":
                if (charging){
                    amount += increment;
                    if (amount >= maximumCharging){
                        engine.throwEvent(new Event("FINISHED_CHARGING_WEAPON", "maximum charge"));
                    }
                }
                break;
        }
    }
}
