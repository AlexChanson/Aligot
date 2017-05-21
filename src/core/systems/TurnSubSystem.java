package core.systems;

import core.Event;

/**
 * @author Ben Crulis
 * @handles END_OF_PLAYER_TURN, END_OF_SIMULATION_TURN, NEW_TURN, REMOVED_LAST_PROJECTILE, EXITING
 */
public class TurnSubSystem extends SubSystem {

    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        switch (event.type){
            case "END_OF_PLAYER_TURN":
                engine.getGameState().changeState("simulationState");
                break;
            case "END_OF_SIMULATION_TURN":
                engine.getGameState().changeState("playerActingState");
                break;
            case "NEW_TURN":
                engine.nextTurn();
                engine.throwEvent(new Event("END_OF_SIMULATION_TURN"));
                break;
            case "REMOVED_LAST_PROJECTILE":
                engine.throwEvent(new Event("NEW_TURN"));
                break;
            case "EXITING":
                engine.getGameState().changeState("endGame");
        }
    }
}
