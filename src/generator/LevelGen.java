package generator;

import core.Level;
import physics.Vector2D;
import java.util.HashSet;


public class LevelGen {
    public static final int[] SMALL = {100,100}, MEDIUM = {250,250}, LARGE = {500,500};
    private final long seed;
    private int[] mapSize = MEDIUM;
    private HashSet<Vector2D> centers = new HashSet<>();

    public LevelGen(long seed) {
        this.seed = seed;
    }

    public LevelGen(long seed, int[] mapSize){
        this.seed = seed;
        if(mapSize != SMALL || mapSize != MEDIUM || mapSize != LARGE) {
            System.out.println("Error illegal argument map size must be SMALL, MEDIUM, LARGE.");
            this.mapSize = MEDIUM;
        } else
            this.mapSize = mapSize;
    }

    public Level create(){



        return null;
    }


}
