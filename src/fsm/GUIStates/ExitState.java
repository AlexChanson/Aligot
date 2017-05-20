package fsm.GUIStates;

import fsm.State;
import gamelauncher.Game;
import son.SoundPlayer;

public class ExitState extends State {
    @Override
    public void onEnter() {
        SoundPlayer.stopLoop("loop_1");
        SoundPlayer.stopLoop("loop_2");
        SoundPlayer.stopLoop("loop_3");
    }

    @Override
    public String onUpdate() {
        finalState = true;
        isFinalState();
        Game.halt();
        return "exit";
    }

    @Override
    public String getStateName() {
        return "exit";
    }
}
