package core.model;

import com.google.gson.Gson;
import utility.Loader;
import utility.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Progression {
    private HashMap<Pair<Level, Player>, Integer> progression = new HashMap<>();

    public Progression(HashMap<Pair<Level, Player>, Integer> progression) {
        this.progression = progression;
    }

    public static Progression load(){
        File f = new File(Loader.getSpriteFolderPath() + "progression.dat");
        if (!f.exists()){
            return new Progression(new HashMap<>());
        }else {
            try {
                String content = new String(Files.readAllBytes(Paths.get("duke.java")));
                Gson gson = new Gson();
                return gson.fromJson(content, Progression.class);
            } catch (Exception e) {
                e.printStackTrace();
                return new Progression(new HashMap<>());
            }
        }
    }

    public static void save(Progression p){
        File f = new File(Loader.getSpriteFolderPath() + "progression.dat");
        if (!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
