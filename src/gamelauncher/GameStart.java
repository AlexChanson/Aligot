package gamelauncher;

/**
 * Created by ben on 15/04/17.
 * allows different games to use the game launcher
 */
public interface GameStart {
    public void start(int screenHeight, int screenWidth, boolean fullscreen, String firstPlayerName, String secondPlayerName);
}
