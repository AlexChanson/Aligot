package core.gamestates;

import core.Engine;
import core.Event;
import core.systems.SubSystem;
import fsm.State;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ben on 28/04/17.
 */
public class PlayerActingState extends State {
    private Engine engine;
    private HashSet<String> activeSubSystems;

    public PlayerActingState( Engine engine ){
        this.engine = engine;
    }

    @Override
    public void initialize(){
        activeSubSystems = new HashSet<>();
        activeSubSystems.add("PlayerMovementSystem");
        activeSubSystems.add("FireSubSystem");
        activeSubSystems.add("PlayerAimingSubSystem");
        activeSubSystems.add("WeaponChangeSystem");
        activeSubSystems.add("ChargingWeaponSubSystem");
    }

    @Override
    public String onUpdate() {

        return "playerActingState";
    }

    @Override
    public void onEnter(){
        engine.throwEvent(new Event("PLAYER_CHANGED"));
        for (SubSystem subSystem : engine.getSystems()){
            if (activeSubSystems.contains(subSystem.getClass().getSimpleName())){
                subSystem.resume();
            }
        }
    }

    @Override
    public void onExit(){
        for (SubSystem subSystem : engine.getSystems()){
            if (activeSubSystems.contains(subSystem.getClass().getSimpleName())){
                subSystem.suspend();
            }
        }
    }

    @Override
    public String getStateName() {
        return "playerActingState";
    }
}
