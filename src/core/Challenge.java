package core;

import java.util.HashSet;

/**
 * Created by Christopher on 24/04/2017.
 */
public class Challenge extends Level{
    private String name, info, bgTexture;
    private HashSet<Planet> planets = new HashSet<>();
    private int[] mapSize;
    private int difficulty = 1;
    private int nb_shot;
    private int nb_target = 5;

    public Challenge (String name, String info, String bgTexture, HashSet<Planet> planets, int[] mapSize, int difficulty, int nb_shot, int nb_target){
        super(name, info, bgTexture, planets, mapSize);
        this.difficulty = difficulty;
        this.nb_shot = nb_shot;
        this.nb_target = nb_target;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String getBgTexture() {
        return bgTexture;
    }

    @Override
    public void setBgTexture(String bgTexture) {
        this.bgTexture = bgTexture;
    }

    @Override
    public HashSet<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(HashSet<Planet> planets) {
        this.planets = planets;
    }

    @Override
    public int[] getMapSize() {
        return mapSize;
    }

    @Override
    public void setMapSize(int[] mapSize) {
        this.mapSize = mapSize;
    }

    public int getNb_target() {
        return nb_target;
    }

    public void setNb_target(int nb_target) {
        this.nb_target = nb_target;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getNb_shot() {
        return nb_shot;
    }

    public void setNb_shot(int nb_shot) {
        this.nb_shot = nb_shot;
    }

}
