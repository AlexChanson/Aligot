package core.systems;

import core.Event;
import core.GraphicsEngine;
import fsm.GUIStates.StartState;
import gamelauncher.Game;

/**
 * Created by ben on 20/05/17.
 */
public class ExitSubSystem extends SubSystem {


    GraphicsEngine graphicsEngine;
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
    }
}
