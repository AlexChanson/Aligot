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
        states = new HashMap<>();
        transitions = new HashMap<>();
    }

    public void addState(State state, String name){
        if ( states.isEmpty() ){
            this.state = name;
        }
        states.put(name, state);
        state.initialize();
    }

    public void setState(String stateName){
        if ( states.containsKey(stateName) ){
            this.state = stateName;
        }
    }

    public Boolean isRegisteredState(String stateName){
        return states.containsKey(stateName);
    }

    public Boolean update(){

        String newState = this.states.get(this.state).onUpdate();

        return end;
    }
}
