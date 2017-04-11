package FSM;

/**
 * Created by ben on 11/04/17.
 */
public abstract class State {
    private Boolean finalState;

    public void initialize(){

    }

    public void onEnter(){

    }

    public void onExit(){

    }

    public abstract String onUpdate();

    public Boolean isFinalState(){
        return finalState;
    }
}
