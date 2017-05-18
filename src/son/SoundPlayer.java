package son;

import javax.sound.sampled.AudioInputStream;
import java.util.HashMap;

/**
 * Provides a simple way to play wav sounds to other game components
 * -------------------------------------------------------------------->  DO NOT TOUCH ANYTHING ! (except Ben)
 */
public class SoundPlayer {
    private static HashMap<String, PlaySound> playing = new HashMap<>();
    private static HashMap<String, PlaySound> paused = new HashMap<>();
    private static HashMap<String, LoopedSound> looped = new HashMap<>();

    public static void play(String soundName){
        AudioInputStream audio = SoundBank.get(soundName);
        if (audio == null || playing.get(soundName) != null)
            return;
        if (paused.get(soundName) != null){
            PlaySound temp = paused.get(soundName);
            paused.remove(soundName);
            temp.stop();
        }
        PlaySound p = new PlaySound(audio, soundName);
        Thread t = new Thread(p);
        playing.put(soundName, p);
        t.start();
    }

    public static void playLoop(String soundName){
        AudioInputStream audio = SoundBank.get(soundName);
        if (audio == null || looped.get(soundName) != null)
            return;
        LoopedSound l = new LoopedSound(audio, soundName);
        Thread t = new Thread(l);
        looped.put(soundName, l);
        t.start();
    }

    public static void stop(String soundName){
        PlaySound p = playing.get(soundName);
        if(p == null)
            return;
        p.stop();
        playing.remove(soundName);
    }

    public static void stopLoop(String soundName){
        LoopedSound l = looped.get(soundName);
        if (l == null)
            return;
        l.stop();
        looped.remove(l);
    }

    public static void pause(String soundName){
       PlaySound p = playing.get(soundName);
        if(p == null)
            return;
        playing.remove(soundName);
        p.pause();
        paused.put(soundName, p);
    }

    public static void resume(String soundName){
        PlaySound p = paused.get(soundName);
        if(p == null)
            return;
        paused.remove(soundName);
        p.resume();
        playing.put(soundName, p);
    }

    static void unregister(String name){
        playing.remove(name);
    }
}
