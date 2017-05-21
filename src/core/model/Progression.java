package core.model;

import com.google.gson.Gson;
import utility.Loader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static java.nio.file.StandardOpenOption.*;

public class Progression {
    private HashMap<String, HashMap<String, Integer>> progression = new HashMap<>();

    public Progression(HashMap<String, HashMap<String, Integer>> progression) {
        this.progression = progression;
    }

    public static Progression load(String path){
        File f = new File(path);
        if (!f.exists()){
            return new Progression(new HashMap<>());
        }else {
            try {
                String content = new String(Files.readAllBytes(Paths.get(path)));
                Gson gson = new Gson();
                Progression p = gson.fromJson(content, Progression.class);
                return p != null ? p : new Progression(new HashMap<>());
            } catch (Exception e) {
                e.printStackTrace();
                return new Progression(new HashMap<>());
            }
        }
    }

    public static Progression load(){
        return load(Loader.getSpriteFolderPath() + "progression.dat");
    }

    public static void save(String path, Progression p){
        File f = new File(path);
        if (f.exists())
            f.delete();
        Gson gson = new Gson();
        String toWrite = gson.toJson(p);
        try {
            Files.write(Paths.get(path), toWrite.getBytes(), CREATE_NEW);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(Progression p){
        save(Loader.getSpriteFolderPath() + "progression.dat", p);
    }

    public void addScore(Player p, Level l, Integer score){
        if (progression.get(p.getName()) != null){
            if (progression.get(p.getName()).get(l.getName()) != null){
                Integer s = progression.get(p.getName()).get(l.getName());
                if (s != null && score > s)
                    progression.get(p.getName()).put(l.getName(), s);
            }else {
                progression.get(p.getName()).put(l.getName(), score);
            }
        }else {
            progression.put(p.getName(), new HashMap<>());
            progression.get(p.getName()).put(l.getName(), score);
        }
    }

    public int getScore(Player p, Level l){
        if (progression.get(p.getName()) == null)
            return 0;
        if (progression.get(p.getName()).get(l.getName()) == null)
            return 0;
        return progression.get(p.getName()).get(l.getName());
    }
}
