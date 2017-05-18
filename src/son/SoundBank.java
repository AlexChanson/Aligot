package son;

import utility.Loader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;

/**
 * Internal class for loading files in memory
 */
class SoundBank {
    private static HashMap<String, AudioInputStream> data;
    static {
        data = new HashMap<>();
    }

    static AudioInputStream get(String filename){
        AudioInputStream temp = data.computeIfAbsent(filename, key -> {
            File soundFile = new File(Loader.getSpriteFolderPath() + key + ".wav");
            try {
                return AudioSystem.getAudioInputStream(soundFile);
            } catch (Exception e){
                System.out.println("Error trying to load sound file: '" + key + ".wav'.");
                //e.printStackTrace();
                return null;
            }

        });
        return null;
    }
}
