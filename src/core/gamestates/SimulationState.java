package core.gamestates;

import fsm.State;

/**
 * Created by ben on 28/04/17.
 */
public class SimulationState extends State {
    private boolean simulating;


    @Override
    public void initialize(){
        simulating = true;
    }

    @Override
    public String onUpdate() {
        if (simulating){
            return "simulationState";
        }
        return "playerActingState";
    }

    @Override
    public void onEnter(){
        simulating = true;
    }

    @Override
    public String getStateName() {
        return "simulationState";
    }
}
