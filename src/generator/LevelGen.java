package generator;

import core.Level;
import core.Planet;
import core.Spawn;
import physics.RigidBody;
import physics.Vector2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static java.lang.Math.*;

public class LevelGen {
    public static final int[] SMALL = {640,360}, MEDIUM = {1280,720}, LARGE = {1920,1080};
    private final long seed;
    private int[] mapSize = MEDIUM;
    public ArrayList<Vector2D> centers = new ArrayList<>();
    public ArrayList<Planet> worlds = new ArrayList<>();
    private Random gen;
    private Vector2D left, right;
    private int planetNumber, minRadius, maxRadius, minMass = 100000000, maxMass = minMass*10;

    public LevelGen(long seed) {
        this.seed = seed;
    }

    public LevelGen(long seed, int[] mapSize, int planetNumber){
        this.seed = seed;
        this.planetNumber = planetNumber;
        gen = new Random(seed);
        if(mapSize != SMALL && mapSize != MEDIUM && mapSize != LARGE) {
            System.out.println("Error illegal argument map size must be SMALL, MEDIUM, LARGE.");
            this.mapSize = MEDIUM;
        } else
            this.mapSize = mapSize;
    }

    public Level create(){
        System.out.println("Starting Procedural generator...");
        genCenters();
        genPlanets();
        System.out.println("Generation Done !, Planet number = " + centers.size());
        return null;
    }

    private void genCenters() {
        double diag = sqrt(pow(mapSize[0],2)+ pow(mapSize[1],2));
        minRadius = (int) (diag*0.015);
        maxRadius = (int) (diag*0.045);
        System.out.println(diag);
        int[][] boundaries = {{(int) (maxRadius*1.5), (int) (mapSize[0] - maxRadius*1.5)},
                {(int) (maxRadius*1.5), (int) (mapSize[1] - maxRadius*1.5)}};

        // Generating the left spawn in the left most third of the boundary box
        int third = mapSize[0]/3;
        left = new Vector2D(getInt(boundaries[0][0], third), getInt(boundaries[1][0], boundaries[1][1]));
        centers.add(left);

        // Generating the left spawn in the right most third of the boundary box
        right = new Vector2D(getInt(third*2, boundaries[0][1]), getInt(boundaries[1][0], boundaries[1][1]));
        right.add(right);

        // Generating the rest of the planets
        int maxTries = 1000;
        Vector2D temp;
        for(int i = 0; i < maxTries && centers.size() < planetNumber; ++i){
            temp = new Vector2D(getInt(boundaries[0][0], boundaries[0][1]), getInt(boundaries[1][0], boundaries[1][1]));
            boolean ok = true;
            for(Vector2D p : centers){
                if(p.distanceTo(temp) < maxRadius*2.5)
                    ok = false;
            }
            if(ok)
                centers.add(temp);
        }
    }

    private void genPlanets() {
        for(Vector2D v : centers){
            worlds.add(new Planet(new RigidBody(v,
                    getInt(minRadius, maxRadius),
                    getInt(minMass, maxMass)),
                    "earth.jpg",
                    "dummy_type"));
        }

    }

    private int getInt(int min, int max){
        return min + gen.nextInt(max-min);
    }

}
