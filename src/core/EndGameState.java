package core;

import fsm.State;

/**
 * Created by ben on 16/05/17.
 */
public class EndGameState extends State {
    @Override
    public String onUpdate() {
        return "endGame";
    }

    @Override
    public String getStateName() {
        return "endGame";
    }
}
