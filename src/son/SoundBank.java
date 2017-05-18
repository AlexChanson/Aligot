package son;

import utility.Loader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Internal class for loading files in memory
 */
class SoundBank {
    private static HashMap<String, byte[]> data;
    private static HashSet<String> notFound = new HashSet<>();
    static {
        data = new HashMap<>();
    }

    static AudioInputStream get(String filename){
        if (notFound.contains(filename))
            return null;
        if(data.get(filename) == null)
            load(filename);
        try {
            return AudioSystem.getAudioInputStream(new ByteArrayInputStream(data.get(filename)));
        } catch (UnsupportedAudioFileException | IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void load(String filename){
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(Loader.getSpriteFolderPath() + filename + ".wav"));
            data.put(filename, bytes);
        } catch (IOException e) {
            System.out.printf("Error loading '%s' .", filename + ".wav");
            notFound.add(filename);
        }
    }
}
