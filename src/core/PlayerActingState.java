package core;

import fsm.State;

/**
 * Created by ben on 28/04/17.
 */
public class PlayerActingState extends State {
    private boolean playerTurnEnded;

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
    }

    @Override
    public String getStateName() {
        return "playerActingState";
    }
}
