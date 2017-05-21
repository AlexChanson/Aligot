package son;

import javax.sound.sampled.AudioInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a simple way to play wav sounds to other game components
 * @author Alexandre Chanson
**/
public class SoundPlayer {
    private static HashMap<String, PlaySound> playing = new HashMap<>();
    private static HashMap<String, PlaySound> paused = new HashMap<>();
    private static HashMap<String, LoopedSound> looped = new HashMap<>();
    private static ArrayList<Thread> threads = new ArrayList<>();
    private static boolean muted = false, concurrentPlay = true;

    /**
     * Plays a wav file contained in the asset folder of the game
     * @param soundName the name of the wav file without .wav
     */
    public static void play(String soundName){
        if (muted)
            return;
        AudioInputStream audio = SoundBank.get(soundName);
        if (audio == null)
            return;
        if (!concurrentPlay && playing.get(soundName)!= null)
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
        threads.add(t);
    }

    /**
     * Plays a wav file contained in the asset folder of the game in loop
     * @param soundName the name of the wav file without .wav
     */
    public static void playLoop(String soundName){
        if (muted)
            return;
        AudioInputStream audio = SoundBank.get(soundName);
        if (audio == null || looped.get(soundName) != null)
            return;
        LoopedSound l = new LoopedSound(audio, soundName);
        Thread t = new Thread(l);
        looped.put(soundName, l);
        t.start();
        threads.add(t);
    }

    /**
     * Stops the playback of a specific sound
     * @param soundName the name of the wav file without .wav
     */
    public static void stop(String soundName){
        PlaySound p = playing.get(soundName);
        if(p == null)
            return;
        p.stop();
        playing.remove(soundName);
    }

    /**
     * Stops the playback of a specific looped sound
     * @param soundName the name of the wav file without .wav
     */
    public static void stopLoop(String soundName){
        LoopedSound l = looped.get(soundName);
        if (l == null)
            return;
        l.stop();
        looped.remove(l);
    }

    /**
     * Stops the playback of a specific looped sound at the end of the current loop
     * @param soundName the name of the wav file without .wav
     */
    public static void stopLoopAtEnd(String soundName){
        LoopedSound l = looped.get(soundName);
        if (l == null)
            return;
        l.timedStop();
    }

    /**
     * Pauses a playing sound
     * @param soundName the name of the wav file without .wav
     */
    public static void pause(String soundName){
       PlaySound p = playing.get(soundName);
        if(p == null)
            return;
        playing.remove(soundName);
        p.pause();
        paused.put(soundName, p);
    }

    /**
     * Resumes a previously paused sound
     * @param soundName the name of the wav file without .wav
     */
    public static void resume(String soundName){
        if (muted)
            return;
        PlaySound p = paused.get(soundName);
        if(p == null)
            return;
        paused.remove(soundName);
        p.resume();
        playing.put(soundName, p);
    }

    public static void killAll(){
        threads.forEach(thread -> {
            try {
                thread.stop();
            }catch (Exception ignored){}
        });
        looped.clear();
        playing.clear();
    }

    public static void mute(boolean m){
        muted = m;
        if (muted)
            killAll();
    }

    public static boolean isMuted() {
        return muted;
    }

    /**
     * internal method used by the PlaySound objects to remove themselves from currently playing sounds
     * before their thread is terminated
     * @param name the name of the wav file without .wav
     */
    static void unregister(String name){
        playing.remove(name);
    }

    public static boolean isConcurrentPlay() {
        return concurrentPlay;
    }

    public static void setConcurrentPlay(boolean concurrentPlay) {
        SoundPlayer.concurrentPlay = concurrentPlay;
    }
}
