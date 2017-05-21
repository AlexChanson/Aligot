package core;

import core.model.Level;
import core.model.Planet;
import physics.RigidBody;
import physics.Vector2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.Math.*;

/**
 * @author Alexandre
 * The procedural level generator
 * Generates levels from a seed
 */
public class LevelGen {
    private final static Logger LOGGER = Logger.getLogger(LevelGen.class.getName());
    public static final int[] SMALL = {640,360}, MEDIUM = {1280,720}, LARGE = {1920,1080};
    private final long seed;
    private int[] mapSize = MEDIUM;
    private ArrayList<Vector2D> centers = new ArrayList<>();
    private ArrayList<Planet> worlds = new ArrayList<>();
    private Random gen;
    private Vector2D left, right;
    private int planetNumber, minRadius, maxRadius;
    private double massRef = 5e15; //the mass of the smallest planet will be scaled with radius
    private Level result;

    public LevelGen(long seed) {
        this.seed = seed;
        this.mapSize = LARGE;
        setPlanetNumber(7);
    }

    /**
     * Initializes the level generator
     * @param seed the seed for the level, two equals seeds will produce the same level
     * @param mapSize can be SMALL, MEDIUM or LARGE or custom int[2]
     */
    public LevelGen(long seed, int[] mapSize){
        this.seed = seed;
        this.planetNumber = (int) (sqrt(pow(mapSize[0],2)+ pow(mapSize[1],2))/(290) + 1);
        gen = new Random(seed);
        this.mapSize = mapSize;
    }

    /**
     * Main method used to run the generation routine, parameters are set with the constructor.
     * @return Returns the generated level
     */
    public Level create(){
        String[] bgTextures = {"menu_bg.png", "menu_bg.png", "menu_bg.png"};
        result = new Level(Long.toString(seed), "Generated procedurally", bgTextures[gen.nextInt(bgTextures.length)]);
        result.setMapSize(mapSize);
        LOGGER.log(java.util.logging.Level.CONFIG, "Starting Procedural generator with seed " + seed);
        genCenters();
        genSpawns();
        genPlanets();
        LOGGER.log(java.util.logging.Level.INFO, "Generation Done !, Planet number = " + centers.size());
        return result;
    }

    /**
     * Generates the center of the planets within defined bounds and avoiding overlap.
     */
    private void genCenters() {
        double diag = sqrt(pow(mapSize[0],2)+ pow(mapSize[1],2));
        minRadius = (int) (diag*0.018);
        maxRadius = (int) (diag*0.045);

        int[][] boundaries = {{(int) (maxRadius*1.4), (int) (mapSize[0] - maxRadius*1.4)},
                {(int) (maxRadius*1.4), (int) (mapSize[1] - maxRadius*1.4)}};

        // Generating the left spawn in the left most third of the boundary box
        int third = mapSize[0]/4;
        left = new Vector2D(getInt(boundaries[0][0], third), getInt(boundaries[1][0], boundaries[1][1]));
        centers.add(left);

        // Generating the left spawn in the right most third of the boundary box
        right = new Vector2D(getInt(third*3, boundaries[0][1]), getInt(boundaries[1][0], boundaries[1][1]));
        centers.add(right);

        // Generating the rest of the planets
        int i, maxTries = 1000;
        Vector2D temp, screenCenter = new Vector2D(mapSize[0]/2, mapSize[1]/2);
        centers.add(screenCenter);
        for(i = 0; i < maxTries && centers.size() <= planetNumber; ++i){
            temp = new Vector2D(getInt(boundaries[0][0], boundaries[0][1]), getInt(boundaries[1][0], boundaries[1][1]));
            boolean ok = true;
            for(Vector2D p : centers){
                if(p.distanceTo(temp) < maxRadius*2.5)
                    ok = false;
            }
            if(ok)
                centers.add(temp);
        }
        centers.remove(screenCenter);
    }

    /**
     * Generates the two span planets for equity they have the same size/gravity,
     * and are placed a opposite sides of the map.
     */
    private void genSpawns(){
        //removing centers from the list will generate them separately
        centers.remove(left);
        centers.remove(right);
        int temp = getInt(minRadius, maxRadius);
        double temp2 = massRef*(temp/minRadius);
        RigidBody rigidBody = new RigidBody(left, temp, temp2);
        rigidBody.setAttractive(true);
        rigidBody.setStaticObject(true);
        worlds.add(Planet.spawn(rigidBody, "planet_spawn_1.png"));
        rigidBody = new RigidBody(right, temp, temp2);
        rigidBody.setAttractive(true);
        rigidBody.setStaticObject(true);
        worlds.add(Planet.spawn(rigidBody, "placeholder.png"));
    }

    /**
     * Generates the other planets of the map they can be of different types,
     * textures are randomized for each type
     */
    private void genPlanets() {
        String[] planetTypes = {"rock", "gas", "star", "black_hole"},
                 rockTextures = {"planet_rock_1.png", "planet_rock_2.png", "planet_rock_3.png"},
                 gasTextures = {"planet_gas_1.png", "planet_gas_2.png", "planet_gas_3.png"},
                 starTextures = {"planet_star_1.png", "planet_star_2.png"},
                 blackHolesTextures = {"blackhole.png"};
        HashMap<String, String[]> textures = new HashMap<>();
        textures.put("rock", rockTextures);
        textures.put("gas", gasTextures);
        textures.put("star", starTextures);
        textures.put("black_hole", blackHolesTextures);

        for(Vector2D v : centers){
            double radius = getInt(minRadius, maxRadius);
            RigidBody rigidBody = new RigidBody(v, radius,massRef*(radius/minRadius));
            rigidBody.setAttractive(true);
            rigidBody.setStaticObject(true);

            String type = planetTypes[gen.nextInt(planetTypes.length)];
            worlds.add(new Planet(rigidBody,textures.get(type)[gen.nextInt(textures.get(type).length)], type));
        }
        result.getPlanets().addAll(worlds);
    }

    /**
     * Generates a pseudo-random integer
     * @param min range lower bound (inclusive)
     * @param max range upper bound (exclusive)
     * @return a random integer between [min] and ]max[
     */
    private int getInt(int min, int max){
        return min + gen.nextInt(max-min);
    }

    public Level getLevel() {
        if(result != null)
            return result;
        else
            return create();
    }

    public int[] getMapSize() {
        return mapSize;
    }

    public void setPlanetNumber(int planetNumber) {
        this.planetNumber = planetNumber;
    }
}
