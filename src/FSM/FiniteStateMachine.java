package FSM;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by ben on 11/04/17.
 */
public class FiniteStateMachine {
    private HashMap<String, State> states;
    private HashMap<String, String> transitions;
    private Boolean end;
    private String state;

    public FiniteStateMachine(){
        end = false;
        state = "";
        states = new HashMap<>();
        transitions = new HashMap<>();
    }

    public void addState(State state){
        String name = state.getStateName();
        if ( states.isEmpty() && name != "" ){
            this.state = name;
            states.put(name, state);
            state.initialize();
        }
    }

    /**
     * set FSM actual state without triggering onEnter and onExit methods
     * @param stateName name of state to set to actual state
     */
    public void setState(String stateName){
        if ( states.containsKey(stateName) ){
            this.state = stateName;
        }
    }

    public void setState(State state){
        if ( states.containsValue(state) ){
            this.state = state.getStateName();
        }
    }

    /**
     * changes actual state by triggering onExit on previous state and onEnter on new state
     * @param stateName name of state to set to actual state
     */
    public void changeState(String stateName){
        if ( isRegisteredState(this.state) && isRegisteredState(stateName)){
            this.states.get(this.state).onExit();
            setState(stateName);
            this.states.get(stateName).onEnter();
        }
    }

    public Boolean isRegisteredState(String stateName){
        return states.containsKey(stateName);
    }

    public Boolean update(){
        if ( isRegisteredState(this.state) ){
            String newState = this.states.get(this.state).onUpdate();
            if ( newState != this.state && isRegisteredState(newState) ){
                changeState(newState);
            }
        }
        if ( this.states.get(state).isFinalState() ){
            end = true;
        }

        return end;
    }
}
