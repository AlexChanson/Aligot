package generator;

import core.Level;
import core.Planet;
import core.Spawn;
import physics.Vector2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static java.lang.Math.*;

public class LevelGen {
    public static final int[] SMALL = {640,360,7}, MEDIUM = {1280,720,12}, LARGE = {1920,1080,20};
    private final long seed;
    private int[] mapSize = MEDIUM;
    private ArrayList<Vector2D> centers = new ArrayList<>();
    private ArrayList<Planet> wolrds = new ArrayList<>();
    private Random gen;
    private Spawn left, right;

    public LevelGen(long seed) {
        this.seed = seed;
    }

    public LevelGen(long seed, int[] mapSize){
        this.seed = seed;
        gen = new Random(seed);
        if(mapSize != SMALL || mapSize != MEDIUM || mapSize != LARGE) {
            System.out.println("Error illegal argument map size must be SMALL, MEDIUM, LARGE.");
            this.mapSize = MEDIUM;
        } else
            this.mapSize = mapSize;
    }

    public Level create(){
        genCenters();
        genPlanets();

        return null;
    }

    private void genCenters() {
        double diag = sqrt(pow(mapSize[0],2)+ pow(mapSize[1],2));
        int minRadius = (int) (diag*0.015);
        int maxRadius = (int) (diag*0.095);
        int[][] boundaries = {{maxRadius*2, mapSize[0] - maxRadius*2}, {maxRadius*2, mapSize[1] - maxRadius*2}};

        // Generating the left spawn in the left most third of the boundary box


        // Generating the left spawn in the left most third of the boundary box


        // Generating the rest of the planets
        int maxTries = 1000;
        for(int i = 0; i < maxTries || centers.size() < mapSize[2] - 1; ++i){
            //centers.add(new Vector2D(getInt(boundaries[0][0], boundaries[0][1]), getInt(boundaries[1][0], boundaries[1][1])));
        }
    }

    private void genPlanets() {

    }

    private int getInt(int min, int max){
        return min + gen.nextInt(max-min);
    }

}
