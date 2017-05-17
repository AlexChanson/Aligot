package core;

import fsm.State;

/**
 * Created by ben on 28/04/17.
 */
public class PlayerActingState extends State {
    private boolean playerTurnEnded;
    private Engine engine;

    public PlayerActingState( Engine engine ){
        this.engine = engine;
    }

    @Override
    public void initialize(){
        playerTurnEnded = false;
    }

    @Override
    public String onUpdate() {
        if (playerTurnEnded){
            return "simulationState";
        }
        return "playerActingState";
    }

    @Override
    public void onEnter(){
        playerTurnEnded = false;
        engine.throwEvent(new Event("PLAYER_CHANGED"));
    }

    @Override
    public String getStateName() {
        return "playerActingState";
    }
}
