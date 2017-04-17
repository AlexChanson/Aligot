package fsm;

/**
 * Created by ben on 11/04/17.
 */
public abstract class State {
    protected Boolean finalState = false;

    public void initialize(){

    }

    public void onEnter(){

    }

    public void onExit(){

    }

    public abstract String onUpdate();

    public final Boolean isFinalState(){
        return finalState;
    }

    public abstract String getStateName();
}
