package core.systems;

import core.Event;
import core.GraphicsEngine;
import core.model.Player;
import fsm.GUIStates.MultiWinState;
import gamelauncher.Game;

public class ExitSubSystem extends SubSystem {


    private GraphicsEngine graphicsEngine;
    public ExitSubSystem(GraphicsEngine graphicsEngine){
        this.graphicsEngine = graphicsEngine;
    }

    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if ( event.type.equals("QUIT") ){
            engine.throwEvent(new Event("EXITING"));
            graphicsEngine.transitionToGUIState("start");
            Game.setLevel(null);
        }
        if (event.type.equals("PLAYER_WINS")){
            Player player = (Player) event.data;
            if (!graphicsEngine.getGuiFSM().isRegisteredState("multiWin_" + player.getName())){
                graphicsEngine.getGuiFSM().addState(new MultiWinState(player));
            }
            graphicsEngine.transitionToGUIState("multiWin_" + player.getName());
            Game.setLevel(null);
        }
    }
}
