package fsm;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ben on 11/04/17.
 */
public class FiniteStateMachine {
    private HashMap<String, State> states;
    private Boolean end;
    private String state;

    public FiniteStateMachine(){
        end = false;
        state = "";
        states = new HashMap<>();
    }

    public void addState(State state){
        String name = state.getStateName();
        if ( states.isEmpty() && name != "" ){
            this.state = name;

        }
        if ( name != "" ){
            states.put(name, state);
            state.initialize();
        }
    }

    public void addStates(State... states){
        Arrays.asList(states).forEach(this::addState);
    }

    /**
     * set fsm actual state without triggering onEnter and onExit methods
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

    public String toString(){
        String returned = "";

        for ( String key: states.keySet() ){
            returned += key + "\n";
        }

        return returned;
    }

    public Boolean update(){
        if ( isRegisteredState(this.state) ){
            String newState = this.states.get(this.state).onUpdate();
            if ( newState != this.state && isRegisteredState(newState) ){
                changeState(newState);
            }
        }
        if ( isRegisteredState(this.state) ){
            if (this.states.get(state).isFinalState()) {
                end = true;
            }
        }
        else{
            return true;
        }

        return end;
    }
}
