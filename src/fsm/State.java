package fsm;

/**
 * Created by ben on 11/04/17.
 */
public abstract class State {
    protected Boolean finalState = false;

    /**
     * is called when state is added in the finite state machine and only once
     */
    public void initialize(){

    }

    /**
     * called when transitioning from another state to this one
     */
    public void onEnter(){

    }

    /**
     * called when transitioning from this state to another one
     */
    public void onExit(){

    }

    /**
     *
     * @return the state to transition to, return name of the state to stay in it
     */
    public abstract String onUpdate();

    /**
     *
     * @return true if the state is final, used by state machine
     */
    public final Boolean isFinalState(){
        return finalState;
    }

    /**
     * the state name should be unique
     * @return the state name
     */
    public abstract String getStateName();
}
