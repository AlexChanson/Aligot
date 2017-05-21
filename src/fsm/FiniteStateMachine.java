package fsm;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ben on 11/04/17.
 * represents a finite state machine automaton
 */
public class FiniteStateMachine {
    private HashMap<String, State> states;
    private Boolean end;
    private String state;
    private boolean init = false;

    public FiniteStateMachine(){
        end = false;
        state = "";
        states = new HashMap<>();
    }

    /**
     * adds a new state to the machine
     * if there are no states yet, this state becomes the actual state
     * @param state
     */
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

    /**
     * set fsm actual state without triggering onEnter and onExit methods
     * @param state state to set to actual state
     */
    public void setState(State state){
        if ( states.containsValue(state) ){
            this.state = state.getStateName();
        }
    }

    /**
     * set the actual state to a state already in the state machine
     * @param stateName name of state
     */
    public void setInitialState(String stateName){
        if(!init){
            if ( states.containsKey(stateName) ){
                this.state = stateName;
                this.states.get(stateName).onEnter();
                init = true;
            }
        }
    }

    /**
     * @return the name of the active state
     */
    public String getActualStateName(){
        if (this.state != null){
            return this.states.get(this.state).getStateName();
        }
        return "";
    }

    /**
     * @return the active state
     */
    public State getActualState(){
        if (this.state != null){
            return this.states.get(this.state);
        }
        return null;
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

    /**
     * checks if finite state machine contains a specific state
     * @param stateName
     * @return
     */
    public Boolean isRegisteredState(String stateName){
        return states.containsKey(stateName);
    }

    /**
     *
     * @return all the states name in the machine
     */
    public String toString(){
        String returned = "Actual state"+this.state+"\n";

        for ( String key: states.keySet() ){
            returned += key + "\n";
        }

        return returned;
    }

    /**
     * call the onUpdate method on every states
     * @return true if the actual state is a final state
     */
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
